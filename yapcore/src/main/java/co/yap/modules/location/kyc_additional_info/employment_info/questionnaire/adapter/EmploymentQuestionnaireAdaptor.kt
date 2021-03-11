package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter

import android.os.Handler
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
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

class QuestionTypeEditTextItemViewHolder(private val binding: LayoutQuestionTypeEditTextBinding) :
    BaseQuestionsViewHolder(binding) {
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
        binding.etQuestionEditText.afterTextChanged {
            onItemClickListener?.onItemClick(
                binding.etQuestionEditText,
                it,
                -1
            )
        }
        setFocusListener(binding.etQuestionEditText, questionUiFields)
    }
}

class QuestionTypeEditTextWithAmountItemViewHolder(private val binding: LayoutQuestionTypeEditTextWithAmountBinding) :
    BaseQuestionsViewHolder(binding) {
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
        binding.ivSupport.setOnClickListener {
            binding.etAmount.clearFocus()
            onItemClickListener?.onItemClick(binding.etAmount, questionUiFields, -1)
        }

        setFocusListener(binding.etAmount, questionUiFields)
    }

}

class QuestionTypeDropDownItemViewHolder(private val binding: LayoutQuestionTypeDropDownBinding) :
    BaseQuestionsViewHolder(binding) {
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
    BaseQuestionsViewHolder(binding) {
    private val businessAdapter: BusinessCountriesAdapter by lazy {
        BusinessCountriesAdapter(arrayListOf())
    }

    fun onBind(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        businessAdapter.setList(
            questionUiFields.question.countriesAnswer
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
