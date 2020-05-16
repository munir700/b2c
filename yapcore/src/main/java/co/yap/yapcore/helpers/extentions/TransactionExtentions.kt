package co.yap.yapcore.helpers.extentions

import android.text.format.DateFormat
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.R
import co.yap.yapcore.enums.TransactionLabelsCode
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType
import co.yap.yapcore.helpers.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun Transaction?.getTransactionTitle(): String {
    this?.let { transaction ->
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
                    String.format(
                        "%s %s",
                        "Top-Up by",
                        "*" + it.substring(it.length - 4, it.length)
                    )
                }
                    ?: transaction.title ?: "Unknown"

            }


            else -> transaction.title ?: "Unknown"
        })
    } ?: return "Unknown"
}

fun Transaction?.getTransactionIcon(): Int {
    return this?.let { transaction ->
        return when (transaction.status) {
            TransactionStatus.CANCELLED.name -> R.drawable.ic_exclamation
            TransactionStatus.FAILED.name -> {
                R.drawable.ic_reverted
            }
            else -> when (transaction.getLabelValues()) {
                TransactionLabelsCode.IS_CASH -> R.drawable.ic_transaction_cash
                TransactionLabelsCode.IS_BANK -> R.drawable.ic_transaction_bank
                TransactionLabelsCode.IS_TRANSACTION_FEE -> R.drawable.ic_package_standered
                TransactionLabelsCode.IS_REFUND -> R.drawable.ic_refund
                else -> {
                    when {
                        TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == transaction.productCode ?: "" || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == transaction.productCode ?: "" -> {
                            if (transaction.txnType == TxnType.DEBIT.type) R.drawable.ic_grey_minus_transactions else R.drawable.ic_grey_plus_transactions
                        }
                        TransactionProductCode.FUND_LOAD.pCode == transaction.productCode || TransactionProductCode.INWARD_REMITTANCE.pCode == transaction.productCode || TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode == transaction.productCode || TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode == transaction.productCode || TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode == transaction.productCode -> {
                            R.drawable.ic_rounded_plus
                        }
                        else -> -1
                    }
                }
            }
        }
    } ?: 0
}

fun Transaction?.getTransactionTypeTitle(): String {
    this?.let { txn ->
        if (txn.status == TransactionStatus.CANCELLED.name) return "Transfer Rejected"
        return when {
            txn.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE -> "Fee"
            txn.getLabelValues() == TransactionLabelsCode.IS_REFUND -> "Refund"
            TransactionProductCode.Y2Y_TRANSFER.pCode == txn.productCode -> "YTY transfer"
            TransactionProductCode.TOP_UP_VIA_CARD.pCode == txn.productCode -> "Top-Up"
            TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode == txn.productCode || TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode == txn.productCode || TransactionProductCode.ATM_DEPOSIT.pCode == txn.productCode || TransactionProductCode.FUND_LOAD.pCode == txn.productCode -> "Deposit"
            TransactionProductCode.ATM_WITHDRAWL.pCode == txn.productCode || TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode == txn.productCode -> {
                if (txn.category.equals("REVERSAL", true)) "Reversal" else "Cash"
            }
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == txn.productCode -> {
                "Added to Virtual Card"
            }
            TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == txn.productCode -> {
                "Withdrawn from Virtual Card"
            }
            else -> return (when (txn.productCode) {
                TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> {
                    "Transfer"
                }
                else ->
                    "Transaction"
            })
        }
    } ?: return "Transaction"
}

fun Transaction?.getTransactionTypeIcon(): Int {
    this?.let { transaction ->
        if (TransactionStatus.FAILED.name == transaction.status || transaction.status == TransactionStatus.CANCELLED.name || transaction.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE) return android.R.color.transparent
        return if (TransactionStatus.PENDING.name == transaction.status || TransactionStatus.IN_PROGRESS.name == transaction.status && transaction.getLabelValues() != TransactionLabelsCode.IS_TRANSACTION_FEE)
            R.drawable.ic_time
        else if (productCode == TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode || productCode == TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode) {
            android.R.color.transparent
        } else (when (txnType) {
            TxnType.DEBIT.type -> {
                R.drawable.ic_outgoing_transaction
            }
            TxnType.CREDIT.type -> {
                android.R.color.transparent
            }
            else -> android.R.color.transparent
        })
    } ?: return android.R.color.transparent
}

fun Transaction?.getCategoryIcon(): Int {
    this?.let { transaction ->
        if (transaction.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE) {
            return R.drawable.ic_expense
        }
        return (when (transaction.productCode) {
            TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_send_money
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> 0
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                R.drawable.ic_send_money
            }
            TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_expense
            TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.FUND_LOAD.pCode -> {
                R.drawable.ic_cash
            }
            TransactionProductCode.POS_PURCHASE.pCode -> getMerchantCategoryIcon()

            else -> 0
        })
    } ?: return 0
}

fun Transaction?.getCategoryTitle(): String {
    this?.let { transaction ->
        transaction.productCode?.let { productCode ->
            if (TransactionLabelsCode.IS_TRANSACTION_FEE == getLabelValues()) {
                return "Fee"
            }
            return (when (productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> if (transaction.txnType == TxnType.DEBIT.type) "Outgoing Transfer" else "Incoming Transfer"
                TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> "Incoming Transfer"
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> ""
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                    "Outgoing Transfer"
                }
                TransactionProductCode.CARD_REORDER.pCode -> "Fee"
                TransactionProductCode.FUND_LOAD.pCode -> "Incoming Funds"
                TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode -> {
                    "Cash"
                }
                TransactionProductCode.ATM_WITHDRAWL.pCode -> {
                    if (transaction.category.equals("REVERSAL", true)) "Reversal" else "Cash"
                }
                else -> ""
            })
        } ?: return ""
    } ?: return ""
}

fun Transaction?.getMerchantCategoryIcon(): Int {
    this?.let { transaction ->
        return (when {
            transaction.merchantCategoryName.equals(
                "shopping",
                true
            ) -> R.drawable.ic_shopping_no_bg
            transaction.merchantCategoryName.equals(
                "education",
                true
            ) -> R.drawable.ic_education_no_bg
            transaction.merchantCategoryName.equals(
                "utilities",
                true
            ) -> R.drawable.ic_utilities_no_bg
            transaction.merchantCategoryName.equals(
                "healthAndBeauty",
                true
            ) -> R.drawable.ic_health_and_beauty_no_bg
            transaction.merchantCategoryName.equals(
                "Insurance",
                true
            ) -> R.drawable.ic_insurance_no_bg
            else -> R.drawable.ic_other_no_bg
        })
    } ?: return R.drawable.ic_other_no_bg
}

fun Transaction?.getMapImage(): Int {
    this?.let { transaction ->
        if (TransactionLabelsCode.IS_TRANSACTION_FEE == getLabelValues()) {
            return R.drawable.ic_image_light_red_background
        }
        return (when (transaction.productCode) {
            TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_image_blue_background
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> R.drawable.ic_image_brown_background
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> R.drawable.ic_image_light_blue_background
            TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_image_light_red_background
            TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.FUND_LOAD.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode -> R.drawable.ic_map
            else -> -1
        })
    } ?: return -1
}

fun Transaction?.getSpentLabelText(): String {
    this?.let { transaction ->
        transaction.productCode?.let { productCode ->
            return (when (productCode) {
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> "Moved"
                else -> {
                    when (transaction.txnType) {
                        TxnType.CREDIT.type -> "Received"
                        TxnType.DEBIT.type -> {
                            when (transaction.productCode) {
                                TransactionProductCode.Y2Y_TRANSFER.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                                    "Sent"
                                }
                                TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode -> "Sent in AED"
                                else -> "Spent"
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
    } ?: return "AED"
}

fun Transaction?.getAmount(): String? {
    this?.let { transaction ->
        return (when (transaction.productCode) {
            TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode -> {
                (transaction.amount?.div(transaction.fxRate.parseToDouble())).toString()
                    .toFormattedCurrency()
            }
            else -> transaction.totalAmount.toString().toFormattedCurrency()
        })
    } ?: return "0.0"
}

fun Transaction?.getLabelValues(): TransactionLabelsCode? {
    this?.productCode?.let { productCode ->
        return (when (productCode) {
            TransactionProductCode.MANUAL_ADJUSTMENT.pCode, TransactionProductCode.VIRTUAL_ISSUANCE_FEE.pCode, TransactionProductCode.FSS_FUNDS_WITHDRAWAL.pCode, TransactionProductCode.CARD_REORDER.pCode, TransactionProductCode.FEE_DEDUCT.pCode, TransactionProductCode.PHYSICAL_ISSUANCE_FEE.pCode, TransactionProductCode.BALANCE_INQUIRY.pCode, TransactionProductCode.PIN_CHANGE.pCode, TransactionProductCode.MINISTATEMENT.pCode, TransactionProductCode.ACCOUNT_STATUS_INQUIRY.pCode, TransactionProductCode.FSS_FEE_NOTIFICATION.pCode -> {
                TransactionLabelsCode.IS_TRANSACTION_FEE
            }
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.PAYMENT_TRANSACTION.pCode, TransactionProductCode.MOTO.pCode, TransactionProductCode.ECOM.pCode -> {
                TransactionLabelsCode.IS_BANK
            }
            TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_ADVANCE.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> {
                TransactionLabelsCode.IS_CASH
            }
            TransactionProductCode.REFUND_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_OF_TXN_ON_FAILURE.pCode -> {
                TransactionLabelsCode.IS_REFUND
            }
            else -> null
        })
    } ?: return null
}

fun Transaction?.getFormattedDate2(): String? {
    this?.creationDate?.let {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = sdf.parse(creationDate)

        val smsTime: Calendar = Calendar.getInstance()
        smsTime.timeZone = TimeZone.getDefault()
        smsTime.timeInMillis = convertedDate.time

        val now: Calendar = Calendar.getInstance()
        val timeFormatString = "MMMM dd"
        val dateTimeFormatString = "EEEE, MMMM d"
        val HOURS = 60 * 60 * 60.toLong()
        return if (now.get(Calendar.DATE) === smsTime.get(Calendar.DATE)) {
            "Today, " + DateFormat.format(timeFormatString, smsTime)
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) === 1) {
            "Yesterday, " + DateFormat.format(timeFormatString, smsTime)
        } else if (now.get(Calendar.YEAR) === smsTime.get(Calendar.YEAR)) {
            DateFormat.format(dateTimeFormatString, smsTime).toString()
        } else {
            DateFormat.format(timeFormatString, smsTime).toString()
        }
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

fun Transaction?.getFormattedTime(outputFormat: String = DateUtils.FORMAT_TIME_24H): String {
    return (when {
        DateUtils.reformatStringDate(
            this?.updatedDate ?: "",
            DateUtils.SERVER_DATE_FORMAT,
            outputFormat
        ).isBlank() -> DateUtils.reformatStringDate(
            this?.creationDate ?: "",
            DateUtils.SERVER_DATE_FORMAT,
            outputFormat
        )
        else -> DateUtils.reformatStringDate(
            this?.creationDate ?: "",
            DateUtils.SERVER_DATE_FORMAT,
            outputFormat
        )
    })
}

fun Transaction?.isTransactionCancelled(): Boolean {
    return this?.status == TransactionStatus.CANCELLED.name
}

