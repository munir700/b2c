package co.yap.modules.dashboard.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.models.TransactionAdapterModel
import co.yap.modules.dashboard.models.TransactionModel
import kotlinx.android.synthetic.main.item_transaction_list.view.*
import kotlinx.android.synthetic.main.item_transaction_list_header.view.*
import org.w3c.dom.Text

class TransactionAdapter(var arrayList: ArrayList<TransactionAdapterModel>, var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_ONE = 1
    private val TYPE_TWO = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_ONE) {
            return ViewHolderOne(
                LayoutInflater.from(context).inflate(
                    R.layout.item_transaction_list_header,
                    parent,
                    false
                )
            )
        } else if (viewType == TYPE_TWO) {
            return ViewHolderTwo(
                LayoutInflater.from(context).inflate(
                    R.layout.item_transaction_list,
                    parent,
                    false
                )
            )
        } else {
            throw RuntimeException("The type has to be ONE or TWO")
        }

        /*
           return ViewHolder(
               LayoutInflater.from(context).inflate(
                   R.layout.item_transaction_list,
                   parent,
                   false
               )
           )*/
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val transactionModel: TransactionModel = arrayList.get(position)

        when (holder.itemViewType) {
            TYPE_ONE -> initLayoutOne(holder as ViewHolderOne, position)
            TYPE_TWO -> initLayoutTwo(holder as ViewHolderTwo, position)
            else -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item:TransactionAdapterModel = arrayList[position]
        return if (item.viewType=="HEADER") {
            TYPE_ONE
        } else {
            TYPE_TWO
        }
        /* if (item.getType() == CardStatement.ItemType.ONE_ITEM) {
                return TYPE_ONE;
            } else if (item.getType() == CardStatement.ItemType.TWO_ITEM) {
                return TYPE_TWO;
            } else {
                return -1;
            }*/
    }

    private fun initLayoutOne(holder: ViewHolderOne, position: Int) {
        val model:TransactionAdapterModel = arrayList.get(position)
        holder.tvTransactionDate?.text = model.date
        holder.tvTotalAmount?.text=model.totalAmount
    }

    private fun initLayoutTwo(holder: ViewHolderTwo, position: Int) {
        val model:TransactionAdapterModel = arrayList.get(position)
        holder.tvTransactionName?.text = model.vendor
        holder.tvNameInitials?.text = shortName(model.vendor)
        holder.tvTransactionTime?.text = model.time
        holder.tvTransactionCategory?.text = model.category
        holder.tvTransactionAmount?.text = model.amount
        holder.tvCurrency?.text = model.currency
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

    internal class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTransactionDate: TextView? = itemView.tvTransactionDate
        val tvTotalAmount: TextView? = itemView.tvTotalAmount
    }


}


class ViewHolderTwo(view: View) : RecyclerView.ViewHolder(view) {
    val tvTransactionName: TextView? = view.tvTransactionName
    val tvNameInitials: TextView? = view.tvNameInitials
    val tvTransactionTime: TextView? = view.tvTransactionTime
    val tvTransactionCategory: TextView? = view.tvTransactionCategory
    val tvTransactionAmount: TextView? = view.tvTransactionAmount
    val tvCurrency: TextView? = view.tvCurrency
}