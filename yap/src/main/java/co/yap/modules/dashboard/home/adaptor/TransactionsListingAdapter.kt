package co.yap.modules.dashboard.home.adaptor

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.widget.ImageViewCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTransactionListBinding
import co.yap.modules.others.helper.ImageBinding
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.translation.Translator.getString
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils.FORMATE_TIME_24H
import co.yap.yapcore.helpers.DateUtils.FORMAT_LONG_INPUT
import co.yap.yapcore.helpers.DateUtils.reformatStringDate
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getColors


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

        fun onBind(transaction: Content) {
            val context: Context = itemTransactionListBinding.tvCurrency.context
            handleProductBaseCases(context, transaction)

            transaction.transactionNote?.let {
                itemTransactionListBinding.tvTransactionNote?.text = it
            }

            itemTransactionListBinding.tvTransactionNote?.visibility =
                if (transaction.transactionNote.isNullOrEmpty() || transaction.transactionNote.equals(
                        "null"
                    )
                ) View.GONE else View.VISIBLE

            itemTransactionListBinding.tvCurrency?.text = transaction.currency
            val txnTypeIconResId = getTxnTypeIcon(
                transaction.productCode ?: "",
                transaction.status ?: "", transaction.txnType ?: ""
            )
            if (txnTypeIconResId != -1)
                itemTransactionListBinding.ivIncoming?.setImageResource(txnTypeIconResId)
            else
                itemTransactionListBinding.ivIncoming?.setImageResource(android.R.color.transparent)


            var txnAmountPreFix = ""
            when (TxnType.valueOf(transaction.txnType ?: "")) {
                TxnType.CREDIT -> {
                    txnAmountPreFix = "+"
                    itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                        context.getColors(
                            R.color.colorSecondaryGreen
                        )
                    )
                }
                TxnType.DEBIT -> {
                    txnAmountPreFix = "-"
                    itemTransactionListBinding.tvTransactionAmount?.setTextColor(
                        context.getColors(
                            R.color.colorPrimaryDark
                        )
                    )
                }
            }
            itemTransactionListBinding.tvTransactionAmount?.text =
                String.format(
                    "%s %s", txnAmountPreFix,
                    Utils.getFormattedCurrency(transaction.totalAmount.toString())
                )
        }


        private fun handleProductBaseCases(context: Context, transaction: Content) {
            val transactionTitle = getTransactionTitle(transaction)
            val categoryTitle: String =
                getCategoryTitle(transaction.productCode ?: "", transaction.txnType ?: "")
            transaction.productCode?.let {
                if (TransactionProductCode.Y2Y_TRANSFER.pCode == transaction.productCode ?: "" || TransactionProductCode.POS_PURCHASE.pCode == transaction.productCode) {
                    ImageBinding.loadAvatar(
                        itemTransactionListBinding.ivTransaction,
                        "",
                        transactionTitle,
                        android.R.color.transparent,
                        R.dimen.text_size_h2
                    )
                } else {
                    val transactionIconResId =
                        getTransactionIcon(it, transaction.txnType ?: "", transaction.status ?: "")
                    if (transactionIconResId != -1)
                        itemTransactionListBinding.ivTransaction?.setImageResource(
                            transactionIconResId
                        )

                    ImageViewCompat.setImageTintList(
                        itemTransactionListBinding.ivTransaction,
                        ColorStateList.valueOf(context.getColors(R.color.colorPrimary))
                    )
                }
            }

            itemTransactionListBinding.tvTransactionName?.text = transactionTitle
            itemTransactionListBinding.tvTransactionTimeAndCategory?.text = getString(
                context,
                R.string.screen_fragment_home_transaction_time_category,
                reformatStringDate(
                    transaction.updatedDate ?: "", FORMAT_LONG_INPUT, FORMATE_TIME_24H
                ), categoryTitle
            )

        }

        private fun getTransactionIcon(
            productCode: String,
            txnType: String = "",
            transactionStatus: String
        ): Int {

            if (productCode.isBlank() || txnType.isBlank() || transactionStatus.isBlank()) return -1

            return if (transactionStatus == TransactionStatus.FAILED.name) {
                R.drawable.ic_reverted
            } else
                when {
                    isCash(productCode) -> R.drawable.ic_transaction_cash
                    isBank(productCode) -> R.drawable.ic_transaction_bank
                    isFee(productCode) -> R.drawable.ic_package_standered
                    isRefund(productCode) -> R.drawable.ic_refund
                    TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == productCode || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == productCode -> {
                        if (txnType == TxnType.DEBIT.type) R.drawable.ic_minus_transactions else R.drawable.ic_plus_transactions
                    }
                    else -> -1
                }
        }

        private fun getCategoryTitle(
            productCode: String,
            txnType: String = ""
        ): String {
            if (productCode.isBlank() || txnType.isBlank()) return "Transaction"
            return when {
                isFee(productCode) -> "Fee"
                isRefund(productCode) -> "Refund"
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

            return if (TransactionStatus.PENDING.name == txnStatus || TransactionStatus.IN_PROGRESS.name == txnStatus && !isFee(
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


        private fun isFee(productCode: String): Boolean {
            return (when (productCode) {
                TransactionProductCode.MANUAL_ADJUSTMENT.pCode, TransactionProductCode.VIRTUAL_ISSUANCE_FEE.pCode, TransactionProductCode.FSS_FUNDS_WITHDRAWAL.pCode, TransactionProductCode.CARD_REORDER.pCode, TransactionProductCode.FEE_DEDUCT.pCode, TransactionProductCode.PHYSICAL_ISSUANCE_FEE.pCode, TransactionProductCode.BALANCE_INQUIRY.pCode, TransactionProductCode.PIN_CHANGE.pCode, TransactionProductCode.MINISTATEMENT.pCode, TransactionProductCode.ACCOUNT_STATUS_INQUIRY.pCode, TransactionProductCode.FSS_FEE_NOTIFICATION.pCode -> {
                    true
                }
                else -> false
            })
        }

        private fun isBank(productCode: String): Boolean {
            return (when (productCode) {
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.PAYMENT_TRANSACTION.pCode, TransactionProductCode.MOTO.pCode, TransactionProductCode.ECOM.pCode -> {
                    true
                }
                else -> false
            })
        }

        private fun isCash(productCode: String): Boolean {
            return (when (productCode) {
                TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_ADVANCE.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> {
                    true
                }
                else -> false
            })
        }

        private fun isRefund(productCode: String): Boolean {
            return (when (productCode) {
                TransactionProductCode.REFUND_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_OF_TXN_ON_FAILURE.pCode -> {
                    true
                }
                else -> false
            })
        }
    }
}

