package co.yap.yapcore.helpers.extentions

import android.text.format.DateFormat
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.R
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionProductType
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMATE_MONTH_DAY
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.TransactionAdapterType
import co.yap.yapcore.managers.SessionManager
import java.util.*

fun Transaction?.getTitle(): String {
    this?.let { transaction -> // poo4 poo6
        return (when (transaction.productCode) {
            TransactionProductCode.Y2Y_TRANSFER.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.DOMESTIC.pCode -> {
                String.format(
                    "%s %s",
                    if (transaction.txnType == TxnType.DEBIT.type) "Sent to" else "Received from",
                    if (transaction.txnType == TxnType.DEBIT.type) transaction.receiverName
                        ?: transaction.title else transaction.senderName
                        ?: transaction.title

                )
            }
            TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                transaction.maskedCardNo?.let {
                    String.format(
                        "%s %s",
                        "Top up via",
                        "*" + it.substring(it.length - 4, it.length)
                    )
                } ?: transaction.title ?: "Unknown"

            }
            TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> "Remove from Virtual Card"
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode -> "Add to Virtual Card"
            TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.ECOM.pCode -> "Spent at ${transaction.merchantName}"
            TransactionProductCode.ATM_WITHDRAWL.pCode -> "Withdrawal"
            TransactionProductCode.ATM_DEPOSIT.pCode -> "Cash deposit"

            else -> transaction.title ?: "Unknown"
        })
    } ?: return "Unknown"
}

fun Transaction?.getIcon(): Int {
    return this?.let { transaction ->
        return when {
            transaction.isTransactionRejected() -> R.drawable.ic_transaction_rejected
            else -> when (transaction.productCode) {
                TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode -> {
                    R.drawable.ic_package_standered
                }
                TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                    R.drawable.ic_icon_card_transfer
                }
                TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode -> {
                    R.drawable.ic_plus_transactions
                }
                else -> return when (transaction.getProductType()) {
                    TransactionProductType.IS_BANK, TransactionProductType.IS_INCOMING -> R.drawable.ic_transaction_bank
                    TransactionProductType.IS_TRANSACTION_FEE -> R.drawable.ic_package_standered
                    TransactionProductType.IS_REFUND -> R.drawable.ic_refund
                    TransactionProductType.IS_CASH -> R.drawable.ic_cash_out_trasaction
                    else -> -1
                }
            }
        }
    } ?: -1
}

fun Transaction?.getStatus(): String {
    when (this?.productCode) {
        TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> return this.cardAcceptorLocation ?: ""
        TransactionProductCode.FUND_LOAD.pCode -> return this.otherBankName ?: ""
        else -> this?.let { txn ->
                return (when (txn.status) {
                    TransactionStatus.CANCELLED.name, TransactionStatus.FAILED.name -> "Rejected transaction"
                    TransactionStatus.PENDING.name, TransactionStatus.IN_PROGRESS.name -> {
                        if (txn.getProductType() != TransactionProductType.IS_TRANSACTION_FEE) "Transaction in process" else ""
                    }
                    else -> ""
                })
        } ?: return ""
    }
}

fun Transaction?.getTransferType(transactionType: TransactionAdapterType? = TransactionAdapterType.TRANSACTION): String {
    this?.let { txn ->
        return when {
            txn.getProductType() == TransactionProductType.IS_TRANSACTION_FEE -> "Fee"
            txn.getProductType() == TransactionProductType.IS_REFUND -> "Refund"
            txn.getProductType() == TransactionProductType.IS_INCOMING -> "Inward Bank Transfer"
            TransactionProductCode.Y2Y_TRANSFER.pCode == txn.productCode -> "YTY"
            TransactionProductCode.TOP_UP_VIA_CARD.pCode == txn.productCode -> "Add money"
            TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode == txn.productCode || TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode == txn.productCode || TransactionProductCode.ATM_DEPOSIT.pCode == txn.productCode || TransactionProductCode.FUND_LOAD.pCode == txn.productCode -> {
                if (txn.category.equals("REVERSAL", true)) "Reversal" else "Deposit"
            }
            TransactionProductCode.ATM_WITHDRAWL.pCode == txn.productCode || TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode == txn.productCode || TransactionProductCode.FUND_WITHDRAWL.pCode == txn.productCode || TransactionProductCode.FUNDS_WITHDRAWAL_BY_CHEQUE.pCode == txn.productCode -> {
                if (txn.category.equals("REVERSAL", true)) "Reversal" else "Withdraw money"
            }
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == txn.productCode -> {
                "Money moved"
            }
            TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == txn.productCode -> {
                "Money moved"
            }
            transactionType == TransactionAdapterType.ANALYTICS_DETAILS -> {
                DateUtils.reformatStringDate(
                    date = this.creationDate ?: "",
                    inputFormatter = SERVER_DATE_FORMAT,
                    outFormatter = FORMATE_MONTH_DAY
                )
            }
            else -> return (when (txn.productCode) {
                TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.UAEFTS.pCode -> {
                    "Send money"
                }
                else ->
                    "Transaction"
            })
        }
    } ?: return "Transaction"
}

fun Transaction?.getStatusIcon(): Int {
    this?.let { transaction ->
        return when (transaction.productCode) {
            TransactionProductCode.ATM_WITHDRAWL.pCode -> {
                R.drawable.ic_identifier_atm_withdrawl
            }
            TransactionProductCode.ATM_DEPOSIT.pCode -> {
                R.drawable.ic_identifier_atm_deposite
            }
            TransactionProductCode.FUNDS_WITHDRAWAL_BY_CHEQUE.pCode, TransactionProductCode.FUND_WITHDRAWL.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> {
                if (transaction.isTransactionInProgress()) R.drawable.ic_time else R.drawable.ic_identifier_atm_withdrawl
            }
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                if (transaction.isTransactionInProgress()) R.drawable.ic_time else R.drawable.ic_identifier_atm_deposite
            }
            TransactionProductCode.Y2Y_TRANSFER.pCode -> {
                if (transaction.txnType == TxnType.DEBIT.type) if (transaction.isTransactionInProgress()) R.drawable.ic_time else R.drawable.ic_outgoing_transaction_y2y else android.R.color.transparent
            }
            TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode -> {
                if (transaction.isTransactionInProgress()) R.drawable.ic_time else android.R.color.transparent
            }
            else -> android.R.color.transparent
        }
    } ?: return android.R.color.transparent
}


fun String?.getMerchantCategoryIcon(): Int {
    this?.let { title ->
        return ImageBinding.getResId(
            "ic_" + ImageBinding.getDrawableName(
                title
            ) + "_no_bg"
        )
    } ?: return -1
}

fun Transaction?.getMapImage(): Int {
    this?.let { transaction ->
        if (TransactionProductType.IS_TRANSACTION_FEE == getProductType()) {
            return R.drawable.ic_image_light_red_background
        }
        return (when (transaction.productCode) {
            TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_image_blue_background
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> R.drawable.ic_image_brown_background
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> R.drawable.ic_image_light_blue_background
            TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_image_light_red_background
            TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.FUND_LOAD.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> R.drawable.ic_map
            else -> -1
        })
    } ?: return -1
}

fun Transaction?.getSpentLabelText(): String {
    this?.let { transaction ->
        transaction.productCode?.let { productCode ->
            return (when (productCode) {
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> "Amount"
                else -> {
                    when (transaction.txnType) {
                        TxnType.CREDIT.type -> "Amount"
                        TxnType.DEBIT.type -> {
                            when (transaction.productCode) {
                                TransactionProductCode.Y2Y_TRANSFER.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                                    "Amount"
                                }
                                TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode -> {
                                    if (transaction.currency == SessionManager.getDefaultCurrency()) "Amount" else "Amount"
                                }
                                else -> "Amount"
                            }
                        }
                        else -> ""
                    }
                }
            })
        } ?: return ""
    } ?: return ""
}

fun Transaction?.getCurrency(): String {
    this?.let { transaction ->
        return (when (transaction.productCode) {
            TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode -> {
                transaction.currency.toString()
            }
            else -> transaction.currency.toString()
        })
    } ?: return SessionManager.getDefaultCurrency()
}

fun Transaction?.getProductType(): TransactionProductType? {
    this?.productCode?.let { productCode ->
        return (when (productCode) {
            TransactionProductCode.MANUAL_ADJUSTMENT.pCode, TransactionProductCode.VIRTUAL_ISSUANCE_FEE.pCode, TransactionProductCode.FSS_FUNDS_WITHDRAWAL.pCode, TransactionProductCode.CARD_REORDER.pCode, TransactionProductCode.FEE_DEDUCT.pCode, TransactionProductCode.PHYSICAL_ISSUANCE_FEE.pCode, TransactionProductCode.BALANCE_INQUIRY.pCode, TransactionProductCode.PIN_CHANGE.pCode, TransactionProductCode.MINISTATEMENT.pCode, TransactionProductCode.ACCOUNT_STATUS_INQUIRY.pCode, TransactionProductCode.FSS_FEE_NOTIFICATION.pCode -> {
                TransactionProductType.IS_TRANSACTION_FEE
            }
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.PAYMENT_TRANSACTION.pCode, TransactionProductCode.MOTO.pCode, TransactionProductCode.ECOM.pCode -> {
                TransactionProductType.IS_BANK
            }
            TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.CASH_ADVANCE.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.ATM_DEPOSIT.pCode, TransactionProductCode.FUND_WITHDRAWL.pCode, TransactionProductCode.FUNDS_WITHDRAWAL_BY_CHEQUE.pCode -> {
                TransactionProductType.IS_CASH
            }
            TransactionProductCode.REFUND_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_OF_TXN_ON_FAILURE.pCode -> {
                TransactionProductType.IS_REFUND
            }
            TransactionProductCode.FUND_LOAD.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode -> {
                TransactionProductType.IS_INCOMING
            }
            else -> null
        })
    } ?: return null
}

fun Transaction?.getFormattedDate(): String? {
    this?.creationDate?.let {
        val date = DateUtils.convertServerDateToLocalDate(it)
        date?.let { convertedDate ->
            val smsTime: Calendar = Calendar.getInstance()
            smsTime.timeInMillis = convertedDate.time
            //smsTime.timeZone = TimeZone.getDefault()

            val now: Calendar = Calendar.getInstance()
            val timeFormatString = "MMMM dd"
            val dateTimeFormatString = "EEEE, MMMM d"
            return when {
                now.get(Calendar.DATE) === smsTime.get(Calendar.DATE) -> {
                    "Today, " + DateFormat.format(timeFormatString, smsTime)
                }
                now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) === 1 -> {
                    "Yesterday, " + DateFormat.format(timeFormatString, smsTime)
                }
                now.get(Calendar.YEAR) === smsTime.get(Calendar.YEAR) -> {
                    DateFormat.format(dateTimeFormatString, smsTime).toString()
                }
                else -> {
                    DateFormat.format(timeFormatString, smsTime).toString()
                }
            }
        } ?: return null
    } ?: return null
}

fun Transaction.getTransactionTime(adapterType: TransactionAdapterType = TransactionAdapterType.TRANSACTION): String {
    return when (adapterType) {
        TransactionAdapterType.ANALYTICS_DETAILS -> {
            getFormattedTime(DateUtils.FORMAT_TIME_12H)
        }
        TransactionAdapterType.TRANSACTION -> {
            getFormattedTime(DateUtils.FORMAT_TIME_12H)
        }
    }
}

fun Transaction?.getFormattedTime(outputFormat: String = DateUtils.FORMAT_TIME_24H): String {
    return (when {
        DateUtils.reformatStringDate(
            this?.updatedDate ?: "",
            SERVER_DATE_FORMAT,
            outputFormat
        ).isBlank() -> DateUtils.reformatStringDate(
            this?.creationDate ?: "",
            SERVER_DATE_FORMAT,
            outputFormat
        )
        else -> DateUtils.reformatStringDate(
            this?.creationDate ?: "",
            SERVER_DATE_FORMAT,
            outputFormat
        )
    })
}

fun Transaction?.getTransactionNoteDate(outputFormat: String = DateUtils.FORMAT_TIME_24H): String {
    return (DateUtils.reformatStringDate(
        this?.transactionNoteDate ?: "",
        SERVER_DATE_FORMAT,
        outputFormat
    ))
}

fun Transaction?.isTransactionRejected(): Boolean {
    return this?.status == TransactionStatus.CANCELLED.name || this?.status == TransactionStatus.FAILED.name
}

fun Transaction?.isTransactionInProgress(): Boolean {
    return (TransactionStatus.PENDING.name == this?.status || TransactionStatus.IN_PROGRESS.name == this?.status && this.getProductType() != TransactionProductType.IS_TRANSACTION_FEE)
}

fun Transaction?.getTransactionAmountPrefix(): String {
    if (this?.productCode == TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode || this?.productCode == TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode || this?.productCode == TransactionProductCode.ATM_WITHDRAWL.pCode) {
        return ""
    } else {
        (return when (this?.txnType) {
            TxnType.DEBIT.type -> "-"
            TxnType.CREDIT.type -> "+"
            else -> ""
        })
    }
}

fun Transaction?.getAmount(): Double {
    (return when (this?.txnType) {
        TxnType.DEBIT.type -> this.totalAmount ?: 0.0
        TxnType.CREDIT.type -> this.amount ?: 0.0
        else -> 0.0
    })
}

fun Transaction?.getFormattedTransactionAmount(): String? {
    return String.format(
        "%s %s", this?.getTransactionAmountPrefix(),
        this?.getAmount().toString().toFormattedCurrency(
            showCurrency = false,
            currency = this?.currency ?: SessionManager.getDefaultCurrency()
        )
    )
}

fun Transaction?.getTransactionAmountColor(): Int {
    if (this?.productCode == TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode || this?.productCode == TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode) {
        return R.color.colorPrimaryDark
    } else {
        (return when (this?.txnType) {
            TxnType.DEBIT.type -> R.color.colorPrimaryDark
            TxnType.CREDIT.type -> {
                if (!this.isTransactionInProgress() && this.status != TransactionStatus.FAILED.name)
                    R.color.colorSecondaryGreen
                else
                    R.color.colorPrimaryDark
            }
            else -> R.color.colorPrimaryDark
        })
    }
}

fun Transaction?.showCutOffMsg(): Boolean {
    return (this?.productCode == TransactionProductCode.SWIFT.pCode)
}

fun List<Transaction>?.getTotalAmount(): String {
    var total = 0.0
    this?.map {
        when (it.productCode) {
            TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode -> {
                if (it.txnType == TxnType.DEBIT.type) {
                    val totalFee = (it.postedFees ?: 0.00).plus(it.vatAmount ?: 0.0)
                    total -= ((it.settlementAmount ?: 0.00).plus(totalFee))
                } else total += (it.settlementAmount ?: 0.0)
            }
            else -> {
                if (it.txnType == TxnType.DEBIT.type) total -= (it.totalAmount
                    ?: 0.0) else total += (it.amount ?: 0.0)
            }
        }
    }

    var totalAmount: String
    when {
        total.toString().startsWith("-") -> {
            totalAmount =
                ((total * -1).toString().toFormattedCurrency(
                    showCurrency = false,
                    currency = SessionManager.getDefaultCurrency()
                ))
            totalAmount = "- ${SessionManager.getDefaultCurrency()} $totalAmount"
        }
        else -> {
            totalAmount = (total.toString()
                .toFormattedCurrency(false, currency = SessionManager.getDefaultCurrency()))
            totalAmount = "+ ${SessionManager.getDefaultCurrency()} $totalAmount"
        }
    }
    return totalAmount
}
