package co.yap.modules.dashboard.home.adaptor

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListHeaderBinding
import co.yap.modules.dashboard.home.helpers.transaction.ItemHeaderTransactionsViewModel
import co.yap.modules.dashboard.home.models.TransactionModel
import co.yap.yapcore.BasePagingBindingRecyclerAdapter
import co.yap.yapcore.databinding.ItemListFooterBinding


class TransactionsHeaderAdapter(retry: () -> Unit) :
    BasePagingBindingRecyclerAdapter<TransactionModel>(retry, diffCallback) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_list_header

    override fun getLayoutIdForFooterType(viewType: Int): Int = R.layout.item_list_footer

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == contentView)
            (holder as HeaderViewHolder).onBind(getItem(position))
        else (holder as ListFooterViewHolder).onBind(getState())
    }

    override fun onCreateContentViewHolder(binding: ViewDataBinding): HeaderViewHolder {
        return HeaderViewHolder(binding as ItemTransactionListHeaderBinding)
    }

    override fun onCreateFooterViewHolder(binding: ViewDataBinding): ListFooterViewHolder {
        return ListFooterViewHolder(binding as ItemListFooterBinding)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TransactionModel>() {
            override fun areItemsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean =
//              oldItem.id == newItem.id
                oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: TransactionModel,
                newItem: TransactionModel
            ): Boolean = oldItem == newItem
        }
    }


    class HeaderViewHolder(val itemTransactionListHeaderBinding: ItemTransactionListHeaderBinding) :
        RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {

        fun onBind(store: TransactionModel?) {
            itemTransactionListHeaderBinding.viewModel = ItemHeaderTransactionsViewModel(store)
            itemTransactionListHeaderBinding.executePendingBindings()
        }
    }


}