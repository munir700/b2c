package co.yap.modules.dashboard.home.adaptor

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

class TransactionsHeaderAdapter(private val list: MutableList<HomeTransactionListData>) :
    BaseBindingRecyclerAdapter<HomeTransactionListData, RecyclerView.ViewHolder>(list) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_list_header

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HeaderViewHolder).onBind(list[position])
    }

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return HeaderViewHolder(binding as ItemTransactionListHeaderBinding)
    }

    class HeaderViewHolder(private val itemTransactionListHeaderBinding: ItemTransactionListHeaderBinding) :
        RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {

        fun onBind(homeTransaction: HomeTransactionListData) {

            itemTransactionListHeaderBinding.tvTransactionDate.text = homeTransaction.date
            itemTransactionListHeaderBinding.tvTotalAmount.text = homeTransaction.totalAmount

            itemTransactionListHeaderBinding.rvExpandedTransactionsListing.layoutManager =
                LinearLayoutManager(
                    itemTransactionListHeaderBinding.rvExpandedTransactionsListing.context,
                    LinearLayoutManager.VERTICAL, false
                )

            val snapHelper = PagerSnapHelper()
            itemTransactionListHeaderBinding.rvExpandedTransactionsListing.onFlingListener = null
            snapHelper.attachToRecyclerView(itemTransactionListHeaderBinding.rvExpandedTransactionsListing)
            itemTransactionListHeaderBinding.rvExpandedTransactionsListing.adapter =
                TransactionsListingAdapter(
                    itemTransactionListHeaderBinding.rvExpandedTransactionsListing.context,
                    homeTransaction.content as ArrayList<Content>
                )

            itemTransactionListHeaderBinding.viewModel =
                ItemHeaderTransactionsViewModel(homeTransaction)
            itemTransactionListHeaderBinding.executePendingBindings()
        }

    }
}