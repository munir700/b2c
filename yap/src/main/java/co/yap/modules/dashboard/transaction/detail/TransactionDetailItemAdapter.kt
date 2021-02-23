package co.yap.modules.dashboard.transaction.detail

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionDetailBinding
import co.yap.networking.transactions.responsedtos.transaction.ItemTransactionDetail
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionDetailItemAdapter(
    val list: MutableList<ItemTransactionDetail>
) :
    BaseBindingRecyclerAdapter<ItemTransactionDetail, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return TransactionDetailViewHolder(binding as ItemTransactionDetailBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_detail

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is TransactionDetailViewHolder) {
            holder.onBind(position, list[position], onItemClickListener)
        }
    }
}

class TransactionDetailViewHolder(val binding: ItemTransactionDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        position: Int,
        item: ItemTransactionDetail,
        onItemClickListener: OnItemClickListener?
    ) {
        binding.viewModel = TransactionDetailItemViewModel(item, position, onItemClickListener)
        binding.executePendingBindings()
    }
}
