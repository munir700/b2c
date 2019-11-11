package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListBinding
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils


class TransactionsListingAdapter(private val list: MutableList<Content>) :
    BaseBindingRecyclerAdapter<Content, RecyclerView.ViewHolder>(list) {


    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_transaction_list

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder as TransactionListingViewHolder).onBind(list[position])
    }

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return TransactionListingViewHolder(binding as ItemTransactionListBinding)
    }

    class TransactionListingViewHolder(private val itemTransactionListBinding: ItemTransactionListBinding) :
        RecyclerView.ViewHolder(itemTransactionListBinding.root) {

        fun onBind(content: Content) {

            val transaction: Content = content
            val context: Context = itemTransactionListBinding.tvCurrency.context
            if (transaction.txnType!!.toLowerCase() == "credit") {
                itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryGreen
                    )
                )
                itemTransactionListBinding.tvTransactionAmount?.text =
                    "+ " + Utils.getFormattedCurrency(transaction.amount.toString())
            } else if (transaction.txnType!!.toLowerCase() == "debit") {
                itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryDark
                    )
                )
                itemTransactionListBinding.tvTransactionAmount?.text =
                    "- " + Utils.getFormattedCurrency(transaction.amount.toString())
            }


            transaction.title = transaction.title ?: "Unknown"
            itemTransactionListBinding.tvTransactionName?.text = transaction.title
            itemTransactionListBinding.tvNameInitials?.text =
                transaction.title?.let { Utils.shortName(it) }

//            itemTransactionListBinding.tvTransactionName?.text = transaction?.senderName
            // itemTransactionListBinding.tvNameInitials?.text = transaction?.senderName?.let { shortName(it) }
            itemTransactionListBinding.tvTransactionTimeAndCategory?.text = Translator.getString(
                context,
                R.string.screen_fragment_home_transaction_time_category,
                splitTimeString(transaction.updatedDate!!),
                transaction.category!!.toLowerCase().capitalize()
            )
            itemTransactionListBinding.tvCurrency?.text = transaction.currency

            //itemTransactionListBinding.viewModel = YapStoreDetailItemViewModel(store)
            //itemTransactionListBinding.executePendingBindings()
        }

        private fun splitTimeString(timeString: String): String {
            val originalTimeStrings = timeString.split("T").toTypedArray()
            var splitTimeStrings = originalTimeStrings[1].split(":").toTypedArray()
            return splitTimeStrings[0] + ":" + splitTimeStrings[1]
        }
    }
}