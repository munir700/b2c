package co.yap.modules.location.kyc_additional_info.employment_info.amendment

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.countryutils.country.unSelectAllCountries
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.QuestionItemViewHolders
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.skeletonlayout.views
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireAmendmentBinding
import co.yap.yapcore.databinding.FragmentEmploymentQuestionnaireBinding
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.extentions.launchBottomSheetSegment
import co.yap.yapcore.helpers.extentions.launchMultiSelectionBottomSheet
import co.yap.yapcore.helpers.infoDialog
import co.yap.yapcore.interfaces.OnItemClickListener
import com.liveperson.infra.utils.UIUtils

class EmploymentQuestionnaireAmendmentFragment :
    BaseBindingFragment<IEmploymentQuestionnaireAmendment.ViewModel>(),
    IEmploymentQuestionnaireAmendment.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_employment_questionnaire_amendment
    override val viewModel: EmploymentQuestionnaireAmendmentViewModel
        get() = ViewModelProvider(this).get(EmploymentQuestionnaireAmendmentViewModel::class.java)

    override fun showInfoDialog(title: String, message: String) {
        requireContext().infoDialog(
            title = title,
            message = message,
            buttonText = getString(Strings.screen_employment_information_dialog_button_text_close)
        )
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> activity?.finish()
            R.id.tvRightText -> {
                viewModel.updateEditMode(true)
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
    }

    private fun initQuestionViews() {
        viewModel.questionsList.clear()
        viewModel.questionsList.addAll(
            viewModel.questionnaires(
                viewModel.employmentStatus.value ?: EmploymentStatus.EMPLOYED,
                viewModel.employmentStatusValue.value
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
        }
        viewModel.setAnswersForQuestions()
        viewModel.documentAdapter.setList(
            viewModel.employmentStatusValue.value?.documents ?: mutableListOf()
        )
        getDataBindingView<FragmentEmploymentQuestionnaireAmendmentBinding>().llQuestions.post {
            viewModel.validator?.targetViewBinding =
                getDataBindingView<FragmentEmploymentQuestionnaireBinding>()
            viewModel.validateForm()
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnSubmit -> {
                /*viewModel.saveEmploymentInfo(
                    viewModel.getEmploymentInfoRequest(
                        viewModel.employmentStatus.value ?: EmploymentStatus.EMPLOYED
                    )
                ) {
                    if (viewModel.isFromAmendment()) {
                        navigateToAmendmentSuccess()
                    } else {
                        navigate(R.id.action_employmentQuestionnaireFragment_to_cardOnTheWayFragment)
                    }
                }*/
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

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.employmentStatus.observe(this, employmentTypeLoadedObserver)
        viewModel.businessCountriesLiveData.observe(this, businessCountriesLiveDataObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
        viewModel.employmentStatus.removeObserver(employmentTypeLoadedObserver)
        viewModel.businessCountriesLiveData.removeObserver(businessCountriesLiveDataObserver)
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
}