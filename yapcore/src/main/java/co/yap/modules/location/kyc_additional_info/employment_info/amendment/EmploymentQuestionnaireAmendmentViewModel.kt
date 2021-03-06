package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.filterSelectedIsoCodes
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.ComplianceQuestionsItemsComposer
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.KYCComplianceComposer
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.BusinessCountriesAdapter
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.EmploymentType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.location.viewmodels.LocationChildViewModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.EmploymentInfoRequest
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.networking.customers.responsedtos.employment_amendment.DocumentResponse
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentFieldType
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegment
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegmentsResponse
import co.yap.networking.customers.responsedtos.sendmoney.CountryModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.*
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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EmploymentQuestionnaireAmendmentViewModel(application: Application) :
    LocationChildViewModel<IEmploymentQuestionnaireAmendment.State>(application),
    IEmploymentQuestionnaireAmendment.ViewModel, IRepositoryHolder<CustomersRepository>, IValidator,
    Validator.ValidationListener {
    override val repository: CustomersRepository = CustomersRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IEmploymentQuestionnaireAmendment.State =
        EmploymentQuestionnaireAmendmentState()
    override var selectedQuestionItemPosition: Int = -1
    override val industrySegmentsList: ArrayList<IndustrySegment> = arrayListOf()
    override var employmentStatus = MutableLiveData<EmploymentStatus>()
    var tempEmploymentStatus = MutableLiveData<EmploymentStatus>()
    var previousEmploymentStatus = MutableLiveData<EmploymentStatus>()
    override var serverEmploymentStatus: EmploymentStatus? = null
    override val selectedBusinessCountries: ObservableField<ArrayList<String>> =
        ObservableField(arrayListOf())
    override var questionsList: ArrayList<QuestionUiFields> = arrayListOf()
    override var employmentStatusValue: MutableLiveData<EmploymentInfoAmendmentResponse> =
        MutableLiveData()
    override var requiredDocumentsResponse: MutableLiveData<MutableList<DocumentResponse>> =
        MutableLiveData()
    override var validator: Validator? = Validator(null)
    override var businessCountriesLiveData: MutableLiveData<ArrayList<String>> =
        MutableLiveData()
    override var countries: ArrayList<Country> = arrayListOf()
    override var accountActivated: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isInEditMode: MutableLiveData<Boolean> = MutableLiveData(false)
    override val documentsList: MutableLiveData<MutableList<Document>> = MutableLiveData()
    override val documentAdapter = DocumentsAdapter(mutableListOf(), null)
    override var salaryAmount: String? = null
    override var monthlyCreditAmount: String? = null
    override var posOfUpdatedDocument: Int? = null
    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun updateEditMode(isEditable: Boolean) {
        isInEditMode.value = isEditable
        if (isEditable) {
            validate()
        }
    }

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle =
            getString(Strings.screen_profile_settings_display_text_employment_information)
        state.rightButtonText =
            getString(Strings.screen_employment_information_display_right_toolbar_text)
        validator?.setValidationListener(this)
        accountActivated.value =
            SessionManager.user?.partnerBankStatus == PartnerBankStatus.ACTIVATED.status && SessionManager.card.value?.pinCreated == true
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
        if (objQuestion.question.multiplePreviousAnswers.get()
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

    val employmentStatusItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (previousEmploymentStatus.value == null) {
                previousEmploymentStatus.value = employmentStatus.value
            }

            (data as? CoreBottomSheetData)?.subTitle.also { selectedType ->
                tempEmploymentStatus.value = EmploymentStatus.values().find {
                    it.status == selectedType
                } ?: EmploymentStatus.EMPLOYED
            }
            showAdditionalDialogue()
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

    fun showAdditionalDialogue() {
        if (tempEmploymentStatus.value?.equals(EmploymentStatus.SELF_EMPLOYED) == true) {
            if (previousEmploymentStatus.value?.equals(EmploymentStatus.EMPLOYED) == true || previousEmploymentStatus.value?.equals(
                    EmploymentStatus.OTHER
                ) == true
            ) {
                state.needToShowAdditionalDocumentDialogue.value =
                    tempEmploymentStatus.value?.equals(EmploymentStatus.SELF_EMPLOYED)
            } else {
                handlePostEmploymentChange()
            }
        } else if (tempEmploymentStatus.value?.equals(EmploymentStatus.SALARIED_AND_SELF_EMPLOYED) == true) {
            if (previousEmploymentStatus.value?.equals(EmploymentStatus.EMPLOYED) == true || previousEmploymentStatus.value?.equals(
                    EmploymentStatus.OTHER
                ) == true
            ) {
                state.needToShowAdditionalDocumentDialogue.value =
                    tempEmploymentStatus.value?.equals(EmploymentStatus.SALARIED_AND_SELF_EMPLOYED)
            } else {
                handlePostEmploymentChange()
            }
        } else {
            handlePostEmploymentChange()
        }
    }

    private fun handlePostEmploymentChange() {
        employmentStatus.value = tempEmploymentStatus.value
        previousEmploymentStatus.value = tempEmploymentStatus.value
        updateDocumentsInView(employmentStatus.value ?: EmploymentStatus.NONE)
        validateForm()
    }

    override fun validateForm() {
        launch {
            validator?.toValidate()
            validator?.isValidate?.value = false
            delay(500)
            validate()
        }
    }

    private fun validate() {
        var isValid = false
        questionsList.forEach {
            isValid = when (it.question.questionType) {
                QuestionType.COUNTRIES_FIELD -> {
                    it.question.multipleAnswers.get()
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

        val salaryQuestion =
            questionsList.firstOrNull { it.key == EmploymentQuestionIdentifier.SALARY_AMOUNT }
        val salaryAmount = salaryQuestion?.getAnswer()

        val documentsValid =
            documentsList.value?.find { it.isMandatory && it.fileURL == null } == null

        validator?.isValidate?.value =
            isValid && documentsValid && salaryAmount.parseToDouble() > depositAmount.parseToDouble() && isInEditMode.value == true && state.ruleValid == true
    }

    private fun fetchParallelAPIResponses(
        responses: (RetroApiResponse<CountryModel>, RetroApiResponse<IndustrySegmentsResponse>, RetroApiResponse<BaseResponse<EmploymentInfoAmendmentResponse>>, RetroApiResponse<BaseListResponse<DocumentResponse>>) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val deferredCountriesResponse = launchAsync {
                repository.getAllCountries()
            }
            val deferredIndustrySegmentsResponse = launchAsync {
                repository.getIndustrySegments()
            }
            val deferredEmploymentStatusResponse = launchAsync {
                repository.getEmploymentInfo()
            }
            val deferredEmploymentProofDocuments = launchAsync {
                repository.getAllDocumentsForEmploymentAmendment()
            }
            responses(
                deferredCountriesResponse.await(),
                deferredIndustrySegmentsResponse.await(),
                deferredEmploymentStatusResponse.await(),
                deferredEmploymentProofDocuments.await()
            )
        }
    }

    override fun getAllApiCallsInParallelForScreen() {
        fetchParallelAPIResponses { countriesResponse, segmentsResponse, employmentResponse, documentResponse ->
            launch(Dispatcher.Main) {
                when (countriesResponse) {
                    is RetroApiResponse.Success -> {
                        countries = Utils.parseCountryList(
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

                when (employmentResponse) {
                    is RetroApiResponse.Success -> {
                        if (employmentResponse.data.data == null) {
                            // Manually adding EMPLOYED & sending empty response
                            employmentStatusValue.value =
                                EmploymentInfoAmendmentResponse(employmentStatus = EmploymentStatus.EMPLOYED.name)
                            employmentStatus.value = EmploymentStatus.EMPLOYED
                            state.needToShowEmploymentStatusBottomSheet.value = true
                        } else {
                            employmentResponse.data.data?.let { res ->
                                employmentStatusValue.value = res
                                salaryAmount = res.monthlySalary
                                monthlyCreditAmount = res.expectedMonthlyCredit
                                serverEmploymentStatus =
                                    EmploymentStatus.valueOf(res.employmentStatus ?: "")
                                employmentStatus.value = serverEmploymentStatus
                                documentsList.value =
                                    res.documents?.toMutableList() ?: mutableListOf()
                            }
                        }
                    }
                    is RetroApiResponse.Error -> {
                        showDialogWithCancel(employmentResponse.error.message)
                    }
                }

                when (documentResponse) {
                    is RetroApiResponse.Success -> {
                        requiredDocumentsResponse.value = documentResponse.data.data
                        fillTitlesOfDocuments(
                            EmploymentStatus.valueOf(
                                employmentStatusValue.value?.employmentStatus
                                    ?: EmploymentStatus.EMPLOYED.name
                            )
                        )
                    }
                    is RetroApiResponse.Error -> {
                        showDialogWithCancel(documentResponse.error.message)
                    }
                }
                state.viewState.value = false
            }
        }
    }

    override fun fillTitlesOfDocuments(status: EmploymentStatus) {
        val document = requiredDocumentsResponse.value?.find { it.empType == status.name }
        document?.let {
            documentsList.value?.forEach { doc ->
                it.documents.find { it.documentType == doc.documentType }?.let {
                    doc.title = it.title
                    doc.description = it.description
                }
            }
        }
    }

    override fun updateDocumentsInView(status: EmploymentStatus) {
        val docList = mutableListOf<Document>()
        requiredDocumentsResponse.value?.find { it.empType == status.name }?.let {
            docList.addAll(it.documents.toMutableList())
        }
        if (status == serverEmploymentStatus && employmentStatusValue.value?.documents?.isNotEmpty() == true) {
            docList.forEach { doc ->
                doc.fileURL =
                    employmentStatusValue.value?.documents?.first { it.documentType == doc.documentType }?.fileURL
            }
        }
        documentsList.value = docList
    }

    override fun unselectDocuments() {
        requiredDocumentsResponse.value?.forEach {
            it.documents.forEach { doc ->
                doc.fileURL = null
            }
        }
    }

    override fun onSalaryOrEmployerUpdate(
        status: EmploymentStatus,
        fieldType: EmploymentFieldType
    ) {
        val docs = requiredDocumentsResponse.value?.find { it.onChange == fieldType.name }
        docs?.let {
            documentsList.value?.forEach { doc ->
                it.documents.find { it.documentType == doc.documentType }?.let {
                    doc.fileURL = null
                }
            }
        }
        documentAdapter.setData(documentsList.value ?: mutableListOf())
    }

    override fun setAnswersForQuestions() {
        if (serverEmploymentStatus != employmentStatus.value)
            return
        when (employmentStatus.value) {
            EmploymentStatus.SALARIED_AND_SELF_EMPLOYED, EmploymentStatus.SELF_EMPLOYED -> {
                employmentStatusValue.value?.let { emp ->
                    val empType = selfEmploymentTypes().find {
                        it.employmentTypeCode == emp.typeOfSelfEmployment
                    }
                    val objQuestionSegment = getDataForPosition(1)
                    objQuestionSegment.question.answer.set(empType?.employmentTypeCode ?: "")
                }
                employmentStatusValue.value?.industrySubSegmentCode?.let {
                    val industrySegment = industrySegmentsList.first {
                        it.segmentCode == employmentStatusValue.value?.industrySubSegmentCode?.get(0) ?: ""
                    }
                    val objQuestionSegment = getDataForPosition(2)
                    objQuestionSegment.question.answer.set(industrySegment.segment)
                }
                // Check Selected Countries
                val businessCountriesList: ArrayList<String> = ArrayList()
                employmentStatusValue.value?.businessCountries?.let { selectedCountries ->
                    for (i in 0 until selectedCountries.size) {
                        businessCountriesList.add(countries.firstOrNull() {
                            it.isoCountryCode2Digit.equals(selectedCountries[i])
                        }?.getName() ?: "")
                    }
                    selectedQuestionItemPosition = 3
                    businessCountriesLiveData.value = businessCountriesList
                }
            }
            EmploymentStatus.OTHER -> {
                selectedQuestionItemPosition = 0
                val objQuestion =
                    getDataForPosition(selectedQuestionItemPosition)
                objQuestion.question.answer.set(employmentTypes().firstOrNull {
                    it.employmentTypeCode == employmentStatusValue.value?.employmentType ?: ""
                }?.employmentType ?: "")
                questionsList[selectedQuestionItemPosition] = objQuestion
            }
            else -> {
            }
        }
    }

    override fun getSalaryAndMonthlyCredit() {
        salaryAmount = getDataForPosition(questionsList.size - 2).getAnswer()
        monthlyCreditAmount = getDataForPosition(questionsList.size - 1).getAnswer()
    }

    override fun saveEmploymentInfo(
        employmentInfoRequest: EmploymentInfoRequest,
        success: () -> Unit
    ) {
        val files = ArrayList<MultipartBody.Part>()
        var documentTypeList = ArrayList<String>()
        documentsList.value?.forEach {
            if (it.fileURL != null && it.fileURL?.contains("http") != true) {
                val file = it.fileForUpdate
                val reqFile: RequestBody = if (file?.extension?.contains("pdf") == true) {
                    RequestBody.create(MediaType.parse("application/pdf"), file)
                } else {
                    RequestBody.create(MediaType.parse("image/" + file?.extension), file)
                }
                val body = MultipartBody.Part.createFormData("files", file?.name, reqFile)
                files.add(body)
                documentTypeList.add(it.documentType ?: "")
            }
        }
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.saveEmploymentInfoWithDocument(
                employmentInfoRequest = employmentInfoRequest,
                files = files,
                documentTypeList = documentTypeList
            )
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
                    isAmendment = false
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
                    businessCountries = countries.filterSelectedIsoCodes(
                        getDataForPosition(3).question.multipleAnswers.get() ?: arrayListOf()
                    ),
                    monthlySalary = getDataForPosition(4).getAnswer(),
                    expectedMonthlyCredit = getDataForPosition(5).getAnswer(),
                    isAmendment = false
                )
            }
            EmploymentStatus.OTHER -> {
                EmploymentInfoRequest(
                    employmentStatus = status.name,
                    employmentType = employmentTypes().first {
                        it.employmentType == getDataForPosition(
                            0
                        ).getAnswer().trim()
                    }.employmentTypeCode,
                    sponsorName = getDataForPosition(1).getAnswer(),
                    monthlySalary = getDataForPosition(3).getAnswer(),
                    expectedMonthlyCredit = getDataForPosition(4).getAnswer(),
                    isAmendment = false
                )
            }
            EmploymentStatus.NONE -> TODO()
        }
    }

    override fun getEmploymentResponse(currentEmploymentStatus: EmploymentStatus): EmploymentInfoAmendmentResponse? =
        if (currentEmploymentStatus == serverEmploymentStatus) employmentStatusValue.value else EmploymentInfoAmendmentResponse(
            monthlySalary = salaryAmount,
            expectedMonthlyCredit = monthlyCreditAmount
        )

    override fun getDataForPosition(position: Int): QuestionUiFields {
        return questionsList[position]
    }

    override fun getEmploymentTypesList(): MutableList<CoreBottomSheetData> =
        mutableListOf<CoreBottomSheetData>().apply {
            add(CoreBottomSheetData(subTitle = EmploymentStatus.EMPLOYED.status))
            add(CoreBottomSheetData(subTitle = EmploymentStatus.SELF_EMPLOYED.status))
            add(CoreBottomSheetData(subTitle = EmploymentStatus.SALARIED_AND_SELF_EMPLOYED.status))
            add(CoreBottomSheetData(subTitle = EmploymentStatus.OTHER.status))
        }

    override fun getUploadDocumentOptions(): java.util.ArrayList<BottomSheetItem> {
        val list = arrayListOf<BottomSheetItem>()
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_camera,
                title = getString(Strings.screen_update_profile_photo_display_text_open_camera),
                subTitle = getString(Strings.screen_upload_documents_display_sheet_text_scan_single_document),
                tag = PhotoSelectionType.CAMERA.name
            )
        )
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_file_manager,
                title = getString(Strings.screen_upload_documents_display_sheet_text_upload_from_files),
                subTitle = getString(Strings.screen_upload_documents_display_sheet_text_upload_from_files_descriptions),
                tag = PhotoSelectionType.GALLERY.name
            )
        )
        return list
    }

    override fun onValidationSuccess(validator: Validator) {
        super.onValidationSuccess(validator)
        state.ruleValid = true
    }

    override fun onValidationError(validator: Validator) {
        super.onValidationError(validator)
        state.ruleValid = false
    }
}