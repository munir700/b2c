package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.enums.QuestionType
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.QuestionUiFields
import co.yap.widgets.DrawableClickEditText
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemEmploymentQuestionnaireBinding
import co.yap.yapcore.databinding.LayoutQuestionTypeCountriesBinding
import co.yap.yapcore.databinding.LayoutQuestionTypeEditTextBinding
import co.yap.yapcore.databinding.LayoutQuestionTypeEditTextWithAmountBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.generateChipViews
import co.yap.yapcore.interfaces.OnItemClickListener

class QuestionnaireItemViewHolder(private val itemEmploymentQuestionnaireBinding: ItemEmploymentQuestionnaireBinding) :
    RecyclerView.ViewHolder(itemEmploymentQuestionnaireBinding.root) {

    fun onBind(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        val inflater: LayoutInflater =
            itemEmploymentQuestionnaireBinding.flow.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                getLayoutId(forType = questionUiFields.question.questionType),
                itemEmploymentQuestionnaireBinding.flow,
                false
            )
        itemEmploymentQuestionnaireBinding.flow.addView(binding.root)

        when (binding) {
            is LayoutQuestionTypeEditTextBinding -> {
                binding.viewModel =
                    getItemViewModel(questionUiFields, position, onItemClickListener)
                binding.etQuestionEditText.afterTextChanged {
                    onItemClickListener?.onItemClick(binding.etQuestionEditText, it, -1)
                }
                setFocusListener(binding.etQuestionEditText, questionUiFields)
            }
            is LayoutQuestionTypeEditTextWithAmountBinding -> {
                binding.viewModel =
                    getItemViewModel(questionUiFields, position, onItemClickListener)
                binding.etAmount.setDrawableClickListener(object :
                    DrawableClickEditText.OnDrawableClickListener {
                    override fun onClick(target: DrawableClickEditText.DrawablePosition) {
                        binding.etAmount.clearFocus()
                        onItemClickListener?.onItemClick(binding.etAmount, questionUiFields, -1)
                    }
                })
                setFocusListener(binding.etAmount, questionUiFields)
            }

            is LayoutQuestionTypeCountriesBinding -> {
                binding.viewModel =
                    getItemViewModel(questionUiFields, position, onItemClickListener)
                binding.chipGroup.generateChipViews(
                    R.layout.item_selected_country_chip,
                    questionUiFields.question.countriesAnswer
                )
            }
        }
    }


    private fun setFocusListener(input: AppCompatEditText, questionUiFields: QuestionUiFields) {
        input.onFocusChangeListener = View.OnFocusChangeListener { _, b ->
            questionUiFields.isFocusInput.set(b)
        }
    }

    private fun getLayoutId(forType: QuestionType): Int {
        return when (forType) {
            QuestionType.EDIT_TEXT_FIELD -> R.layout.layout_question_type_edit_text
            QuestionType.EDIT_TEXT_FIELD_WITH_AMOUNT -> R.layout.layout_question_type_edit_text_with_amount
            QuestionType.DROP_DOWN_FIELD -> TODO()
            QuestionType.COUNTRIES_FIELD -> R.layout.layout_question_type_countries
        }
    }

    private fun getItemViewModel(
        questionUiFields: QuestionUiFields,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) = QuestionnaireItemViewModel(
        questionUiFields,
        position,
        onItemClickListener
    )
}
