package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.app.Application
import android.view.View
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.EmploymentInfoRequest
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegment
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegmentsResponse
import co.yap.networking.customers.responsedtos.sendmoney.CountryModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class EmploymentQuestionnaireViewModel(application: Application) :
    LocationChildViewModel<IEmploymentQuestionnaire.State>(application),
    IEmploymentQuestionnaire.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val questionnaireAdaptor: EmploymentQuestionnaireAdaptor =
        EmploymentQuestionnaireAdaptor(
            arrayListOf()
        )
    override val state: IEmploymentQuestionnaire.State = EmploymentQuestionnaireState()
    override var selectedQuestionItemPosition: Int = -1
    override val industrySegmentsList: ArrayList<IndustrySegment> = arrayListOf()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setProgress(95)
    }

    override fun isDataRequiredFromApi(forStatus: EmploymentStatus) {
        when (forStatus) {
            EmploymentStatus.SELF_EMPLOYED, EmploymentStatus.SALARIED_AND_SELF_EMPLOYED -> {
                if (parentViewModel?.countries.isNullOrEmpty() && industrySegmentsList.isNullOrEmpty())
                    getCountriesAndSegments()
            }
            else -> {
            }
        }
    }

    override fun questionnaires(forStatus: EmploymentStatus): ArrayList<QuestionUiFields> {
        val questionnairesComposer: ComplianceQuestionsItemsComposer = KYCComplianceComposer()
        return questionnairesComposer.compose(forStatus)
    }

    override fun employmentTypes(): MutableList<EmploymentType> {
        val gson = GsonBuilder().create()
        return gson.fromJson<MutableList<EmploymentType>>(
            context.getJsonDataFromAsset(
                "jsons/employment_describe_you_best.json"
            ), object : TypeToken<List<EmploymentType>>() {}.type
        )
    }

    override fun parseEmploymentTypes(employmentTypes: MutableList<EmploymentType>): MutableList<CoreBottomSheetData> {
        employmentTypes.forEach {
            it.subTitle = it.employmentType
        }
        return employmentTypes.toMutableList()
    }

    override fun parseSegments(segments: MutableList<IndustrySegment>): MutableList<CoreBottomSheetData> {
        segments.forEach {
            it.subTitle = it.segment
        }
        return segments.toMutableList()
    }

    override fun getSelectedStateCountries(countries: ArrayList<Country>): List<Country> {
        if (countries.isNullOrEmpty()) return emptyList()
        countries.forEach {
            it.subTitle = it.getName()
            it.sheetImage = CurrencyUtils.getFlagDrawable(
                context,
                it.isoCountryCode2Digit.toString()
            )
        }

        return countries
    }

    override fun onInfoClick(
        questionUiFields: QuestionUiFields,
        callBack: (title: String, message: String) -> Unit
    ) {
        if (questionUiFields.key != null) {
            var title = ""
            var message = ""
            when (questionUiFields.key) {
                EmploymentQuestionIdentifier.SALARY_AMOUNT -> {
                    title =
                        getString(Strings.screen_employment_information_dialog_display_text_heading)

                    message =
                        getString(Strings.screen_employment_information_dialog_display_text_subheading)
                }

                EmploymentQuestionIdentifier.DEPOSIT_AMOUNT -> {
                    title =
                        getString(Strings.screen_employment_information_cash_dialog_display_text_heading)
                    message =
                        getString(Strings.screen_employment_information_cash_dialog_display_text_subheading)
                }
                else -> {
                }
            }
            if (title.isNotEmpty() && message.isNotEmpty()) {
                callBack(title, message)
            }
        }
    }

    override fun setBusinessCountries(
        countries: ArrayList<String>,
        position: Int
    ) {
        val objQuestion = questionnaireAdaptor.getDataForPosition(position)
        objQuestion.question.multipleAnswers.clear()
        objQuestion.question.multipleAnswers.addAll(countries)
        questionnaireAdaptor.setItemAt(position, objQuestion)
    }

    val countriesItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is ArrayList<*>) {
                setBusinessCountries(data as ArrayList<String>, selectedQuestionItemPosition)
            }
        }
    }

    val employmentTypeItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val objQuestion = questionnaireAdaptor.getDataForPosition(selectedQuestionItemPosition)
            objQuestion.question.answer.set((data as CoreBottomSheetData).subTitle)
            questionnaireAdaptor.setItemAt(selectedQuestionItemPosition, objQuestion)
        }
    }

    val rvQuestionItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            selectedQuestionItemPosition = pos
            when (view.id) {
                R.id.etQuestionEditText -> {
                }
            }
        }
    }

    private fun fetchParallelAPIResponses(
        responses: (RetroApiResponse<CountryModel>, RetroApiResponse<IndustrySegmentsResponse>) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            coroutineScope {
                val deferredCountriesResponse = async {
                    repository.getAllCountries()
                }
                val deferredIndustrySegmentsResponse = async {
                    repository.getIndustrySegments()
                }
                responses(
                    deferredCountriesResponse.await(),
                    deferredIndustrySegmentsResponse.await()
                )
            }
        }
    }

    override fun getCountriesAndSegments() {
        fetchParallelAPIResponses { countriesResponse, segmentsResponse ->
            launch(Dispatcher.Main) {
                when (countriesResponse) {
                    is RetroApiResponse.Success -> {
                        parentViewModel?.countries = Utils.parseCountryList(
                            countriesResponse.data.data,
                            addOIndex = false
                        ) as ArrayList<Country>
                    }
                    is RetroApiResponse.Error -> {
                        showDialogWithCancel(countriesResponse.error.message)
                    }
                }

                when (segmentsResponse) {
                    is RetroApiResponse.Success -> {
                        industrySegmentsList.clear()
                        industrySegmentsList.addAll(segmentsResponse.data.segments)
                    }
                    is RetroApiResponse.Error -> {
                        showDialogWithCancel(segmentsResponse.error.message)
                    }
                }
                state.viewState.value = false
            }
        }
    }

    private fun saveEmploymentInfo(
        employmentInfoRequest: EmploymentInfoRequest,
        success: () -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.saveEmploymentInfo(employmentInfoRequest)
            launch(Dispatcher.Main) {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        success.invoke()
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    private fun getEmploymentInfoRequest(
        status: EmploymentStatus
    ): EmploymentInfoRequest {
        val infoRequest = EmploymentInfoRequest()
        return when (status) {
            EmploymentStatus.EMPLOYED -> {
                EmploymentInfoRequest(
                    employerName = questionnaireAdaptor.getDataForPosition(0).question.answer.get(),
                    monthlySalary = questionnaireAdaptor.getDataForPosition(1).question.answer.get(),
                    expectedMonthlyCredit = questionnaireAdaptor.getDataForPosition(2).question.answer.get()
                )
            }
            EmploymentStatus.SELF_EMPLOYED -> {
                EmploymentInfoRequest(
                    companyName = questionnaireAdaptor.getDataForPosition(0).question.answer.get(),
                    industrySegmentCodes = questionnaireAdaptor.getDataForPosition(1).question.multipleAnswers,
                    businessCountries = questionnaireAdaptor.getDataForPosition(2).question.multipleAnswers,
                    monthlySalary = questionnaireAdaptor.getDataForPosition(3).question.answer.get(),
                    expectedMonthlyCredit = questionnaireAdaptor.getDataForPosition(4).question.answer.get()
                )
            }
            EmploymentStatus.SALARIED_AND_SELF_EMPLOYED -> TODO()
            EmploymentStatus.OTHER -> TODO()
        }
    }
}
