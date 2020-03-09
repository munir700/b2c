package co.yap.yapcore.transactions

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingSearchRecylerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemEmptyBinding
import co.yap.yapcore.databinding.ItemTransactionHeaderBinding
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.transactions.viewholders.HeaderViewHolder

class TransactionsAdapter(
    private val list: MutableList<HomeTransactionListData>,
    private val clickListener: OnItemClickListener?
) : BaseBindingSearchRecylerAdapter<HomeTransactionListData, RecyclerView.ViewHolder>(list) {

    val isLoaderMore: MutableLiveData<Boolean> = MutableLiveData()
    private val empty = 1
    private val actual = 2

    init {
        isLoaderMore.value = false
    }

    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == actual) R.layout.item_transaction_header else R.layout.item_empty

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is ItemTransactionHeaderBinding) HeaderViewHolder(binding) else EmptyItemViewHolder(
            binding as ItemEmptyBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is HeaderViewHolder) {
            holder.onBind(list[position], clickListener)
        } else {
            if (holder is EmptyItemViewHolder)
                holder.onBind(position)
        }
    }

    override fun getItemCount(): Int {
        return if (true == isLoaderMore.value) list.size.inc() else list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= list.size) empty else actual
    }

    class EmptyItemViewHolder(private val itemEmptyBinding: ItemEmptyBinding) :
        RecyclerView.ViewHolder(itemEmptyBinding.root) {

        fun onBind(position: Int) {
            itemEmptyBinding.executePendingBindings()
        }
    }

    override fun filterItem(constraint: CharSequence?, item: HomeTransactionListData): Boolean {
      return true
    }

}