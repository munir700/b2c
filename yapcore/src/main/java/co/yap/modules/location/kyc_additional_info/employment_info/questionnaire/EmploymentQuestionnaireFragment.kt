package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.unSelectAllCountries
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.BusinessCountriesAdapter
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter.QuestionnaireItemViewModel
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.*
import co.yap.yapcore.enums.EmploymentQuestionIdentifier
import co.yap.yapcore.enums.EmploymentStatus
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.helpers.extentions.launchBottomSheetSegment
import co.yap.yapcore.helpers.extentions.launchMultiSelectionBottomSheet
import co.yap.yapcore.helpers.infoDialog
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentQuestionnaireFragment : LocationChildFragment<IEmploymentQuestionnaire.ViewModel>(),
    IEmploymentQuestionnaire.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_employment_questionnaire

    override val viewModel: EmploymentQuestionnaireViewModel
        get() = ViewModelProviders.of(this).get(EmploymentQuestionnaireViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        viewModel.employmentStatus = arguments?.get("EMPLOYMENT_STATUS") as EmploymentStatus
        viewModel.isDataRequiredFromApi(forStatus = viewModel.employmentStatus)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutInflater = LayoutInflater.from(context)
        val questions = viewModel.state.questionsList
        questions.forEach { questionUiField ->
            var view: View? = null
            val position = questions.indexOf(questionUiField)
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater,
                    getItemViewType(position, viewModel.state.questionsList),
                    null,
                    false
                )
            view = getViewFromBinding(binding, questionUiField, position, listener)
            if (view != null)
                getBinding().llQuestions.addView(view)
        }
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnSubmit -> {
                viewModel.saveEmploymentInfo(viewModel.getEmploymentInfoRequest(viewModel.employmentStatus)) {
                    navigate(R.id.action_employmentQuestionnaireFragment_to_cardOnTheWayFragment)
                }
            }
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
                    requireActivity().launchMultiSelectionBottomSheet(
                        viewModel.countriesItemClickListener,
                        countriesList = viewModel.getSelectedStateCountries(
                            viewModel.parentViewModel?.countries ?: arrayListOf()
                        )
                    )
                }

                R.id.tvDropDown -> {
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

    private fun openEmploymentTypeBottomSheet() {
        launchBottomSheetSegment(
            viewModel.employmentTypeItemClickListener,
            label = getString(Strings.screen_employment_questionnaire_display_text__bottom_sheet_title_describe_you),
            viewType = Constants.VIEW_WITHOUT_FLAG,
            listData = viewModel.parseEmploymentTypes(viewModel.employmentTypes())
        )
    }

    private fun openSegmentsBottomSheet() {
        launchBottomSheetSegment(
            viewModel.employmentTypeItemClickListener,
            label = getString(Strings.screen_employment_questionnaire_display_text__bottom_sheet_title_segments),
            viewType = Constants.VIEW_WITHOUT_FLAG,
            listData = viewModel.parseSegments(viewModel.industrySegmentsList)
        )
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getBinding(): FragmentEmploymentQuestionnaireBinding =
        viewDataBinding as FragmentEmploymentQuestionnaireBinding

    override fun showInfoDialog(title: String, message: String) {
        requireContext().infoDialog(
            title = title,
            message = message,
            buttonText = getString(Strings.screen_employment_information_dialog_button_text_close)
        )
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        viewModel.parentViewModel?.countries?.unSelectAllCountries(viewModel.selectedBusinessCountries)
    }

    /*new code*/

    private fun getLayoutId(forType: QuestionType): Int {
        return when (forType) {
            QuestionType.EDIT_TEXT_FIELD -> R.layout.layout_question_type_edit_text
            QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT -> R.layout.layout_question_type_edit_text_with_amount
            QuestionType.COUNTRIES_FIELD -> R.layout.layout_question_type_countries
            QuestionType.DROP_DOWN_FIELD -> R.layout.layout_question_type_drop_down
        }
    }


    fun questionTypeEditTextItemViewHolder(
        binding: LayoutQuestionTypeEditTextBinding,
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
        binding.etQuestionEditText.afterTextChanged {
            onItemClickListener?.onItemClick(
                binding.etQuestionEditText,
                it,
                -1
            )
        }
        return binding.root
    }

    fun questionTypeEditTextWithAmountItemViewHolder(
        binding: LayoutQuestionTypeEditTextWithAmountBinding,
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
        binding.ivSupport.setOnClickListener {
            binding.etAmount.hideKeyboard()
            onItemClickListener?.onItemClick(binding.ivSupport, questionUiFields, -1)
        }
        binding.etAmount.afterTextChanged {
            onItemClickListener?.onItemClick(
                binding.etAmount,
                it,
                -1
            )
        }
        return binding.root
    }

    fun questionTypeDropDownItemViewHolder(
        binding: LayoutQuestionTypeDropDownBinding, questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
        return binding.root
    }

    fun questionTypeCountriesItemViewHolder(
        binding: LayoutQuestionTypeCountriesBinding, questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ): View {
        val businessAdapter: BusinessCountriesAdapter by lazy {
            BusinessCountriesAdapter(arrayListOf())
        }

        businessAdapter.setList(
            questionUiFields.question.multipleAnswers
        )
        binding.businessCountriesAdapter = businessAdapter
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
        return binding.root
    }

    open class BaseQuestionsViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setFocusListener(input: AppCompatEditText, questionUiFields: QuestionUiFields) {
            var lastFocusedPosition = -1
            val handler = Handler()
            input.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    handler.postDelayed({
                        if (lastFocusedPosition == -1 || lastFocusedPosition == adapterPosition) {
                            lastFocusedPosition = adapterPosition
                            input.requestFocus()
                            questionUiFields.isFocusInput.set(hasFocus)
                        }
                    }, 100)
                } else {
                    questionUiFields.isFocusInput.set(hasFocus)
                    lastFocusedPosition = -1
                }
            }
        }
    }

    fun getLayoutIdForViewType(viewType: Int): Int = viewType

    fun getItemViewType(position: Int, list: List<QuestionUiFields>): Int {
        return getLayoutId(list[position].question.questionType)
    }

    fun getViewFromBinding(
        binding: ViewDataBinding,
        questionUiField: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ): View? {
        return when (binding) {
            is LayoutQuestionTypeEditTextBinding -> {
                questionTypeEditTextItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener
                )
            }
            is LayoutQuestionTypeEditTextWithAmountBinding -> {
                questionTypeEditTextWithAmountItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener
                )
            }
            is LayoutQuestionTypeDropDownBinding -> {
                questionTypeDropDownItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener
                )
            }
            is LayoutQuestionTypeCountriesBinding -> {
                questionTypeCountriesItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener
                )
            }
            else -> null
        }
    }
}

fun List<Country>.f(fooApiList: List<String>) = filter { m -> fooApiList.any { it == m.getName() } }
