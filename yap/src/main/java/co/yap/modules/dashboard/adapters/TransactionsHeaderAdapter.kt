package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.models.TransactionModel
import kotlinx.android.synthetic.main.item_transaction_list_header.view.*

class TransactionsHeaderAdapter(
    val context: Context?,
    private var categoriesList: List<TransactionModel>) : RecyclerView.Adapter<TransactionsHeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transaction_list_header, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

        val categories: TransactionModel = categoriesList[position]

        if (categories.transactionsList.isNotEmpty()) {

            holder.tvTotalAmount!!.text = categories.totalAmount
            holder.tvTransactionDate!!.text = categories.date

            holder.horizontalView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )

            val snapHelper = PagerSnapHelper()

            holder.horizontalView.onFlingListener = null

            snapHelper.attachToRecyclerView(holder.horizontalView)

            holder.horizontalView.adapter =
                TransactionsListingAdapter(context!!, categories.transactionsList)
        }

    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTransactionDate: TextView? = itemView.tvTransactionDate
        val tvTotalAmount: TextView? = itemView.tvTotalAmount
        var horizontalView: RecyclerView = view.findViewById(R.id.rv_expanded_transactions_listing)
    }

}