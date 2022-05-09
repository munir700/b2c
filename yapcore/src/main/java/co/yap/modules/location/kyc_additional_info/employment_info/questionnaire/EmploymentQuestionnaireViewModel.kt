package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.filterSelectedIsoCodes
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.BusinessCountriesAdapter
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.EmploymentInfoRequest
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
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
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

class EmploymentQuestionnaireViewModel(application: Application) :
    LocationChildViewModel<IEmploymentQuestionnaire.State>(application),
    IEmploymentQuestionnaire.ViewModel, IRepositoryHolder<CustomersRepository>, IValidator,
    Validator.ValidationListener {
    override val repository: CustomersRepository = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IEmploymentQuestionnaire.State = EmploymentQuestionnaireState()
    override var selectedQuestionItemPosition: Int = -1
    override val industrySegmentsList: ArrayList<IndustrySegment> = arrayListOf()
    override var employmentStatus: EmploymentStatus = EmploymentStatus.NONE
    override val selectedBusinessCountries: ObservableField<ArrayList<String>> =
        ObservableField(arrayListOf())
    override var questionsList: ArrayList<QuestionUiFields> = arrayListOf()
    override var employmentStatusValue: MutableLiveData<EmploymentInfoAmendmentResponse> =
        MutableLiveData()
    override var validator: Validator? = Validator(null)
    override var businessCountriesLiveData: MutableLiveData<ArrayList<String>> =
        MutableLiveData()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        validator?.setValidationListener(this)
        if (hasAmendmentMap()) {
            getAmendmentsEmploymentInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        if (parentViewModel?.isOnBoarding == true) {
            progressToolBarVisibility(true)
            setProgress(95)
        }
    }

    override fun isDataRequiredFromApi(
        forStatus: EmploymentStatus, businessCountries: ArrayList<String>?,
        segmentCode: String?
    ) {
        when (forStatus) {
            EmploymentStatus.SELF_EMPLOYED, EmploymentStatus.SALARIED_AND_SELF_EMPLOYED -> getCountriesAndSegments(
                businessCountries,
                segmentCode
            )
            else -> {
            }
        }
    }

    override fun questionnaires(
        forStatus: EmploymentStatus,
        defaultValue: EmploymentInfoAmendmentResponse?
    ): ArrayList<QuestionUiFields> {
        val questionnairesComposer: ComplianceQuestionsItemsComposer =
            KYCComplianceComposer()
        return questionnairesComposer.compose(forStatus, defaultValue)
    }

    override fun employmentTypes(): MutableList<EmploymentType> {
        val gson = GsonBuilder().create()
        return gson.fromJson<MutableList<EmploymentType>>(
            context.getJsonDataFromAsset(
                "jsons/employment_describe_you_best.json"
            ), object : TypeToken<List<EmploymentType>>() {}.type
        )
    }
    override fun selfEmploymentTypes(): MutableList<EmploymentType> {
        val gson = GsonBuilder().create()
        return gson.fromJson<MutableList<EmploymentType>>(
            context.getJsonDataFromAsset(
                "jsons/self_employment_type.json"
            ), object : TypeToken<List<EmploymentType>>() {}.type
        )
    }

    override fun parseEmploymentTypes(employmentTypes: MutableList<EmploymentType>): MutableList<CoreBottomSheetData> {
        employmentTypes.forEach {
            it.subTitle = it.employmentType.trim()
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
            val a = selectedBusinessCountries.get()?.firstOrNull { selectedCountryName ->
                selectedCountryName == it.getName()
            }
            it.isSelected = a != null
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
        lyCountries: View,
        countries: ArrayList<String>,
        position: Int
    ) {

        /*Update countries recycler view adapter*/
        val rvCountries =
            lyCountries.findViewById<RecyclerView>(R.id.rvBusinessCountries)
        val objQuestion = getDataForPosition(position)
        objQuestion.question.multipleAnswers.get()?.clear()
        objQuestion.question.multipleAnswers.get()?.addAll(countries)
        // Only adding previous answers when its empty
        if (hasAmendmentMap() && objQuestion.question.multiplePreviousAnswers.get()
                ?.isEmpty() == true
        ) {
            objQuestion.question.multiplePreviousAnswers.get()?.addAll(countries)
        }
        questionsList[position] = objQuestion
        selectedBusinessCountries.get()?.clear()
        selectedBusinessCountries.get()?.addAll(countries)
        (rvCountries?.adapter as BusinessCountriesAdapter).setList(
            selectedBusinessCountries.get() ?: arrayListOf()
        )
        if (selectedBusinessCountries.get().isNullOrEmpty()) rvCountries.visibility =
            View.GONE else rvCountries.visibility = View.VISIBLE

        rvCountries.smoothScrollToPosition(rvCountries.adapter?.itemCount ?: 0)
        validateForm()
    }

    val employmentTypeItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val objQuestion = getDataForPosition(selectedQuestionItemPosition)
            val answerValue = when (data) {
                is EmploymentType -> data.employmentType
                is IndustrySegment -> data.segment
                else -> ""
            }
            objQuestion.question.answer.set(answerValue)
            questionsList[selectedQuestionItemPosition] = objQuestion
            validateForm()
        }
    }

    val rvQuestionItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            selectedQuestionItemPosition = pos
            when (view.id) {
                R.id.etAmount, R.id.etQuestionEditText -> validateForm()
            }
        }
    }

    override fun validateForm() {
        launch {
            delay(500)
            validator?.toValidate()
            validate()
        }
    }

    private fun validate() {
        var isValid = false
        questionsList.forEach {
            isValid = when (it.question.questionType) {
                QuestionType.COUNTRIES_FIELD -> {
                    var hasCountryError = true
                    if (it.question.multiplePreviousAnswers.get()
                            ?.isNotEmpty() == true && hasKeyInAmendmentMap(it.question.tag)
                    ) {
                        it.question.multiplePreviousAnswers.get()?.let { previousAnswersList ->
                            if (previousAnswersList.size == it.question.multipleAnswers.get()?.size) {
                                previousAnswersList.forEach { country ->
                                    if (it.question.multipleAnswers.get()
                                            ?.firstOrNull { it == country } == null
                                    ) {
                                        hasCountryError = false
                                        return@let
                                    }
                                }
                            } else {
                                hasCountryError = false
                                it.containsError.set(false)
                            }
                        }
                    } else {
                        hasCountryError = false
                        it.containsError.set(false)
                    }
                    if (hasCountryError) {
                        it.containsError.set(true)
                        false
                    } else it.question.multipleAnswers.get()
                        ?.isNotEmpty() == true
                }
                QuestionType.EDIT_TEXT_FIELD -> {
                    StringUtils.checkSpecialCharacters(it.question.answer.get() ?: "")
                }
                else -> it.question.answer.get().isNullOrBlank().not()
            }
            if (!isValid) {
                 validator?.isValidate?.value = isValid
                return@validate
            }
        }

        val depositQuestion =
            questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.DEPOSIT_AMOUNT }
        val depositAmount =
            depositQuestion?.getAnswer()
        if (depositQuestion != null && hasKeyInAmendmentMap(depositQuestion.question.tag)) {
            val previousDepositAmount =
                questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.DEPOSIT_AMOUNT }
                    ?.question?.previousValue
            if (depositAmount?.isNotBlank() == true && depositAmount == previousDepositAmount?.get()) {
                questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.DEPOSIT_AMOUNT }
                    ?.containsError?.set(true)
            } else {
                questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.DEPOSIT_AMOUNT }
                    ?.containsError?.set(false)
            }
        }

        val salaryQuestion =
            questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.SALARY_AMOUNT }
        val salaryAmount = salaryQuestion?.getAnswer()
        if (salaryQuestion != null && hasKeyInAmendmentMap(salaryQuestion.question.tag)) {
            val previousSalary =
                questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.SALARY_AMOUNT }?.question?.previousValue
            if (salaryAmount?.isNotBlank() == true && salaryAmount == previousSalary?.get()) {
                questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.SALARY_AMOUNT }
                    ?.containsError?.set(true)
            } else {
                questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.SALARY_AMOUNT }
                    ?.containsError?.set(false)
            }
        }
        validator?.isValidate?.value =
            isValid && salaryAmount.parseToDouble() > depositAmount.parseToDouble() && validator?.isValidate?.value == true
    }

    override fun hasKeyInAmendmentMap(key: String?): Boolean {
        if (key != null && parentViewModel?.amendmentMap != null) {
            parentViewModel?.amendmentMap?.let { it ->
                it.values.toList().forEach { it ->
                    it?.forEach {
                        if (key == it) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun fetchParallelAPIResponses(
        responses: (RetroApiResponse<CountryModel>, RetroApiResponse<IndustrySegmentsResponse>) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val deferredCountriesResponse = launchAsync {
                repository.getAllCountries()
            }
            val deferredIndustrySegmentsResponse = launchAsync {
                repository.getIndustrySegments()
            }
            responses(
                deferredCountriesResponse.await(),
                deferredIndustrySegmentsResponse.await()
            )
        }
    }

    override fun getCountriesAndSegments(
        businessCountries: ArrayList<String>?,
        segmentCode: String?
    ) {
        fetchParallelAPIResponses { countriesResponse, segmentsResponse ->
            launch(Dispatcher.Main) {
                when (countriesResponse) {
                    is RetroApiResponse.Success -> {
                        parentViewModel?.countries = Utils.parseCountryList(
                            countriesResponse.data.data,
                            addOIndex = false
                        ) as ArrayList<Country>
                        val businessCountriesList: ArrayList<String> = ArrayList()
                        if (isFromAmendment() && businessCountries != null) {
                            for (i in 0 until businessCountries.size) {
                                val businessCountry = parentViewModel?.countries?.filter {
                                    it.isoCountryCode2Digit.equals(businessCountries[i])
                                }?.get(0)?.getName() ?: ""
                                businessCountriesList.add(businessCountry)
                            }
                            selectedQuestionItemPosition = 3
                            businessCountriesLiveData.value = businessCountriesList
                        }
                    }
                    is RetroApiResponse.Error -> {
                        showDialogWithCancel(countriesResponse.error.message)
                    }
                }

                when (segmentsResponse) {
                    is RetroApiResponse.Success -> {
                        industrySegmentsList.clear()
                        industrySegmentsList.addAll(segmentsResponse.data.segments)

                        if (isFromAmendment() && segmentCode.isNullOrBlank().not()) {
                            val industrySegment = industrySegmentsList.first {
                                it.segmentCode == segmentCode
                            }
                            val objQuestion = getDataForPosition(2)
                            objQuestion.question.answer.set(industrySegment.segment)
                            objQuestion.question.previousValue.set(industrySegment.segment)
                            validateForm()
                        }
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
                    employerName = getDataForPosition(0).getAnswer(),
                    monthlySalary = getDataForPosition(1).getAnswer(),
                    expectedMonthlyCredit = getDataForPosition(2).getAnswer(),
                    isAmendment = isFromAmendment()
                )
            }
            EmploymentStatus.SALARIED_AND_SELF_EMPLOYED, EmploymentStatus.SELF_EMPLOYED -> {
                EmploymentInfoRequest(
                    employmentStatus = status.name,
                    companyName = getDataForPosition(0).getAnswer(),
                    typeOfSelfEmployment = selfEmploymentTypes().find {
                        it.employmentType == getDataForPosition(
                            1
                        ).getAnswer().trim()
                    }?.employmentTypeCode,
                    industrySegmentCodes = listOf(
                        industrySegmentsList.first {
                            it.segment == getDataForPosition(
                                2
                            ).getAnswer()
                        }.segmentCode ?: ""
                    ),
                    businessCountries = parentViewModel?.countries?.filterSelectedIsoCodes(
                        getDataForPosition(3).question.multipleAnswers.get() ?: arrayListOf()
                    ),
                    monthlySalary = getDataForPosition(4).getAnswer(),
                    expectedMonthlyCredit = getDataForPosition(5).getAnswer(),
                    isAmendment = isFromAmendment()
                )
            }
            EmploymentStatus.OTHER -> {
                EmploymentInfoRequest(
                    employmentStatus = status.name,
                    employmentType = employmentTypes().find {
                        it.employmentType == getDataForPosition(
                            0
                        ).getAnswer().trim()
                    }?.employmentTypeCode,
                    sponsorName = getDataForPosition(1).getAnswer(),
                    monthlySalary = getDataForPosition(3).getAnswer(),
                    expectedMonthlyCredit = getDataForPosition(4).getAnswer(),
                    employmentTypeValue = employmentTypes().find {
                        it.employmentType == getDataForPosition(
                            0
                        ).getAnswer().trim()
                    }?.employmentType,
                    isAmendment = isFromAmendment()

                )
            }
            EmploymentStatus.NONE -> TODO()
        }
    }

    override fun getDataForPosition(position: Int): QuestionUiFields {
        return questionsList[position]
    }

    override fun hasAmendmentMap() = parentViewModel?.amendmentMap?.isNullOrEmpty() == false

    override fun getAmendmentsEmploymentInfo() {
        launch {
            state.loading = true
            when (val response =
                repository.getAmendmentsEmploymentInfo(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    response.data.data?.let { res ->
                        if (employmentStatus == EmploymentStatus.NONE)
                            employmentStatus = EmploymentStatus.valueOf(res.employmentStatus ?: "")
                        employmentStatusValue.value = res

                        if (employmentStatus == EmploymentStatus.SALARIED_AND_SELF_EMPLOYED ||
                            employmentStatus == EmploymentStatus.SELF_EMPLOYED
                        ) {
                            isDataRequiredFromApi(
                                employmentStatus,
                                res.businessCountries,
                                res.industrySubSegmentCode?.get(0) ?: ""
                            )
                        } else if (employmentStatus == EmploymentStatus.OTHER) {
                            selectedQuestionItemPosition = 0
                            val objQuestion = getDataForPosition(selectedQuestionItemPosition)
                            objQuestion.question.answer.set(res.employmentTypeValue ?: "")
                            questionsList[selectedQuestionItemPosition] = objQuestion
                            if (objQuestion.question.questionType == QuestionType.DROP_DOWN_FIELD)
                                objQuestion.question.previousValue.set(objQuestion.question.answer.get())
                            validateForm()
                        } else {
                            isDataRequiredFromApi(employmentStatus)
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun onValidationSuccess(validator: Validator) {
        super.onValidationSuccess(validator)
        // state.ruleValid = true
        //  validate()
    }

    override fun onValidationError(validator: Validator) {
        super.onValidationError(validator)
        // state.ruleValid = false
    }

    //check if Amendment exist or not
    override fun isFromAmendment() = parentViewModel?.amendmentMap?.isNullOrEmpty() == false
}
