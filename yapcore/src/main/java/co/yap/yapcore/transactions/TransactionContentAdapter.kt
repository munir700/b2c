package co.yap.yapcore.transactions

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTransactionListContentBinding
import co.yap.yapcore.transactions.viewholders.TransactionContentViewHolder

class TransactionContentAdapter(private val list: MutableList<Transaction>) :
    BaseBindingRecyclerAdapter<Transaction, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_list_content

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return TransactionContentViewHolder(
            binding as ItemTransactionListContentBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is TransactionContentViewHolder)
            holder.onBind(list[position])
    }
}