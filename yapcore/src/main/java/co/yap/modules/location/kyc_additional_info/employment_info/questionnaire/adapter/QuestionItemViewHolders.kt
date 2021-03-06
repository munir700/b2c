package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.yapcore.R
import co.yap.yapcore.databinding.*
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.hideKeyboard
import co.yap.yapcore.interfaces.OnItemClickListener

class QuestionItemViewHolders {
    private fun getLayoutId(forType: QuestionType): Int {
        return when (forType) {
            QuestionType.EDIT_TEXT_FIELD -> R.layout.layout_question_type_edit_text
            QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT -> R.layout.layout_question_type_edit_text_with_amount
            QuestionType.COUNTRIES_FIELD -> R.layout.layout_question_type_countries
            QuestionType.DROP_DOWN_FIELD -> R.layout.layout_question_type_drop_down
            QuestionType.DISPLAY_TEXT -> R.layout.layout_question_type_display_text
        }
    }

    fun questionTypeEditTextItemViewHolder(
        binding: LayoutQuestionTypeEditTextBinding,
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?,
        amendmentMap: HashMap<String?, List<String>?>?,
        isEditable: MutableLiveData<Boolean> = MutableLiveData(true)
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener,
                amendmentMap,
                questionUiFields.question.tag,
                isEditable
            )
        binding.etQuestionEditText.afterTextChanged {
            onItemClickListener?.onItemClick(
                binding.etQuestionEditText,
                it,
                -1
            )
        }
        setFocusListener(binding.etQuestionEditText, questionUiFields)
        return binding.root
    }

    fun questionTypeEditTextWithAmountItemViewHolder(
        binding: LayoutQuestionTypeEditTextWithAmountBinding,
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?,
        amendmentMap: HashMap<String?, List<String>?>?,
        isEditable: MutableLiveData<Boolean> = MutableLiveData(true)
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener,
                amendmentMap,
                questionUiFields.question.tag,
                isEditable
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
        setFocusListener(binding.etAmount, questionUiFields)
        return binding.root
    }

    fun questionTypeDropDownItemViewHolder(
        binding: LayoutQuestionTypeDropDownBinding, questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?,
        amendmentMap: HashMap<String?, List<String>?>?,
        isEditable: MutableLiveData<Boolean> = MutableLiveData(true)
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener,
                amendmentMap,
                questionUiFields.question.tag,
                isEditable
            )
        return binding.root
    }

    fun questionTypeCountriesItemViewHolder(
        binding: LayoutQuestionTypeCountriesBinding, questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?,
        countries: ArrayList<String>,
        amendmentMap: HashMap<String?, List<String>?>?,
        isEditable: MutableLiveData<Boolean> = MutableLiveData(true)
    ): View {
        val businessAdapter: BusinessCountriesAdapter by lazy {
            BusinessCountriesAdapter(arrayListOf())
        }

        businessAdapter.setList(countries)
        binding.businessCountriesAdapter = businessAdapter
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener,
                amendmentMap,
                questionUiFields.question.tag,
                isEditable
            )
        return binding.root
    }

    fun questionTypeDisplayTextItemViewHolder(
        binding: LayoutQuestionTypeDisplayTextBinding, questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?,
        amendmentMap: HashMap<String?, List<String>?>?
    ): View {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener,
                amendmentMap,
                questionUiFields.question.tag
            )
        return binding.root
    }

    fun setFocusListener(input: AppCompatEditText, questionUiFields: QuestionUiFields) {
        input.setOnFocusChangeListener { v, hasFocus ->
            questionUiFields.isFocusInput.set(hasFocus)
        }
    }

    fun getLayoutIdForViewType(viewType: Int): Int = viewType

    fun getItemViewType(forType: QuestionType): Int {
        return getLayoutId(forType)
    }

    fun getViewFromBinding(
        binding: ViewDataBinding,
        questionUiField: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?,
        countries: ArrayList<String>,
        amendmentMap: HashMap<String?, List<String>?>?,
        isEditable: MutableLiveData<Boolean> = MutableLiveData(true)
    ): View? {
        return when (binding) {
            is LayoutQuestionTypeEditTextBinding -> {
                questionTypeEditTextItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener,
                    amendmentMap,
                    isEditable
                )
            }
            is LayoutQuestionTypeEditTextWithAmountBinding -> {
                questionTypeEditTextWithAmountItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener,
                    amendmentMap,
                    isEditable
                )
            }
            is LayoutQuestionTypeDropDownBinding -> {
                questionTypeDropDownItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener,
                    amendmentMap,
                    isEditable
                )
            }
            is LayoutQuestionTypeCountriesBinding -> {
                questionTypeCountriesItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener,
                    countries,
                    amendmentMap,
                    isEditable
                )
            }
            is LayoutQuestionTypeDisplayTextBinding -> {
                questionTypeDisplayTextItemViewHolder(
                    binding,
                    questionUiField,
                    position,
                    onItemClickListener,
                    amendmentMap
                )
            }
            else -> null
        }
    }
}