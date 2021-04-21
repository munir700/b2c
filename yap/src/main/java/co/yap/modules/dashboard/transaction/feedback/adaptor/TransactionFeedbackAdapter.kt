package co.yap.modules.dashboard.transaction.feedback.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemImprovementComponentsBinding
import co.yap.modules.dashboard.transaction.feedback.models.ItemTransactionFeedback
import co.yap.yapcore.BaseBindingRecyclerAdapter

class TransactionFeedbackAdapter (val list: MutableList<ItemTransactionFeedback>) :
    BaseBindingRecyclerAdapter<ItemTransactionFeedback, RecyclerView.ViewHolder>(list){

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
       return TransactionFeedbackViewHolder(binding as ItemImprovementComponentsBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_improvement_components

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is TransactionFeedbackViewHolder){
            holder.onBind(position,list[position])
        }
    }
}