package co.yap.modules.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.models.Transaction
import co.yap.translation.Translator
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.item_transaction_list.view.*


class TransactionsListingAdapter(
    var context: Context,
    private val transactionsList: ArrayList<Transaction>
) : RecyclerView.Adapter<TransactionsListingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionsListingAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_transaction_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionsListingAdapter.ViewHolder, position: Int) {
        val transaction: Transaction = transactionsList[position]
        if (transaction.type == "Credit") {
            holder.tvTransactionAmount?.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSecondaryGreen
                )
            )
            holder.tvTransactionAmount?.text = "+" + Utils.getFormattedCurrency(transaction.amount)
        } else if (transaction.type == "Debit") {
            holder.tvTransactionAmount?.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
            holder.tvTransactionAmount?.text = "-" + Utils.getFormattedCurrency(transaction.amount)
        }
        holder.tvTransactionName?.text = transaction.vendor
        holder.tvNameInitials?.text = shortName(transaction.vendor.toUpperCase())
        holder.tvTransactionTimeAndCategory?.text = Translator.getString(
            context,
            R.string.screen_fragment_home_transaction_time_category,
            transaction.time,
            transaction.category
        )
        holder.tvCurrency?.text = transaction.currency
    }


    override fun getItemCount(): Int {
        return transactionsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTransactionTimeAndCategory: TextView? = view.tvTransactionTimeAndCategory
        val tvTransactionName: TextView? = view.tvTransactionName
        val tvNameInitials: TextView? = view.tvNameInitials
        val tvTransactionAmount: TextView? = view.tvTransactionAmount
        val tvCurrency: TextView? = view.tvCurrency


    }

    private fun shortName(cardFullName: String): String {
        var cardFullName = cardFullName
        cardFullName = cardFullName.trim { it <= ' ' }
        var shortName = ""
        if (cardFullName.isNotEmpty() && cardFullName.contains(" ")) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            val lastName = nameStr[1]
            shortName = firstName.substring(0, 1) + lastName.substring(0, 1)
            return shortName
        } else if (cardFullName.length > 0) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            shortName = firstName.substring(0, 1)
            return shortName
        }
        return shortName
    }

}