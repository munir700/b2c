package co.yap.modules.dashboard.transaction.detail

import co.yap.networking.transactions.responsedtos.transaction.ItemTransactionDetail
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.enums.TransactionDetailItems
import co.yap.yapcore.helpers.extentions.getTransactionDetailValue
import co.yap.yapcore.helpers.extentions.getTransactionDetailLabel
import co.yap.yapcore.helpers.extentions.getTransactionDetailItemVisibility

interface TransactionDetailItemsComposer {
    fun compose(): List<ItemTransactionDetail>
}

class TransactionDetailComposer(private val transaction: Transaction?) :
    TransactionDetailItemsComposer {
    override fun compose(): List<ItemTransactionDetail> {
        return getOneWayData().filter {
            it.visibility == true
        }
    }

    private fun getOneWayData(): MutableList<ItemTransactionDetail> {
        return arrayListOf(
            makeTransactionDetailItem(TransactionDetailItems.CARD_NUMBER),
            makeTransactionDetailItem(TransactionDetailItems.TRANSFER_AMOUNT),
            makeTransactionDetailItem(TransactionDetailItems.EXCHANGE_RATE),
            makeTransactionDetailItem(TransactionDetailItems.SENDER),
            makeTransactionDetailItem(TransactionDetailItems.RECEIVER),
            makeTransactionDetailItem(TransactionDetailItems.SENT_RECEIVED),
            makeTransactionDetailItem(TransactionDetailItems.FEES),
            makeTransactionDetailItem(TransactionDetailItems.VAT),
            makeTransactionDetailItem(TransactionDetailItems.TOTAL),
            makeTransactionDetailItem(TransactionDetailItems.REFERENCE_NUMBER),
            makeTransactionDetailItem(TransactionDetailItems.REMARKS)
        )
    }

    private fun makeTransactionDetailItem(
        tag: TransactionDetailItems
    ): ItemTransactionDetail {
        return ItemTransactionDetail(
            itemTitle = transaction?.getTransactionDetailLabel(tag),
            itemDescription = transaction?.getTransactionDetailValue(tag),
            visibility = transaction?.getTransactionDetailItemVisibility(tag)
        )
    }
}