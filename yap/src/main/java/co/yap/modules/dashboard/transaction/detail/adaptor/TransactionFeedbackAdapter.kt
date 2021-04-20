package co.yap.modules.dashboard.transaction.detail.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.transaction.detail.models.ItemTransactionFeedback
import co.yap.yapcore.BaseBindingRecyclerAdapter

class TransactionFeedbackAdapter (val list: MutableList<ItemTransactionFeedback>) :
    BaseBindingRecyclerAdapter<ItemTransactionFeedback, RecyclerView.ViewHolder>(list){
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getLayoutIdForViewType(viewType: Int): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }
}