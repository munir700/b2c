package co.yap.yapcore.transactions.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.databinding.ItemTransactionHeaderBinding
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.transactions.TransactionContentAdapter
import co.yap.yapcore.transactions.viewmodels.ItemTransactionHeaderViewModel

class HeaderViewHolder(private val itemTransactionListHeaderBinding: ItemTransactionHeaderBinding) :
    RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {

    fun onBind(homeTransaction: HomeTransactionListData, adaptorClick: OnItemClickListener?) {
        itemTransactionListHeaderBinding.rvExpandedTransactionsListing.layoutManager =
            LinearLayoutManager(
                itemTransactionListHeaderBinding.rvExpandedTransactionsListing.context,
                LinearLayoutManager.VERTICAL, false
            )

        val snapHelper = PagerSnapHelper()
        itemTransactionListHeaderBinding.rvExpandedTransactionsListing.onFlingListener = null
        snapHelper.attachToRecyclerView(itemTransactionListHeaderBinding.rvExpandedTransactionsListing)

        val mutableList = mutableListOf<Transaction>()
        mutableList.addAll(homeTransaction.transaction)

        val adaptor =
            TransactionContentAdapter(mutableList)
        itemTransactionListHeaderBinding.rvExpandedTransactionsListing.adapter = adaptor
        adaptor.allowFullItemClickListener = true
        adaptor.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                adaptorClick?.onItemClick(view, data, pos)
            }
        })

        var total = 0.0
        homeTransaction.transaction.map {
            if (it.txnType == TxnType.DEBIT.type) total -= (it.totalAmount
                ?: 0.0) else total += (it.totalAmount ?: 0.0)
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
            ItemTransactionHeaderViewModel(
                homeTransaction
            )
        itemTransactionListHeaderBinding.executePendingBindings()
    }
}