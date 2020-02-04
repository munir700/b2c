package co.yap.modules.dashboard.home.adaptor

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListBinding
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.StringUtils
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
            if (transaction.txnType.toLowerCase() == "credit") {
                itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryGreen
                    )
                )
                itemTransactionListBinding.tvTransactionAmount?.text =
                    "+ " + Utils.getFormattedCurrency(transaction.amount.toString())
            } else if (transaction.txnType.toLowerCase() == "debit") {
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
            if (transaction.productCode == Constants.Y_TO_Y_TRANSFER) {
                itemTransactionListBinding.ivTransaction.setImageDrawable(context.getDrawable(R.drawable.ic_yap_to_yap))
            } else {
                if (transaction.productCode == Constants.TOP_UP_VIA_CARD) {
                    itemTransactionListBinding.ivTransaction.setImageDrawable(context.getDrawable(R.drawable.ic_top_up))
                } else {
                    if (transaction.productCode == Constants.SUPP_WITHDRAW || transaction.txnType == Constants.SUPP_CARD_TOP_UP) {
                        if (transaction.txnType == Constants.MANUAL_DEBIT) {
                            itemTransactionListBinding.ivTransaction.setImageDrawable(
                                context.getDrawable(
                                    R.drawable.ic_minus_transactions
                                )
                            )
                            itemTransactionListBinding.ivTransaction.setPadding(0)
                        } else if (transaction.txnType == Constants.MANUAL_CREDIT) {
                            itemTransactionListBinding.ivTransaction.setImageDrawable(
                                context.getDrawable(
                                    R.drawable.ic_plus_transactions
                                )
                            )
                            itemTransactionListBinding.ivTransaction.setPadding(0)
                        }
                    } else if (transaction.txnType == Constants.MANUAL_DEBIT) {
                        itemTransactionListBinding.ivTransaction.setImageDrawable(
                            context.getDrawable(
                                R.drawable.ic_outgoing
                            )
                        )
                    } else if (transaction.txnType == Constants.MANUAL_CREDIT) {
                        itemTransactionListBinding.ivTransaction.setImageDrawable(
                            context.getDrawable(
                                R.drawable.ic_incoming
                            )
                        )
                    }
                }

            }

            setDataAgainstProductCode(transaction.productCode, transaction)
            //itemTransactionListBinding.viewModel = YapStoreDetailItemViewModel(store)
            //itemTransactionListBinding.executePendingBindings()
        }

        @SuppressLint("SetTextI18n")
        private fun setDataAgainstProductCode(productCode: String, transaction: Content) {
            when (productCode) {
                Constants.Y_TO_Y_TRANSFER -> {
                    itemTransactionListBinding.tvTransactionName?.text =
                        "${StringUtils.getFirstname(transaction.senderName)} to ${StringUtils.getFirstname(
                            transaction.receiverName.toString()
                        )}"
                }
                else -> {
                    itemTransactionListBinding.tvTransactionName?.text = transaction.title
                }
            }
        }

        private fun splitTimeString(timeString: String): String {
            val originalTimeStrings = timeString.split("T").toTypedArray()
            var splitTimeStrings = originalTimeStrings[1].split(":").toTypedArray()
            return splitTimeStrings[0] + ":" + splitTimeStrings[1]
        }
    }
}