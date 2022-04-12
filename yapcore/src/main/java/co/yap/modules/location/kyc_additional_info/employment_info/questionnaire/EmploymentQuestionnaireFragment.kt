package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.countryutils.country.unSelectAllCountries
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.QuestionItemViewHolders
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.skeletonlayout.views
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.ButtonType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.launchBottomSheetSegment
import co.yap.yapcore.helpers.extentions.launchMultiSelectionBottomSheet
import co.yap.yapcore.helpers.extentions.showInfoDialog
import co.yap.yapcore.helpers.infoDialog
import co.yap.yapcore.interfaces.OnItemClickListener
import com.liveperson.infra.utils.UIUtils.hideKeyboard

class EmploymentQuestionnaireFragment : LocationChildFragment<FragmentEmploymentQuestionnaireBinding , IEmploymentQuestionnaire.ViewModel>(),
    IEmploymentQuestionnaire.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_employment_questionnaire
    override val viewModel: EmploymentQuestionnaireViewModel
        get() = ViewModelProvider(this).get(EmploymentQuestionnaireViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        viewModel.employmentStatus = arguments?.get(ExtraKeys.EMPLOYMENT_STATUS.name) as EmploymentStatus
        viewModel.isDataRequiredFromApi(forStatus = viewModel.employmentStatus)

        if (arguments?.containsKey("EMPLOYMENT_STATUS") == true) viewModel.employmentStatus =
            arguments?.get("EMPLOYMENT_STATUS") as EmploymentStatus
        if (!viewModel.hasAmendmentMap()) {
            viewModel.employmentStatus = arguments?.get("EMPLOYMENT_STATUS") as EmploymentStatus
            viewModel.isDataRequiredFromApi(forStatus = viewModel.employmentStatus)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.hasAmendmentMap()) {
            initQuestionViews()
        }
        getDataBindingView<FragmentEmploymentQuestionnaireBinding>().lifecycleOwner = this
    }

    private fun initQuestionViews() {
        viewModel.questionsList.addAll(
            viewModel.questionnaires(
                viewModel.employmentStatus,
                viewModel.employmentStatusValue.value
            )
        )
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
                viewModel.parentViewModel?.amendmentMap
            )
            if (questionView != null)
                getBinding().llQuestions.addView(questionView)
        }
        getBinding().llQuestions.post {
            viewModel.validator?.targetViewBinding =
                getDataBindingView<FragmentEmploymentQuestionnaireBinding>()
            viewModel.validateForm()
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnSubmit -> {
                viewModel.saveEmploymentInfo(
                    viewModel.getEmploymentInfoRequest(
                        viewModel.employmentStatus
                    )
                ) {
                    if (viewModel.isFromAmendment()) {
                        navigateToAmendmentSuccess()
                    } else {
                        navigate(R.id.action_employmentQuestionnaireFragment_to_cardOnTheWayFragment)
                    }
                }
            }
        }
    }

    private val employmentStatusLoadedObserver =
        Observer<EmploymentInfoAmendmentResponse> {
            initQuestionViews()
        }

    private val businessCountriesLiveDataObserver =
        Observer<ArrayList<String>> {
            onBusinessCountriesSelection(it)
        }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.rvQuestionItemListener.onItemClick(view, data, pos)
            when (view.id) {
                R.id.ivSupport -> {
                    viewModel.onInfoClick(data as QuestionUiFields) { title, message ->
                        requireContext().showInfoDialog(title, message, arrayListOf(ButtonType.CLOSE)) {}
                    }
                }

                R.id.searchCountries -> {
                    hideKeyboard(requireActivity())
                    requireActivity().launchMultiSelectionBottomSheet(
                        itemClickListener = object : OnItemClickListener {
                            override fun onItemClick(view: View, data: Any, pos: Int) {
                                onBusinessCountriesSelection(data as ArrayList<String>)
                                viewModel.validateForm()
                            }
                        }, configuration = BottomSheetConfiguration(
                            getString(Strings.screen_employee_information_display_bottom_sheet_text_heading),
                            showSearch = true,
                            showHeaderSeparator = true
                        ),
                        countriesList = viewModel.getSelectedStateCountries(
                            viewModel.parentViewModel?.countries ?: arrayListOf()
                        )
                    )
                }

                R.id.tvDropDown -> {
                    hideKeyboard(requireActivity())
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
            getBinding().llQuestions.views()[viewModel.selectedQuestionItemPosition]
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
        viewModel.employmentStatusValue.observe(this, employmentStatusLoadedObserver)
        viewModel.businessCountriesLiveData.observe(this, businessCountriesLiveDataObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
        viewModel.employmentStatusValue.removeObserver(employmentStatusLoadedObserver)
        viewModel.businessCountriesLiveData.removeObserver(businessCountriesLiveDataObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getBinding(): FragmentEmploymentQuestionnaireBinding =
        viewDataBinding as FragmentEmploymentQuestionnaireBinding


  /*  override fun showInfoDialog(
        title: String,
        message: String,
        buttonTypes: ArrayList<ButtonType>,
        cb: (view: View) -> Unit
    ) {
        requireContext().infoDialog(
            title = title,
            message = message,
            buttonType = buttonTypes,
            callback = cb
        )
    }*/

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        viewModel.parentViewModel?.countries?.unSelectAllCountries(
            viewModel.selectedBusinessCountries.get() ?: arrayListOf()
        )
    }

    private fun navigateToAmendmentSuccess() {
        viewModel.parentViewModel?.hideProgressToolbar?.value = true
        viewModel.parentViewModel?.amendmentMap?.let { amendmentMap ->
            val bundle = Bundle()
            bundle.putSerializable(
                Constants.CONFIRMATION_DESCRIPTION,
                Pair(
                    first = getString(if (amendmentMap.size == 1) R.string.screen_missing_info_confirmation_display_all_set_title else R.string.screen_missing_info_confirmation_display_step_step_completed_title),
                    second = getString(if (amendmentMap.size == 1) R.string.screen_missing_info_confirmation_display_all_set_description else R.string.screen_missing_info_confirmation_display_step_step_completed_description)
                )
            )
            bundle.putSerializable(
                Constants.KYC_AMENDMENT_MAP,
                viewModel.parentViewModel?.amendmentMap
            )
            navigate(
                R.id.action_employmentQuestionnaireFragment_to_missingInfoConfirmationFragment,
                bundle
            )
        }
    }
}