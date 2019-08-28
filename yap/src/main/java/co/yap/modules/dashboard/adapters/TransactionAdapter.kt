package co.yap.modules.dashboard.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.models.TransactionModel
import kotlinx.android.synthetic.main.item_transaction_list.view.*
import org.w3c.dom.Text

class TransactionAdapter(var arrayList: ArrayList<TransactionModel>, var context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_transaction_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionModel: TransactionModel = arrayList.get(position)
        holder.tvTransactionName?.text = transactionModel.vendor
        holder.tvNameInitials?.text = shortName(transactionModel.vendor)
        holder.tvTransactionTime?.text = transactionModel.time
        holder.tvTransactionCategory?.text = transactionModel.category
        holder.tvTransactionAmount?.text = transactionModel.amount.toString()
        holder.tvCurrency?.text = transactionModel.currency
    }


    private fun shortName(cardFullName: String): String {
        var cardFullName = cardFullName
        cardFullName = cardFullName.trim { it <= ' ' }
        var shortName = ""
        if (cardFullName.isNotEmpty() && cardFullName.contains(" ")) {
            val nameStr = cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            val lastName = nameStr[1]
            shortName = firstName.substring(0, 1) + lastName.substring(0, 1)
            return shortName
        } else if (cardFullName.length > 0) {
            val nameStr = cardFullName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstName = nameStr[0]
            shortName = firstName.substring(0, 1)
            return shortName
        }
        return shortName
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTransactionName: TextView? = view.tvTransactionName
    val tvNameInitials: TextView? = view.tvNameInitials
    val tvTransactionTime: TextView? = view.tvTransactionTime
    val tvTransactionCategory: TextView? = view.tvTransactionCategory
    val tvTransactionAmount: TextView? = view.tvTransactionAmount
    val tvCurrency: TextView? = view.tvCurrency
}