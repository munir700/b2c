package co.yap.yapcore.transactions.viewholders

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTransactionListContentBinding
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getTransactionIcon
import co.yap.yapcore.helpers.extentions.getTransactionTypeIcon
import co.yap.yapcore.helpers.extentions.getTransactionTypeTitle
import co.yap.yapcore.transactions.viewmodels.ItemTransactionContentViewModel

class TransactionContentViewHolder(private val itemTransactionListBinding: ItemTransactionListContentBinding) :
    RecyclerView.ViewHolder(itemTransactionListBinding.root) {

    fun onBind(content: Content) {
        val transaction: Content = content
        val context: Context = itemTransactionListBinding.tvCurrency.context

        transaction.title = getTransactionTitle(transaction)
        transaction.category = Translator.getString(
            context,
            R.string.screen_fragment_home_transaction_time_category,
            DateUtils.reformatStringDate(
                transaction.updatedDate ?: "",
                DateUtils.FORMAT_LONG_INPUT,
                DateUtils.FORMATE_TIME_24H
            ), transaction.getTransactionTypeTitle()
        )
        setTxnAmount(transaction)
        itemTransactionListBinding.viewModel =
            ItemTransactionContentViewModel(
                transaction,
                getTxnResId(transaction), transaction.getTransactionTypeIcon()

            )
        itemTransactionListBinding.executePendingBindings()
    }

    private fun getTxnResId(transaction: Content): Int {
        return if (TransactionProductCode.Y2Y_TRANSFER.pCode == transaction.productCode || TransactionProductCode.POS_PURCHASE.pCode == transaction.productCode) {
            -1
        } else {
            transaction.getTransactionIcon()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTxnAmount(transaction: Content) {
        var txnAmountPreFix = ""
        when (transaction.txnType ?: "") {
            TxnType.CREDIT.type -> {
                txnAmountPreFix = "+"
                itemTransactionListBinding.tvTransactionAmount.setTextColor(
                    ContextCompat.getColor(
                        itemTransactionListBinding.tvTransactionAmount.context,
                        R.color.colorSecondaryGreen
                    )
                )
            }
            TxnType.DEBIT.type -> {
                txnAmountPreFix = "-"
                itemTransactionListBinding.tvTransactionAmount.setTextColor(
                    ContextCompat.getColor(
                        itemTransactionListBinding.tvTransactionAmount.context,
                        R.color.colorPrimaryDark
                    )
                )
            }
        }

        itemTransactionListBinding.tvTransactionAmount.text =
            String.format(
                "%s %s", txnAmountPreFix,
                Utils.getFormattedCurrency(transaction.totalAmount.toString())
            )
    }


    private fun getTransactionTitle(transaction: Content): String {
        return (when (transaction.productCode) {
            TransactionProductCode.Y2Y_TRANSFER.pCode -> {
                String.format(
                    "%s %s",
                    if (transaction.txnType == TxnType.DEBIT.type) "To" else "From",
                    if (transaction.txnType == TxnType.DEBIT.type) transaction.receiverName
                        ?: transaction.title else transaction.senderName
                        ?: transaction.title
                )
            }
            TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                transaction.maskedCardNo?.let {
                    String.format("%s %s", "Top-Up by*", it.substring(it.length - 4, it.length))
                }
                    ?: transaction.title ?: "Unknown"
            }
            else -> transaction.title ?: "Unknown"
        })
    }


}