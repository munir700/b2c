package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models.Question
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemEmploymentQuestionnaireBinding

class EmploymentQuestionnaireAdaptor(private val list: MutableList<Question>) :
    BaseBindingRecyclerAdapter<Question, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_employment_questionnaire

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return QuestionnaireItemViewHolder(binding as ItemEmploymentQuestionnaireBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is QuestionnaireItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
