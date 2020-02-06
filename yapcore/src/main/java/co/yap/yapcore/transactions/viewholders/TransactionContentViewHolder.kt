package co.yap.yapcore.transactions.viewholders

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.ItemTransactionListContentBinding
import co.yap.yapcore.helpers.StringUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.transactions.viewmodels.ItemTransactionContentViewModel

class TransactionContentViewHolder(private val itemTransactionListBinding: ItemTransactionListContentBinding) :
    RecyclerView.ViewHolder(itemTransactionListBinding.root) {

    fun onBind(content: Content) {
        val transaction: Content = content
        val txnImageResId: Int?
        val context: Context = itemTransactionListBinding.tvCurrency.context

        when (transaction.txnType.toLowerCase()) {
            "credit" -> setTxnAmountData(transaction, R.color.colorSecondaryGreen)
            "debit" -> setTxnAmountData(transaction, R.color.colorPrimaryDark)
        }
        transaction.title = transaction.title ?: "Unknown"
        transaction.category = ""
        transaction.category = Translator.getString(
            context,
            R.string.screen_fragment_home_transaction_time_category,
            splitTimeString(content.updatedDate),
            transaction.category.toLowerCase().capitalize()
        )

        if (transaction.productCode == Constants.Y_TO_Y_TRANSFER) {
            txnImageResId = R.drawable.ic_yap_to_yap
            transaction.title = "${StringUtils.getFirstname(transaction.senderName)} to ${
            StringUtils.getFirstname(
                transaction.receiverName.toString()
            )}"
        } else {
            txnImageResId = getTransactionImage(transaction.productCode, transaction.txnType)
        }

        itemTransactionListBinding.viewModel =
            ItemTransactionContentViewModel(
                transaction,
                txnImageResId
            )
        itemTransactionListBinding.executePendingBindings()
    }

    @SuppressLint("SetTextI18n")
    private fun setTxnAmountData(transaction: Content, txtColor: Int) {
        itemTransactionListBinding.tvTransactionAmount?.setTextColor(
            ContextCompat.getColor(
                itemTransactionListBinding.tvTransactionAmount.context,
                txtColor
            )
        )

        itemTransactionListBinding.tvTransactionAmount?.text =
            "+ " + Utils.getFormattedCurrency(transaction.amount.toString())
    }

    private fun getTransactionImage(productCode: String, txnType: String): Int? {
        if (productCode == Constants.TOP_UP_VIA_CARD) {
            return (R.drawable.ic_top_up)
        } else {
            if (productCode == Constants.SUPP_WITHDRAW || txnType == Constants.SUPP_CARD_TOP_UP) {
                if (txnType == Constants.MANUAL_DEBIT) {
                    itemTransactionListBinding.ivTransaction.setPadding(0)
                    return R.drawable.ic_minus_transactions
                } else if (txnType == Constants.MANUAL_CREDIT) {
                    itemTransactionListBinding.ivTransaction.setPadding(0)
                    return R.drawable.ic_plus_transactions
                }
            } else if (txnType == Constants.MANUAL_DEBIT) {
                return R.drawable.ic_plus_transactions
            } else if (txnType == Constants.MANUAL_CREDIT) {
                return R.drawable.ic_plus_transactions
            }
        }

        return null
    }

    private fun splitTimeString(timeString: String): String {
        if (!timeString.isBlank()) {
            val originalTimeStrings = timeString.split("T").toTypedArray()
            val splitTimeStrings = originalTimeStrings[1].split(":").toTypedArray()
            return splitTimeStrings[0] + ":" + splitTimeStrings[1]
        }
        return ""

    }
}