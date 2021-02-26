package co.yap.yapcore.helpers.extentions

import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.enums.*
import co.yap.yapcore.managers.SessionManager


class TransactionDetailFactory(private val transaction: Transaction) {
    fun label(forTag: TransactionDetailItem): String {
        return when (forTag) {
            TransactionDetailItem.CARD_NUMBER -> "Card"
            TransactionDetailItem.TRANSFER_AMOUNT -> if (isInternationalPOS(transaction)) "Spent amount" else "Transfer amount"
            TransactionDetailItem.EXCHANGE_RATE -> "Exchange rate"
            TransactionDetailItem.SENDER -> "Sender"
            TransactionDetailItem.RECEIVER -> "Receiver"
            TransactionDetailItem.SENT_RECEIVED -> "Amount"
            TransactionDetailItem.FEES -> "Fee"
            TransactionDetailItem.VAT -> "VAT"
            TransactionDetailItem.TOTAL -> "Total amount"
            TransactionDetailItem.REFERENCE_NUMBER -> "Reference number"
            TransactionDetailItem.REMARKS -> "Remarks"

        }
    }

    fun value(forTag: TransactionDetailItem): String {
        return when (forTag) {
            TransactionDetailItem.CARD_NUMBER -> {
                transaction.maskedCardNo?.split(" ")?.lastOrNull().let { maskCardNo ->
                    "*${maskCardNo}"
                }
            }
            TransactionDetailItem.TRANSFER_AMOUNT -> {
                transaction.amount.toString().toFormattedCurrency(true, transaction.currency, true)
            }
            TransactionDetailItem.EXCHANGE_RATE -> {
                if (isInternationalPOS(transaction)) "${transaction.currency} 1.00 = AED ${
                    getExchangeRateForInternationalPOS(
                        transaction
                    )
                }" else "${transaction.currency} 1.00 = AED ${transaction.fxRate}"
            }
            TransactionDetailItem.SENDER, TransactionDetailItem.RECEIVER -> {
                if (transaction.txnType == TxnType.CREDIT.type) transaction.senderName
                    ?: "" else transaction.receiverName ?: ""
            }

            TransactionDetailItem.SENT_RECEIVED -> {
                getSpentAmount(transaction).toString()
                    .toFormattedCurrency(showCurrency = transaction.status != TransactionStatus.FAILED.name)
            }
            TransactionDetailItem.FEES -> {
                fee(transaction)
            }
            TransactionDetailItem.VAT -> {
                transaction.vatAmount?.toString()
                    ?.toFormattedCurrency(true, SessionManager.getDefaultCurrency(), true)
                    ?: "AED 0.00"
            }
            TransactionDetailItem.TOTAL -> {
                getCalculatedTotalAmount(transaction).toString().toFormattedCurrency()
            }
            TransactionDetailItem.REFERENCE_NUMBER -> {
                transaction.transactionId ?: ""
            }
            TransactionDetailItem.REMARKS -> {
                transaction.remarks ?: ""
            }
        }
    }

    fun isShowItem(tag: TransactionDetailItem): Boolean {
        return when (tag) {
            TransactionDetailItem.CARD_NUMBER -> {
                transaction.status == TransactionStatus.CANCELLED.name || (transaction.productCode == TransactionProductCode.SWIFT.pCode || transaction.productCode == TransactionProductCode.RMT.pCode) || isInternationalPOS(
                    transaction
                )
            }
            TransactionDetailItem.TRANSFER_AMOUNT, TransactionDetailItem.EXCHANGE_RATE -> {
                isInternationalPOS(transaction) || (transaction.productCode == TransactionProductCode.SWIFT.pCode || transaction.productCode == TransactionProductCode.RMT.pCode)
            }
            TransactionDetailItem.SENDER -> {
                transaction.getProductType() == TransactionProductType.IS_SEND_MONEY && transaction.txnType == TxnType.CREDIT.type
            }
            TransactionDetailItem.RECEIVER -> {
                transaction.getProductType() == TransactionProductType.IS_SEND_MONEY && transaction.txnType == TxnType.DEBIT.type
            }
            TransactionDetailItem.SENT_RECEIVED, TransactionDetailItem.FEES, TransactionDetailItem.VAT -> {
                true
            }
            TransactionDetailItem.TOTAL -> {
                transaction.getProductType() == TransactionProductType.IS_TRANSACTION_FEE && transaction.productCode != TransactionProductCode.MANUAL_ADJUSTMENT.pCode
            }
            TransactionDetailItem.REFERENCE_NUMBER -> {
                true
            }
            TransactionDetailItem.REMARKS -> {
                !transaction.remarks.isNullOrEmpty()
            }
        }
    }

    private fun isInternationalPOS(transaction: Transaction): Boolean {
        return transaction.productCode.equals(TransactionProductCode.POS_PURCHASE.pCode) && transaction.currency != SessionManager.getDefaultCurrency()
    }

    private fun getSpentAmount(transaction: Transaction): Double {
        transaction.let {
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
        }
    }

    private fun fee(forTransaction: Transaction): String {
        return when {
            isInternationalPOS(forTransaction) -> {
                forTransaction.markupFees.toString()
                    .toFormattedCurrency(true, SessionManager.getDefaultCurrency(), true)
            }
            forTransaction.postedFees != null -> {
                forTransaction.postedFees.toString()
                    .toFormattedCurrency(true, SessionManager.getDefaultCurrency(), true)
            }
            else -> {
                "AED 0.00"
            }
        }
    }

    private fun getCalculatedTotalAmount(transaction: Transaction): Double {
        transaction.let {
            return when (it.productCode) {
                TransactionProductCode.RMT.pCode, TransactionProductCode.SWIFT.pCode -> {
                    val totalFee = (it.postedFees ?: 0.00).plus(it.vatAmount ?: 0.0)
                    (it.settlementAmount ?: 0.00).plus(totalFee)
                }
                TransactionProductCode.POS_PURCHASE.pCode -> {
                    if (it.currency != SessionManager.getDefaultCurrency()) {
                        (it.cardHolderBillingAmount ?: 0.00).plus(it.markupFees ?: 0.00)
                    } else {
                        if (it.txnType == TxnType.DEBIT.type) it.totalAmount ?: 0.0 else it.amount
                            ?: 0.0
                    }
                }
                else -> if (it.txnType == TxnType.DEBIT.type) it.totalAmount ?: 0.00 else it.amount
                    ?: 0.00
            }
        }
    }


    private fun getExchangeRateForInternationalPOS(transaction: Transaction): Double {
        val fxRate: Double? =
            transaction.amount?.let { transaction.cardHolderBillingAmount?.div(it) }
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


}
