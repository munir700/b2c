package co.yap.yapcore.enums

import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.R

object Transaction{
    fun getTransactionTitle(transaction: Content?): String {
        transaction?.let { transaction ->
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

    fun getTransactionIcon(transaction: Content?): Int {
        if (transaction?.productCode.isNullOrBlank() || transaction?.txnType.isNullOrBlank() || transaction?.status.isNullOrBlank()) return 0
        return if (transaction?.status == TransactionStatus.FAILED.name) {
            R.drawable.ic_reverted
        } else
            when {
                isCash(transaction?.productCode ?: "") -> R.drawable.ic_transaction_cash
                isBank(transaction?.productCode ?: "") -> R.drawable.ic_transaction_bank
                isFee(transaction?.productCode ?: "") -> R.drawable.ic_package_standered
                isRefund(transaction?.productCode ?: "") -> R.drawable.ic_refund
                TransactionProductCode.TOP_UP_SUPPLEMENTARY_CARD.pCode == transaction?.productCode ?: "" || TransactionProductCode.WITHDRAW_SUPPLEMENTARY_CARD.pCode == transaction?.productCode ?: "" -> {
                    if (transaction?.txnType == TxnType.DEBIT.type) R.drawable.ic_minus_transactions else R.drawable.ic_plus_transactions
                }
                else -> 0
            }
    }

    fun getCategoryTitle(transaction: Content?): String {
        transaction?.let { txn ->
            if (txn.productCode.isNullOrBlank() || txn.txnType.isNullOrBlank()) return "Transaction"
            return when {
                isFee(txn.productCode ?: "") -> "Fee"
                isRefund(txn.productCode ?: "") -> "Refund"
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

     fun isFee(productCode: String): Boolean {
        return (when (productCode) {
            TransactionProductCode.MANUAL_ADJUSTMENT.pCode, TransactionProductCode.VIRTUAL_ISSUANCE_FEE.pCode, TransactionProductCode.FSS_FUNDS_WITHDRAWAL.pCode, TransactionProductCode.CARD_REORDER.pCode, TransactionProductCode.FEE_DEDUCT.pCode, TransactionProductCode.PHYSICAL_ISSUANCE_FEE.pCode, TransactionProductCode.BALANCE_INQUIRY.pCode, TransactionProductCode.PIN_CHANGE.pCode, TransactionProductCode.MINISTATEMENT.pCode, TransactionProductCode.ACCOUNT_STATUS_INQUIRY.pCode, TransactionProductCode.FSS_FEE_NOTIFICATION.pCode -> {
                true
            }
            else -> false
        })
    }

     fun isBank(productCode: String): Boolean {
        return (when (productCode) {
            TransactionProductCode.UAEFTS.pCode, TransactionProductCode.DOMESTIC.pCode, TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode, TransactionProductCode.TOP_UP_VIA_CARD.pCode, TransactionProductCode.CASH_DEPOSIT_AT_RAK.pCode, TransactionProductCode.CHEQUE_DEPOSIT_AT_RAK.pCode, TransactionProductCode.INWARD_REMITTANCE.pCode, TransactionProductCode.LOCAL_INWARD_TRANSFER.pCode, TransactionProductCode.PAYMENT_TRANSACTION.pCode, TransactionProductCode.MOTO.pCode, TransactionProductCode.ECOM.pCode -> {
                true
            }
            else -> false
        })
    }

     fun isCash(productCode: String): Boolean {
        return (when (productCode) {
            TransactionProductCode.CASH_PAYOUT.pCode, TransactionProductCode.ATM_WITHDRAWL.pCode, TransactionProductCode.MASTER_CARD_ATM_WITHDRAWAL.pCode, TransactionProductCode.CASH_ADVANCE.pCode, TransactionProductCode.ATM_DEPOSIT.pCode -> {
                true
            }
            else -> false
        })
    }

     fun isRefund(productCode: String): Boolean {
        return (when (productCode) {
            TransactionProductCode.REFUND_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_MASTER_CARD.pCode, TransactionProductCode.REVERSAL_OF_TXN_ON_FAILURE.pCode -> {
                true
            }
            else -> false
        })
    }
}
enum class TransactionProductCode(val pCode: String) {
    Y2Y_TRANSFER("P003"),
    WITHDRAW_SUPPLEMENTARY_CARD("P004"), //  Means withdraw
    CARD_REORDER("P005"),
    TOP_UP_SUPPLEMENTARY_CARD("P006"),
    TOP_UP_VIA_CARD("P009"),
    UAEFTS("P010"),
    SWIFT("P011"),
    RMT("P012"),
    CASH_PAYOUT("P013"),
    MANUAL_ADJUSTMENT("P014"),
    FEE_DEDUCT("P015"),
    FUND_LOAD("P016"),
    FUND_WITHDRAWL("P017"),
    ATM_WITHDRAWL("P018"),
    POS_PURCHASE("P019"),
    FSS_FUNDS_WITHDRAWAL("P020"),
    VIRTUAL_ISSUANCE_FEE("P021"),
    PHYSICAL_ISSUANCE_FEE("P022"),
    DOMESTIC("P023"),
    CASH_DEPOSIT_AT_RAK("P024"),
    CHEQUE_DEPOSIT_AT_RAK("P025"),
    INWARD_REMITTANCE("P026"),
    LOCAL_INWARD_TRANSFER("P027"),
    FUNDS_WITHDRAWAL_BY_CHEQUE("P028"),
    MASTER_CARD_ATM_WITHDRAWAL("P029"),
    REVERSAL_MASTER_CARD("P030"),
    REFUND_MASTER_CARD("P031"),
    REVERSAL_OF_TXN_ON_FAILURE("P032"),
    CASH_ADVANCE("P033"),
    ATM_DEPOSIT("P034"),
    MOTO("P035"),
    ECOM("P036"),
    BALANCE_INQUIRY("P037"),
    MINISTATEMENT("P038"),
    PIN_CHANGE("P039"),
    ACCOUNT_STATUS_INQUIRY("P040"),
    PAYMENT_TRANSACTION("P041"),
    FSS_FEE_NOTIFICATION("P042");

}

enum class TransactionStatus {
    PENDING, IN_PROGRESS, COMPLETED, FAILED, NONE
}