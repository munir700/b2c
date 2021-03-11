package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.widgets.DrawableClickEditText
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.LayoutQuestionTypeCountriesBinding
import co.yap.yapcore.databinding.LayoutQuestionTypeDropDownBinding
import co.yap.yapcore.databinding.LayoutQuestionTypeEditTextBinding
import co.yap.yapcore.databinding.LayoutQuestionTypeEditTextWithAmountBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.interfaces.OnItemClickListener

class EmploymentQuestionnaireAdaptor(private val list: MutableList<QuestionUiFields>) :
    BaseBindingRecyclerAdapter<QuestionUiFields, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = viewType

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(list[position].question.questionType)
    }

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return when (binding) {
            is LayoutQuestionTypeEditTextBinding -> QuestionTypeEditTextItemViewHolder(binding)
            is LayoutQuestionTypeEditTextWithAmountBinding -> QuestionTypeEditTextWithAmountItemViewHolder(
                binding
            )
            is LayoutQuestionTypeDropDownBinding -> QuestionTypeDropDownItemViewHolder(binding)
            is LayoutQuestionTypeCountriesBinding -> QuestionTypeCountriesItemViewHolder(binding)
            else -> throw IllegalStateException("Invalid data binding found $binding")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when (holder) {
            is QuestionTypeEditTextItemViewHolder -> holder.onBind(
                list[position],
                position,
                onItemClickListener
            )
            is QuestionTypeEditTextWithAmountItemViewHolder -> holder.onBind(
                list[position],
                position,
                onItemClickListener
            )
            is QuestionTypeDropDownItemViewHolder -> holder.onBind(
                list[position],
                position,
                onItemClickListener
            )
            is QuestionTypeCountriesItemViewHolder -> holder.onBind(
                list[position],
                position,
                onItemClickListener
            )
        }
    }

    private fun getLayoutId(forType: QuestionType): Int {
        return when (forType) {
            QuestionType.EDIT_TEXT_FIELD -> R.layout.layout_question_type_edit_text
            QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT -> R.layout.layout_question_type_edit_text_with_amount
            QuestionType.COUNTRIES_FIELD -> R.layout.layout_question_type_countries
            QuestionType.DROP_DOWN_FIELD -> R.layout.layout_question_type_drop_down
        }
    }
}

class QuestionTypeEditTextItemViewHolder(private val layoutQuestionTypeEditTextBinding: LayoutQuestionTypeEditTextBinding) :
    RecyclerView.ViewHolder(layoutQuestionTypeEditTextBinding.root) {
    fun onBind(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutQuestionTypeEditTextBinding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
        layoutQuestionTypeEditTextBinding.etQuestionEditText.afterTextChanged {
            onItemClickListener?.onItemClick(
                layoutQuestionTypeEditTextBinding.etQuestionEditText,
                it,
                -1
            )
        }
        setFocusListener(layoutQuestionTypeEditTextBinding.etQuestionEditText, questionUiFields)
    }

    private fun setFocusListener(input: AppCompatEditText, questionUiFields: QuestionUiFields) {
        input.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            questionUiFields.isFocusInput.set(b)
        }
    }
}

class QuestionTypeEditTextWithAmountItemViewHolder(private val binding: LayoutQuestionTypeEditTextWithAmountBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
        binding.etAmount.setDrawableClickListener(object :
            DrawableClickEditText.OnDrawableClickListener {
            override fun onClick(target: DrawableClickEditText.DrawablePosition) {
//                binding.etAmount.clearFocus()
                onItemClickListener?.onItemClick(binding.etAmount, questionUiFields, -1)
            }
        })
        binding.etAmount.afterTextChanged {
            onItemClickListener?.onItemClick(
                binding.etAmount,
                it,
                -1
            )
        }
        setFocusListener(binding.etAmount, questionUiFields)
    }

    private fun setFocusListener(input: AppCompatEditText, questionUiFields: QuestionUiFields) {
//        input.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
//            questionUiFields.isFocusInput.set(b)
//        }
    }
}

class QuestionTypeDropDownItemViewHolder(private val binding: LayoutQuestionTypeDropDownBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        binding.viewModel =
            QuestionnaireItemViewModel(
                questionUiFields,
                position,
                onItemClickListener
            )
    }
}

class QuestionTypeCountriesItemViewHolder(private val binding: LayoutQuestionTypeCountriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val businessAdapter: BusinessCountriesAdapter by lazy {
        BusinessCountriesAdapter(arrayListOf())
    }

    fun onBind(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
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
    }

}
