package co.yap.yapcore.transactions.viewholders

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ItemTransactionListContentBinding
import co.yap.yapcore.enums.Transaction
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getColors
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
            ), getCategoryTitle(transaction.productCode, transaction.txnType)
        )
        setTxnAmount(transaction)
        handleProductBaseCases(context, transaction)
        itemTransactionListBinding.viewModel =
            ItemTransactionContentViewModel(
                transaction,
                getTxnResId(transaction)
            )
        itemTransactionListBinding.executePendingBindings()
    }

    private fun getTxnResId(transaction: Content): Int {
        return if (TransactionProductCode.Y2Y_TRANSFER.pCode == transaction.productCode || TransactionProductCode.POS_PURCHASE.pCode == transaction.productCode) {
            -1
        } else {
            getTransactionIcon(transaction.productCode, transaction.txnType, transaction.status)
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


    private fun handleProductBaseCases(context: Context, transaction: Content) {
        transaction.productCode?.let {
            ImageViewCompat.setImageTintList(
                itemTransactionListBinding.ivTransaction,
                ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
            )
        }
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
            else -> transaction.title ?: "Unknown"
        })
    }

    private fun getTransactionIcon(
        productCode: String?,
        txnType: String? = "",
        transactionStatus: String?
    ): Int {

        if (productCode.isNullOrBlank() || txnType.isNullOrBlank() || transactionStatus.isNullOrBlank()) return -1

        return if (transactionStatus == TransactionStatus.FAILED.name) {
            R.drawable.ic_reverted
        } else
            when {
                Transaction.isCash(productCode) -> R.drawable.ic_transaction_cash
                Transaction.isBank(productCode) -> R.drawable.ic_transaction_bank
                Transaction.isFee(productCode) -> R.drawable.ic_package_standered
                Transaction.isRefund(productCode) -> R.drawable.ic_refund
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == productCode || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == productCode -> {
                    if (txnType == TxnType.DEBIT.type) R.drawable.ic_minus_transactions else R.drawable.ic_plus_transactions
                }
                else -> -1
            }
    }

    private fun getCategoryTitle(
        productCode: String?,
        txnType: String? = ""
    ): String {
        if (productCode.isNullOrBlank() || txnType.isNullOrBlank()) return "Transaction"
        return when {
            Transaction.isFee(productCode) -> "Fee"
            Transaction.isRefund(productCode) -> "Refund"
            TransactionProductCode.Y2Y_TRANSFER.pCode == productCode -> "YTY transfer"
            TransactionProductCode.TOP_UP_VIA_CARD.pCode == productCode -> "Top up"
            TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode == productCode || TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode == productCode -> "Deposit"
            TransactionProductCode.ATM_WITHDRAWL.pCode == productCode || TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode == productCode -> "Cash"
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == productCode || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == productCode -> {
                if (txnType == TxnType.DEBIT.type) "Withdrawn from virtual card" else "Added to virtual card"
            }
            else -> return (when (productCode) {
                TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> {
                    "Transfer"
                }
                else ->
                    "Transaction"
            })
        }
    }

    private fun getTxnTypeIcon(
        productCode: String,
        txnStatus: String,
        txnType: String = ""
    ): Int {
        if (TransactionStatus.FAILED.name == txnStatus) return -1

        return if (TransactionStatus.PENDING.name == txnStatus || TransactionStatus.IN_PROGRESS.name == txnStatus && !Transaction.isFee(
                productCode
            )
        )
            R.drawable.ic_time
        else (when (txnType) {
            TxnType.DEBIT.type -> {
                R.drawable.ic_outgoing_transaction
            }
            TxnType.CREDIT.type -> {
                R.drawable.ic_incoming_transaction
            }
            else -> -1
        })
    }


}