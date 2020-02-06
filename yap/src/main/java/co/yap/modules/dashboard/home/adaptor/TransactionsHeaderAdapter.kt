package co.yap.modules.dashboard.home.adaptor

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListHeaderBinding
import co.yap.modules.dashboard.home.helpers.transaction.ItemHeaderTransactionsViewModel
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.databinding.ItemEmptyBinding
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class TransactionsHeaderAdapter(
    private val list: MutableList<HomeTransactionListData>,
    private val adaptorClick: OnItemClickListener
) :
    BaseBindingRecyclerAdapter<HomeTransactionListData, RecyclerView.ViewHolder>(list) {

    private val empty = 1
    private val actual = 2

    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == actual) R.layout.item_transaction_list_header else R.layout.item_empty

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is ItemTransactionListHeaderBinding) HeaderViewHolder(binding) else EmptyItemViewHolder(
            binding as ItemEmptyBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is HeaderViewHolder) {
            holder.onBind(list[position], adaptorClick)
        } else {
            if (holder is EmptyItemViewHolder)
                holder.onBind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].totalAmount == "loader") empty else actual
    }

    class EmptyItemViewHolder(private val itemEmptyBinding: ItemEmptyBinding) :
        RecyclerView.ViewHolder(itemEmptyBinding.root) {

        fun onBind(position: Int) {
            itemEmptyBinding.executePendingBindings()
        }
    }

    class HeaderViewHolder(private val itemTransactionListHeaderBinding: ItemTransactionListHeaderBinding) :
        RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {

        fun onBind(homeTransaction: HomeTransactionListData, adaptorClick: OnItemClickListener) {

            //itemTransactionListHeaderBinding.tvTransactionDate.text = homeTransaction.date
            //itemTransactionListHeaderBinding.tvTotalAmount.text = homeTransaction.totalAmount

            itemTransactionListHeaderBinding.rvExpandedTransactionsListing.layoutManager =
                LinearLayoutManager(
                    itemTransactionListHeaderBinding.rvExpandedTransactionsListing.context,
                    LinearLayoutManager.VERTICAL, false
                )

            val snapHelper = PagerSnapHelper()
            itemTransactionListHeaderBinding.rvExpandedTransactionsListing.onFlingListener = null
            snapHelper.attachToRecyclerView(itemTransactionListHeaderBinding.rvExpandedTransactionsListing)

            val mutableList = mutableListOf<Content>()
            mutableList.addAll(homeTransaction.content)

            val adaptor =
                TransactionsListingAdapter(mutableList)
            itemTransactionListHeaderBinding.rvExpandedTransactionsListing.adapter = adaptor
            adaptor.allowFullItemClickListener = true
            adaptor.setItemListener(object : OnItemClickListener {
                override fun onItemClick(view: View, data: Any, pos: Int) {
                    adaptorClick.onItemClick(view, data, pos)
                }
            })

            var total = 0.0
            homeTransaction.content.map {
                if (it.txnType == "DEBIT") total -= it.amount else total += it.amount
            }

            var value: String
            when {
                total.toString().startsWith("-") -> {
                    value = Utils.getFormattedCurrency((total * -1).toString())
                    value = "- $value"
                }
                else -> {
                    value = Utils.getFormattedCurrency(total.toString())
                    value = "+ $value"
                }
            }

            homeTransaction.totalAmount = value
            itemTransactionListHeaderBinding.viewModel =
                ItemHeaderTransactionsViewModel(
                    homeTransaction
                )
            itemTransactionListHeaderBinding.executePendingBindings()
        }
    }
}