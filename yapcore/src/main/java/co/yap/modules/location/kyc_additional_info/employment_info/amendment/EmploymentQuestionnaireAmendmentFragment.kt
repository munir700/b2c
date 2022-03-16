package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.unSelectAllCountries
import co.yap.modules.document.ViewDocumentFragment
import co.yap.modules.document.enums.FileFrom
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.QuestionItemViewHolders
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.networking.customers.responsedtos.employment_amendment.DocumentResponse
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentFieldType
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegment
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.bottomsheet.IAnimationComplete
import co.yap.widgets.skeletonlayout.views
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingImageFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireAmendmentBinding
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.FileUtils
import co.yap.yapcore.helpers.beneficiaryInfoDialog
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.infoDialog
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import com.liveperson.infra.utils.UIUtils
import pl.aprilapps.easyphotopicker.MediaFile
import java.io.File


class EmploymentQuestionnaireAmendmentFragment :
    BaseBindingImageFragment<IEmploymentQuestionnaireAmendment.ViewModel>(),
    IEmploymentQuestionnaireAmendment.View, IAnimationComplete {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_employment_questionnaire_amendment
    override val viewModel: EmploymentQuestionnaireAmendmentViewModel
        get() = ViewModelProvider(this).get(EmploymentQuestionnaireAmendmentViewModel::class.java)

    override fun showInfoDialog(title: String, message: String) {
        requireContext().infoDialog(
            title = title,
            message = message
        )
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.finish()
            }
            R.id.tvRightText -> {
                startFragmentForResult<EmploymentQuestionnaireAmendmentFragment>(
                    EmploymentQuestionnaireAmendmentFragment::class.java.name,
                    bundleOf(
                        "countries" to viewModel.countries,
                        "segments" to viewModel.industrySegmentsList,
                        "empStatus" to viewModel.employmentStatusValue.value,
                        "documentsList" to viewModel.requiredDocumentsResponse.value
                    ), false
                ) { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        activity?.finish()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<FragmentEmploymentQuestionnaireAmendmentBinding>().lifecycleOwner = this
        viewModel.documentAdapter.onItemClickListener = documentListener
        arguments?.let {
            viewModel.countries =
                it.getParcelableArrayList<Country>("countries") as? ArrayList<Country>
                    ?: arrayListOf()
            viewModel.industrySegmentsList.addAll(
                it.getParcelableArrayList<IndustrySegment>("segments") as? ArrayList<IndustrySegment>
                    ?: arrayListOf()
            )
            viewModel.employmentStatusValue.value =
                it.getParcelable("empStatus") as? EmploymentInfoAmendmentResponse
            viewModel.employmentStatusValue.value?.let { empResp ->
                viewModel.serverEmploymentStatus =
                    EmploymentStatus.valueOf(
                        empResp.employmentStatus ?: ""
                    )
                viewModel.employmentStatus.value = viewModel.serverEmploymentStatus
                viewModel.updateEditMode(true)
            }
            viewModel.requiredDocumentsResponse.value =
                (it.getParcelableArrayList<DocumentResponse>("documentsList") as? ArrayList<DocumentResponse>
                    ?: arrayListOf()).toMutableList()
            viewModel.employmentStatusValue.value?.let { emp ->
                viewModel.documentsList.value = emp.documents?.toMutableList() ?: mutableListOf()
                if (viewModel.documentsList.value.isNullOrEmpty()) {
                    viewModel.updateDocumentsInView(
                        EmploymentStatus.valueOf(
                            emp.employmentStatus ?: ""
                        )
                    )
                }
                viewModel.fillTitlesOfDocuments(
                    EmploymentStatus.valueOf(
                        emp.employmentStatus ?: ""
                    )
                )
            }
        }
        if (viewModel.countries.isEmpty() && viewModel.industrySegmentsList.isEmpty()) {
            viewModel.getAllApiCallsInParallelForScreen()
        }
    }

    private fun initQuestionViews() {
        viewModel.unselectDocuments()
        viewModel.selectedBusinessCountries.get()?.clear()
        viewModel.questionsList.clear()
        val empStatus = viewModel.employmentStatus.value ?: EmploymentStatus.EMPLOYED
        viewModel.questionsList.addAll(
            viewModel.questionnaires(
                empStatus,
                viewModel.getEmploymentResponse(empStatus)
            )
        )
        getDataBindingView<FragmentEmploymentQuestionnaireAmendmentBinding>().llQuestions.removeAllViews()
        val questionItemViewHolders = QuestionItemViewHolders()
        viewModel.questionsList.forEachIndexed { position, questionUiField ->
            val questionView: View?
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater,
                    questionItemViewHolders.getItemViewType(questionUiField.question.questionType),
                    null,
                    false
                )
            questionView = questionItemViewHolders.getViewFromBinding(
                binding,
                questionUiField,
                position,
                listener,
                viewModel.selectedBusinessCountries.get() ?: arrayListOf(),
                null,
                viewModel.isInEditMode
            )
            if (questionView != null)
                getDataBindingView<FragmentEmploymentQuestionnaireAmendmentBinding>().llQuestions.addView(
                    questionView
                )
            binding.lifecycleOwner = this
            binding.executePendingBindings()
            // Adding Observer for Employer
            if (position == 0) {
                questionUiField.question.answer.addOnPropertyChangedCallback(object :
                    Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        viewModel.onSalaryOrEmployerUpdate(
                            viewModel.employmentStatus.value ?: EmploymentStatus.NONE,
                            EmploymentFieldType.EMPLOYER_NAME
                        )
                    }
                })
            }
            // Adding Observer for Salary
            if (position == viewModel.questionsList.size - 2) {
                questionUiField.question.answer.addOnPropertyChangedCallback(object :
                    Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        viewModel.getSalaryAndMonthlyCredit()
                        viewModel.onSalaryOrEmployerUpdate(
                            viewModel.employmentStatus.value ?: EmploymentStatus.NONE,
                            EmploymentFieldType.SALARY
                        )
                    }
                })
            }
            // Adding Observer for Monthly Credit
            if (position == viewModel.questionsList.size - 1) {
                questionUiField.question.answer.addOnPropertyChangedCallback(object :
                    Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                        viewModel.getSalaryAndMonthlyCredit()
                    }
                })
            }
        }
        viewModel.setAnswersForQuestions()
        getDataBindingView<FragmentEmploymentQuestionnaireAmendmentBinding>().llQuestions.post {
            viewModel.validator?.targetViewBinding =
                getDataBindingView<FragmentEmploymentQuestionnaireBinding>()
            viewModel.validateForm()
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnSubmit -> {
                startOtpFragment()
            }
            R.id.tvEmploymentStatusDropDown -> {
                UIUtils.hideKeyboard(requireActivity())
                openAmendmentEmploymentTypeBottomSheet()
            }
        }
    }

    private val employmentTypeLoadedObserver =
        Observer<EmploymentStatus> {
            initQuestionViews()
        }

    private val businessCountriesLiveDataObserver =
        Observer<ArrayList<String>> {
            onBusinessCountriesSelection(it)
        }

    private val documentsLiveDataObserver =
        Observer<List<Document>> {
            viewModel.documentAdapter.setData(it.toMutableList())
            viewModel.validateForm()
        }

    private val documentListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Document) {
                viewModel.posOfUpdatedDocument = pos
                if (data.fileURL.isNullOrEmpty()) {
                    showDialogueOptions()
                } else {
                    context?.let {
                        startFragmentForResult<ViewDocumentFragment>(
                            ViewDocumentFragment::class.java.name,
                            bundleOf(
                                "LINK" to data.fileURL,
                                "FILETYPE" to data.extension,
                                "ISEDITABLE" to viewModel.isInEditMode.value,
                                "DOCUMENTTYPE" to data.documentType
                            ), false
                        ) { resultCode, data ->
                            if (resultCode == Activity.RESULT_OK) {
                                data?.let {
                                    handleFileResult(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private val needToShowAdditionalDocumentDialogue =
        Observer<Boolean> {
            if (it) {
                openAdditionallyDocumentConfirmationDialogue()
            }
        }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.rvQuestionItemListener.onItemClick(view, data, pos)
            when (view.id) {
                R.id.ivSupport -> {
                    viewModel.onInfoClick(data as QuestionUiFields) { title, message ->
                        showInfoDialog(title, message)
                    }
                }

                R.id.searchCountries -> {
                    UIUtils.hideKeyboard(requireActivity())
                    requireActivity().launchMultiSelectionBottomSheet(
                        itemClickListener = object : OnItemClickListener {
                            override fun onItemClick(view: View, data: Any, pos: Int) {
                                onBusinessCountriesSelection(data as ArrayList<String>)
                                viewModel.validateForm()
                            }
                        }, configuration = BottomSheetConfiguration(
                            heading = "Add all the countries your company does business with:",
                            showSearch = true,
                            showHeaderSeparator = true
                        ),
                        countriesList = viewModel.getSelectedStateCountries(
                            viewModel.countries
                        )
                    )
                }

                R.id.tvDropDown -> {
                    UIUtils.hideKeyboard(requireActivity())
                    when ((data as QuestionUiFields).key) {
                        EmploymentQuestionIdentifier.EMPLOYMENT_TYPE -> openEmploymentTypeBottomSheet()
                        EmploymentQuestionIdentifier.INDUSTRY_SEGMENT -> openSegmentsBottomSheet()
                        EmploymentQuestionIdentifier.SELF_EMPLOYMENT -> openSelfEmploymentTypeBottomSheet()
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun onBusinessCountriesSelection(list: ArrayList<String>) {
        val viewCountriesLayout =
            getDataBindingView<FragmentEmploymentQuestionnaireAmendmentBinding>().llQuestions.views()[viewModel.selectedQuestionItemPosition]
        viewModel.setBusinessCountries(
            viewCountriesLayout,
            list,
            viewModel.selectedQuestionItemPosition
        )
    }

    private fun openEmploymentTypeBottomSheet() {
        launchBottomSheetSegment(
            viewModel.employmentTypeItemClickListener,
            configuration = BottomSheetConfiguration(heading = getString(Strings.screen_employment_questionnaire_display_text__bottom_sheet_title_describe_you)),
            viewType = Constants.VIEW_WITHOUT_FLAG,
            listData = viewModel.parseEmploymentTypes(viewModel.employmentTypes())
        )
    }

    private fun openAmendmentEmploymentTypeBottomSheet() {
        launchBottomSheetSegment(
            viewModel.employmentStatusItemClickListener,
            configuration = BottomSheetConfiguration(heading = getString(Strings.screen_employment_type_display_text_bottom_sheet_title)),
            viewType = Constants.VIEW_WITHOUT_FLAG,
            listData = viewModel.getEmploymentTypesList()
        )
    }

    private fun openSegmentsBottomSheet() {
        launchBottomSheetSegment(
            viewModel.employmentTypeItemClickListener,
            configuration = BottomSheetConfiguration(heading = getString(Strings.screen_employment_questionnaire_display_text__bottom_sheet_title_segments)),
            viewType = Constants.VIEW_FIXED_HEIGHT,
            listData = viewModel.parseSegments(viewModel.industrySegmentsList)
        )
    }

    private fun openSelfEmploymentTypeBottomSheet() {
        launchBottomSheetSegment(
            viewModel.employmentTypeItemClickListener,
            configuration = BottomSheetConfiguration(heading = getString(Strings.screen_employment_questionnaire_display_text__bottom_sheet_title_self_employment)),
            viewType = Constants.VIEW_WITHOUT_FLAG,
            listData = viewModel.parseEmploymentTypes(viewModel.selfEmploymentTypes())
        )
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.employmentStatus.observe(this, employmentTypeLoadedObserver)
        viewModel.businessCountriesLiveData.observe(this, businessCountriesLiveDataObserver)
        viewModel.documentsList.observe(this, documentsLiveDataObserver)
        viewModel.state.needToShowAdditionalDocumentDialogue.observe(
            this,
            needToShowAdditionalDocumentDialogue
        )
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
        viewModel.employmentStatus.removeObserver(employmentTypeLoadedObserver)
        viewModel.businessCountriesLiveData.removeObserver(businessCountriesLiveDataObserver)
        viewModel.documentsList.removeObserver(documentsLiveDataObserver)
        viewModel.state.needToShowAdditionalDocumentDialogue.removeObserver(
            needToShowAdditionalDocumentDialogue
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        viewModel.countries.unSelectAllCountries(
            viewModel.selectedBusinessCountries.get() ?: arrayListOf()
        )
    }

    fun openAdditionallyDocumentConfirmationDialogue() {
        context?.beneficiaryInfoDialog(
            title = "Additional documents\n" +
                    "required.",
            message = "Since you’re self-employed we will need to ask you to provide additional documentation.",
            buttonText = "Back",
            callback = { proceed ->
                if (proceed) {
                    viewModel.employmentStatus.value = viewModel.tempEmploymentStatus.value
                    viewModel.previousEmploymentStatus.value = viewModel.tempEmploymentStatus.value
                    viewModel.updateDocumentsInView(
                        viewModel.employmentStatus.value ?: EmploymentStatus.NONE
                    )
                    viewModel.validateForm()
                } else {
                    requireActivity().finish()
                }
            },
            icon = R.drawable.ic_exclamation_primary_white,
            coreButtonTitle = "Continue"
        )
    }

    private fun handleFileResult(data: Intent?) {
        val fileForUpdate = data?.getSerializableExtra(ExtraKeys.FILE_FOR_UPDATE.name) as File
        updateDocumentLists(fileForUpdate)
    }

    private fun startOtpFragment() {
        var mobileNumber =
            SessionManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    OTPActions.EMPLOYMENT_AMENDMENT.name,
                    otpMessage = "Hi, your OTP for your employment information update is $mobileNumber. Please do not share your OTP with anyone. For help, contact our customer support team on 0600551214",
                    mobileNumber = mobileNumber
                )
            ),
            showToolBar = true
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.saveEmploymentInfo(
                    viewModel.getEmploymentInfoRequest(
                        viewModel.employmentStatus.value ?: EmploymentStatus.EMPLOYED
                    )
                )
                {
                    openCardSuccessBottomSheet()
                }
            }
        }
    }

    private fun showDialogueOptions() {
        activity?.launchSheet(
            itemClickListener = itemListener,
            itemsList = viewModel.getUploadDocumentOptions(),
            heading = getString(R.string.choose_from_library_display_text_title)
        )
    }

    private val itemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when ((data as BottomSheetItem).tag) {
                PhotoSelectionType.CAMERA.name -> {
                    openImagePicker(PhotoSelectionType.CAMERA)
                }
                PhotoSelectionType.GALLERY.name -> {
                    requireActivity().openFilePicker("File picker",
                        completionHandler = { _, dataUri ->
                            dataUri?.let { uriIntent ->
                                var fileSelected = FileUtils.getFile(context, uriIntent.data)
                                if (fileSelected.sizeInMb() < 25) {
                                    fileSelected?.let {
                                        updateDocumentLists(
                                            it
                                        )
                                    }
                                } else {
                                    showToast(getString(R.string.screen_view_document_file_size_not_fine))
                                }

                            }
                        })
                }
            }
        }
    }


    override fun onImageReturn(mediaFile: MediaFile) {
        if (mediaFile.file.sizeInMb() < 25) {
            updateDocumentLists(
                mediaFile.file
            )
        } else {
            showToast(getString(R.string.screen_view_document_file_size_not_fine))
        }
    }

    fun updateDocumentLists(fileForUpdate: File?) {
        viewModel.posOfUpdatedDocument?.let {
            viewModel.documentsList.value?.get(it)?.fileURL = fileForUpdate?.absolutePath ?: ""
            viewModel.documentsList.value?.get(it)?.extension = fileForUpdate?.extension ?: ""
            viewModel.documentsList.value?.get(it)?.fileForUpdate = fileForUpdate
            viewModel.documentAdapter.update(viewModel.documentsList.value?.get(it) ?: Document())
            viewModel.validateForm()
        }
    }

    private fun openCardSuccessBottomSheet() {
        var list: MutableList<CoreBottomSheetData> = mutableListOf()
        list.add(CoreBottomSheetData(subTitle = Strings.screen_employment_document_updation_completion_display_text_complete))
        launchBottomSheetSegment(
            cardBottomSheetItemClickListener,
            configuration = BottomSheetConfiguration(
                heading = ""
            ),
            viewType = Constants.VIEW_ITEM_CARD_SUCCESSS,
            listData = list,
            isIAnimationComplete = this
        )
    }

    private val cardBottomSheetItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is CoreBottomSheetData) {
                when (view.id) {
                }
            }
        }
    }

    override fun onAnimationComplete(isComplete: Boolean) {
        if (isComplete) {
            val intent = Intent()
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }
}