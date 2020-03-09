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
            when {
                isCash(transaction.productCode ?: "") -> R.drawable.ic_transaction_cash
                isBank(transaction.productCode ?: "") -> R.drawable.ic_transaction_bank
                Transaction.isFee(transaction.productCode ?: "") -> R.drawable.ic_package_standered
                Transaction.isRefund(transaction.productCode ?: "") -> R.drawable.ic_refund
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == transaction.productCode ?: "" || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == transaction.productCode ?: "" -> {
                    if (transaction.txnType == TxnType.DEBIT.type) R.drawable.ic_minus_transactions else R.drawable.ic_plus_transactions
                }
                else -> 0
            }
    } ?: 0
}

fun Content?.getCategoryTitle(): String {
    this?.let { txn ->
        if (txn.productCode.isNullOrBlank() || txn.txnType.isNullOrBlank()) return "Transaction"
        return when {
            Transaction.isFee(txn.productCode ?: "") -> "Fee"
            Transaction.isRefund(txn.productCode ?: "") -> "Refund"
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
