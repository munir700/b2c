package co.yap.yapcore.helpers.extentions

import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.enums.*
import co.yap.yapcore.managers.SessionManager


fun Transaction.getTransactionDetailLabel(tag: TransactionDetailItems): String {
    return when (tag) {
        TransactionDetailItems.CARD_NUMBER -> {
            "Card"
        }
        TransactionDetailItems.TRANSFER_AMOUNT -> {
            if (isExchangeRateNull(this)) "Spent amount" else "Transfer amount"
        }
        TransactionDetailItems.EXCHANGE_RATE -> {
            "Exchange rate"
        }
        TransactionDetailItems.SENDER -> {
            "Sender"
        }
        TransactionDetailItems.RECEIVER -> {
            "Receiver"
        }
        TransactionDetailItems.SENT_RECEIVED -> {
            getSpentLabelText()
        }
        TransactionDetailItems.FEES -> {
            "Fee"
        }
        TransactionDetailItems.VAT -> {
            "VAT"
        }
        TransactionDetailItems.TOTAL -> {
            "Total amount"
        }
        TransactionDetailItems.REFERENCE_NUMBER -> {
            "Reference number"
        }
        TransactionDetailItems.REMARKS -> {
            "Remarks"
        }
    }
}

fun Transaction.getTransactionDetailValue(tag: TransactionDetailItems): String {
    return when (tag) {
        TransactionDetailItems.CARD_NUMBER -> {
            this.maskedCardNo?.split(" ")?.lastOrNull().let { maskCardNo ->
                "*${maskCardNo}"
            }
        }
        TransactionDetailItems.TRANSFER_AMOUNT -> {
            this.amount.toString().toFormattedCurrency(true, this.currency, true)
        }
        TransactionDetailItems.EXCHANGE_RATE -> {
            if (isExchangeRateNull(this)) "${this.currency} 1.00 = AED ${getExchangeRate(this)}" else "${this.currency} 1.00 = AED ${this.fxRate}"
        }
        TransactionDetailItems.SENDER -> {
            this.senderName ?: ""
        }
        TransactionDetailItems.RECEIVER -> {
            this.receiverName ?: ""
        }
        TransactionDetailItems.SENT_RECEIVED -> {
            this.getSpentAmount().toString()
                .toFormattedCurrency(showCurrency = this.status != TransactionStatus.FAILED.name)
        }
        TransactionDetailItems.FEES -> {
            getFees(this)
        }
        TransactionDetailItems.VAT -> {
            this.vatAmount?.let {
                it.toString().toFormattedCurrency(true, "AED", true)
            } ?: "AED 0.00"

        }
        TransactionDetailItems.TOTAL -> {
            getCalculatedTotalAmount().toString().toFormattedCurrency()
        }
        TransactionDetailItems.REFERENCE_NUMBER -> {
            this.transactionId ?: ""
        }
        TransactionDetailItems.REMARKS -> {
            this.remarks ?: ""
        }
    }
}

fun Transaction.getTransactionDetailItemVisibility(tag: TransactionDetailItems): Boolean {
    return when (tag) {
        TransactionDetailItems.CARD_NUMBER -> {
            this.status == TransactionStatus.CANCELLED.name || (this.productCode == TransactionProductCode.SWIFT.pCode || this.productCode == TransactionProductCode.RMT.pCode) || isExchangeRateNull(
                this)
        }
        TransactionDetailItems.TRANSFER_AMOUNT, TransactionDetailItems.EXCHANGE_RATE -> {
            isExchangeRateNull(this) || (this.productCode == TransactionProductCode.SWIFT.pCode || this.productCode == TransactionProductCode.RMT.pCode)
        }
        TransactionDetailItems.SENDER -> {
            setSenderOrReceiver(this) && this.txnType == TxnType.CREDIT.type
        }
        TransactionDetailItems.RECEIVER -> {
            setSenderOrReceiver(this) && this.txnType == TxnType.DEBIT.type
        }
        TransactionDetailItems.SENT_RECEIVED, TransactionDetailItems.FEES, TransactionDetailItems.VAT -> {
            true
        }
        TransactionDetailItems.TOTAL -> {
            this.getProductType() == TransactionProductType.IS_TRANSACTION_FEE && this.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode
        }
        TransactionDetailItems.REFERENCE_NUMBER -> {
            true
        }
        TransactionDetailItems.REMARKS -> {
            !this.remarks.isNullOrEmpty()
        }
    }
}

fun isExchangeRateNull(transaction: Transaction): Boolean {
    return transaction.productCode.equals(TransactionProductCode.POS_PURCHASE.pCode) && transaction.currency != SessionManager.getDefaultCurrency()
}

fun getExchangeRate(transaction: Transaction): Double {
    val fxRate: Double? = transaction.amount?.let { transaction.cardHolderBillingAmount?.div(it) }
    return when {
        transaction.amount?.let { transaction.cardHolderBillingAmount?.compareTo(it) } == -1 -> {
            getDecimalFormatUpTo(
                6,
                amount = fxRate.toString(),
                withComma = true
            ).toDouble()
        }
        else -> {
            getDecimalFormatUpTo(
                3,
                amount = fxRate.toString(),
                withComma = true
            ).toDouble()
        }
    }
}

fun Transaction?.getCalculatedTotalAmount(): Double {
    this?.let {
        return when (it.productCode) {
            TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode -> {
                val totalFee = (it.postedFees ?: 0.00).plus(it.vatAmount ?: 0.0)
                (it.settlementAmount ?: 0.00).plus(totalFee)
            }
            else -> if (it.txnType == TxnType.DEBIT.type) it.totalAmount ?: 0.00 else it.amount
                ?: 0.00
        }
    }
    return 0.00
}

private fun getFees(transaction: Transaction): String {
    return when {
        isExchangeRateNull(transaction) -> {
            transaction.markupFees.toString().toFormattedCurrency(true, "AED", true)
        }
        transaction.postedFees != null -> {
            transaction.postedFees.toString().toFormattedCurrency(true, "AED", true)
        }
        else -> {
            "AED 0.00"
        }
    }
}

private fun setSenderOrReceiver(transaction: Transaction): Boolean {
    return transaction.productCode == TransactionProductCode.Y2Y_TRANSFER.pCode || transaction.productCode == TransactionProductCode.UAEFTS.pCode || transaction.productCode == TransactionProductCode.DOMESTIC.pCode || transaction.productCode == TransactionProductCode.RMT.pCode || transaction.productCode == TransactionProductCode.SWIFT.pCode || transaction.productCode == TransactionProductCode.CASH_PAYOUT.pCode
}

fun Transaction?.getSpentAmount(): Double {
    this?.let {
        return when {
            it.status == TransactionStatus.FAILED.name -> 0.00
            it.getProductType() == TransactionProductType.IS_TRANSACTION_FEE && it.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode -> {
                0.00
            }
            it.productCode == TransactionProductCode.SWIFT.pCode || it.productCode == TransactionProductCode.RMT.pCode -> {
                (it.settlementAmount ?: 0.00)
            }
            it.productCode == TransactionProductCode.POS_PURCHASE.pCode -> {
                it.cardHolderBillingAmount ?: 0.00
            }
            else -> it.amount ?: 0.00
        }
    } ?: return 0.00
}