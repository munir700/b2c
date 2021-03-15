package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.app.Application
import android.view.View
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.filterSelectedIsoCodes
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.EmploymentQuestionnaireAdaptor
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
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
    override var employmentStatus: EmploymentStatus = EmploymentStatus.NONE
    override val selectedBusinessCountries: ArrayList<String> = arrayListOf()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        if (parentViewModel?.isOnBoarding == true) {
            progressToolBarVisibility(true)
            setProgress(95)
        }
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
        selectedBusinessCountries.clear()
        selectedBusinessCountries.addAll(countries)
    }

    val countriesItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is ArrayList<*>) {
                setBusinessCountries(data as ArrayList<String>, selectedQuestionItemPosition)
                validate()
            }
        }
    }

    val employmentTypeItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val objQuestion = questionnaireAdaptor.getDataForPosition(selectedQuestionItemPosition)
            val answerValue = when (data) {
                is EmploymentType -> data.employmentType
                is IndustrySegment -> data.segment
                else -> ""
            }
            objQuestion.question.answer.set(answerValue)
            questionnaireAdaptor.setItemAt(selectedQuestionItemPosition, objQuestion)
            validate()
        }
    }

    val rvQuestionItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            selectedQuestionItemPosition = pos
            when (view.id) {
                R.id.etAmount, R.id.etQuestionEditText -> validate()
            }
        }
    }

    private fun validate() {
        var isValid = false
        questionnaireAdaptor.getDataList().forEach {
            isValid = when (it.question.questionType) {
                QuestionType.COUNTRIES_FIELD -> it.question.multipleAnswers.isNotEmpty()
                else -> !it.question.answer.get().isNullOrBlank()
            }

            if (!isValid) {
                state.valid.set(isValid)
                return
            }
        }

        state.valid.set(isValid)
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

    override fun saveEmploymentInfo(
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

    override fun getEmploymentInfoRequest(
        status: EmploymentStatus
    ): EmploymentInfoRequest {
        return when (status) {
            EmploymentStatus.EMPLOYED -> {
                EmploymentInfoRequest(
                    employmentStatus = status.name,
                    employerName = questionnaireAdaptor.getDataForPosition(0).getAnswer(),
                    monthlySalary = questionnaireAdaptor.getDataForPosition(1).getAnswer(),
                    expectedMonthlyCredit = questionnaireAdaptor.getDataForPosition(2).getAnswer()
                )
            }
            EmploymentStatus.SELF_EMPLOYED, EmploymentStatus.SALARIED_AND_SELF_EMPLOYED -> {
                EmploymentInfoRequest(
                    employmentStatus = status.name,
                    companyName = questionnaireAdaptor.getDataForPosition(0).getAnswer(),
                    industrySegmentCodes = listOf(
                        industrySegmentsList.first {
                            it.segment == questionnaireAdaptor.getDataForPosition(
                                1
                            ).getAnswer()
                        }.segmentCode ?: ""
                    ),
                    businessCountries = parentViewModel?.countries?.filterSelectedIsoCodes(
                        questionnaireAdaptor.getDataForPosition(2).question.multipleAnswers
                    ),
                    monthlySalary = questionnaireAdaptor.getDataForPosition(3).getAnswer(),
                    expectedMonthlyCredit = questionnaireAdaptor.getDataForPosition(4).getAnswer()
                )
            }
            EmploymentStatus.OTHER -> {
                EmploymentInfoRequest(
                    employmentStatus = status.name,
                    employmentType = employmentTypes().first {
                        it.employmentType == questionnaireAdaptor.getDataForPosition(
                            0
                        ).getAnswer()
                    }.employmentTypeCode,
                    monthlySalary = questionnaireAdaptor.getDataForPosition(1).getAnswer(),
                    expectedMonthlyCredit = questionnaireAdaptor.getDataForPosition(2).getAnswer()
                )
            }
            EmploymentStatus.NONE -> TODO()
        }
    }
}
