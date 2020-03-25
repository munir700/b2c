package co.yap.household.helpers

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.transactions.TransactionRecyclerView
import co.yap.yapcore.transactions.TransactionsAdapter

object TransactionBindingAdapter {
    @BindingAdapter("txnList")
    @JvmStatic
    fun setViewContainerAsLinearLayout(
        recyclerView: TransactionRecyclerView,
        list: ObservableField<MutableList<HomeTransactionListData>>
    ) {
        if (true == getRecycleViewAdaptor(recyclerView.rvTransaction)?.isLoaderMore?.value && null != list.get()) {
            getRecycleViewAdaptor(recyclerView.rvTransaction)?.isLoaderMore?.value = false
            getRecycleViewAdaptor(recyclerView.rvTransaction)?.itemCount?.let {
                getRecycleViewAdaptor(recyclerView.rvTransaction)?.notifyItemRemoved(it)
            }
            getRecycleViewAdaptor(recyclerView.rvTransaction)?.addList(
                list.get() ?: mutableListOf()
            )
        } else {
            if (!list.get().isNullOrEmpty()) {
                getRecycleViewAdaptor(recyclerView.rvTransaction)?.setList(
                    list.get() ?: mutableListOf()
                )
            }
        }
    }

    private fun getRecycleViewAdaptor(recyclerView: RecyclerView?): TransactionsAdapter? {
        return recyclerView?.adapter as? TransactionsAdapter
    }
}