package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.item_transaction_list.view.*


class TransactionsListingAdapter(
    var context: Context,
    private val transactionsList: ArrayList<Content>
) : RecyclerView.Adapter<TransactionsListingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_transaction_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction: Content = transactionsList[position]
        if (transaction.txnType!!.toLowerCase() == "credit") {
            holder.tvTransactionAmount?.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSecondaryGreen
                )
            )
            holder.tvTransactionAmount?.text =
                "+" + Utils.getFormattedCurrency(transaction.amount.toString())
        } else if (transaction.txnType!!.toLowerCase() == "debit") {
            holder.tvTransactionAmount?.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
            holder.tvTransactionAmount?.text =
                "-" + Utils.getFormattedCurrency(transaction.amount.toString())
        }

        holder.tvTransactionName?.text = transaction?.senderName
        holder.tvNameInitials?.text = transaction?.senderName?.let { shortName(it) }
        holder.tvTransactionTimeAndCategory?.text = Translator.getString(
            context,
            R.string.screen_fragment_home_transaction_time_category,
            splitTimeString(transaction.updatedDate!!),
            transaction.category!!.toLowerCase().capitalize()
        )
        holder.tvCurrency?.text = transaction.currency
    }

    fun splitTimeString(timeString: String): String {

        val originalTimeStrings = timeString.split("T").toTypedArray()
        println(originalTimeStrings[1])
        var splitTimeStrings = originalTimeStrings[1].split(":").toTypedArray()
        var timeofTransaction = splitTimeStrings[0] + ":" + splitTimeStrings[1]

        return timeofTransaction
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
            val lastName = nameStr[nameStr.size - 1]
            shortName = firstName.substring(0, 1) + lastName.substring(0, 1)
            return shortName.toUpperCase()
        } else if (cardFullName.length > 0) {
            val nameStr =
                cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            shortName = firstName.substring(0, 1)
            return shortName.toUpperCase()
        }
        return shortName.toUpperCase()
    }

}