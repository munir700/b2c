package co.yap.yapcore.helpers.extentions

import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.R
import co.yap.yapcore.enums.TransactionLabelsCode
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.enums.TransactionStatus
import co.yap.yapcore.enums.TxnType

fun Content?.getTransactionTitle(): String {
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
                    String.format("%s %s", "Top-Up by*", it.substring(it.length - 4, it.length))
                }
                    ?: transaction.title ?: "Unknown"

            }


            else -> transaction.title ?: "Unknown"
        })
    } ?: return "Unknown"
}

fun Content?.getTransactionIcon(): Int {
    return this?.let { transaction ->
        return if (transaction.status == TransactionStatus.FAILED.name) {
            R.drawable.ic_reverted
        } else
            when (transaction.getLabelValues()) {
                TransactionLabelsCode.IS_CASH -> R.drawable.ic_transaction_cash
                TransactionLabelsCode.IS_BANK -> R.drawable.ic_transaction_bank
                TransactionLabelsCode.IS_TRANSACTION_FEE -> R.drawable.ic_package_standered
                TransactionLabelsCode.IS_REFUND -> R.drawable.ic_refund
                else -> {
                    when {
                        TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == transaction.productCode ?: "" || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == transaction.productCode ?: "" -> {
                            if (transaction.txnType == TxnType.DEBIT.type) R.drawable.ic_minus_transactions else R.drawable.ic_plus_transactions
                        }
                        else -> 0
                    }
                }
            }
    } ?: 0
}

fun Content?.getTransactionTypeTitle(): String {
    this?.let { txn ->
        if (txn.productCode.isNullOrBlank() || txn.txnType.isNullOrBlank()) return "Transaction"
        return when {
            txn.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE -> "Fee"
            txn.getLabelValues() == TransactionLabelsCode.IS_TRANSACTION_FEE -> "Refund"
            TransactionProductCode.Y2Y_TRANSFER.pCode == txn.productCode -> "YTY transfer"
            TransactionProductCode.TOP_UP_VIA_CARD.pCode == txn.productCode -> "Top up"
            TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode == txn.productCode || TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode == txn.productCode -> "Deposit"
            TransactionProductCode.ATM_WITHDRAWL.pCode == txn.productCode || TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode == txn.productCode -> "Cash"
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == txn.productCode || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == txn.productCode -> {
                if (txn.txnType == TxnType.DEBIT.type) "Withdrawn from virtual card" else "Added to virtual card"
            }
            else -> return (when (txn.productCode) {
                TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.UAEFTS.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> {
                    "Transfer"
                }
                else ->
                    "Transaction"
            })
        }
    } ?: return "Transaction"
}

fun Content?.getTransactionTypeIcon(): Int {
    this?.let { transaction ->
        if (TransactionStatus.FAILED.name == transaction.status) return android.R.color.transparent

        return if (TransactionStatus.PENDING.name == transaction.status || TransactionStatus.IN_PROGRESS.name == transaction.status && transaction.getLabelValues() != TransactionLabelsCode.IS_TRANSACTION_FEE
        )
            R.drawable.ic_time
        else (when (txnType) {
            TxnType.DEBIT.type -> {
                R.drawable.ic_outgoing_transaction
            }
            TxnType.CREDIT.type -> {
                R.drawable.ic_incoming_transaction
            }
            else -> android.R.color.transparent
        })
    } ?: return android.R.color.transparent
}

fun Content?.getCategoryIcon(): Int {
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
            TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode -> {
                R.drawable.ic_cash
            }
            TransactionProductCode.POS_PURCHASE.pCode -> getMerchantCategoryIcon()

            else -> 0
        })
    } ?: return 0
}

fun Content?.getCategoryTitle(): String {
    this?.let { transaction ->
        transaction.productCode?.let { productCode ->
            if (TransactionLabelsCode.IS_TRANSACTION_FEE == getLabelValues()) {
                return "Fee"
            }
            return (when (productCode) {
                TransactionProductCode.Y2Y_TRANSFER.pCode -> if (transaction.txnType == TxnType.DEBIT.type) "Outgoing Transfer" else "Incoming Transfer"
                TransactionProductCode.TOP_UP_VIA_CARD.pCode -> "Incoming Transfer"
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> ""
                TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.CASH_PAYOUT.pCode -> {
                    "Outgoing Transfer"
                }
                TransactionProductCode.CARD_REORDER.pCode -> "Fee"
                TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> "Incoming Funds"
                TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode -> {
                    "Cash"
                }
                else -> ""
            })
        } ?: return ""
    } ?: return ""
}

fun Content?.getMerchantCategoryIcon(): Int {
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


fun Content?.getMapImage(): Int {
    this?.let { transaction ->
        if (TransactionLabelsCode.IS_TRANSACTION_FEE == getLabelValues()) {
            return R.drawable.ic_image_light_red_background
        }
        return (when (transaction.productCode) {
            TransactionProductCode.Y2Y_TRANSFER.pCode -> R.drawable.ic_image_blue_background
            TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode, TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode -> R.drawable.ic_image_blue_background
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode -> R.drawable.ic_image_light_blue_background
            TransactionProductCode.CARD_REORDER.pCode -> R.drawable.ic_image_light_red_background
            TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.POS_PURCHASE.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode -> R.drawable.ic_map
            else -> 0
        })
    } ?: return 0
}

fun Content?.getLabelValues(): TransactionLabelsCode? {
    this?.productCode?.let { productCode ->
        return (when (productCode) {
            TransactionProductCode.MANUAL_ADJUSTMENT.pCode, TransactionProductCode.VIRTUAL_ISSUANCE_FEE.pCode, TransactionProductCode.FSS_FUNDS_WITHDRAWAL.pCode, TransactionProductCode.CARD_REORDER.pCode, TransactionProductCode.FEE_DEDUCT.pCode, TransactionProductCode.PHYSICAL_ISSUANCE_FEE.pCode, TransactionProductCode.BALANCE_INQUIRY.pCode, TransactionProductCode.PIN_CHANGE.pCode, TransactionProductCode.MINISTATEMENT.pCode, TransactionProductCode.ACCOUNT_STATUS_INQUIRY.pCode, TransactionProductCode.FSS_FEE_NOTIFICATION.pCode -> {
                TransactionLabelsCode.IS_TRANSACTION_FEE
            }
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.PAYMENT_TRANSACTION.pCode, TransactionProductCode.MOTO.pCode, TransactionProductCode.ECOM.pCode -> {
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