package co.yap.modules.dashboard.transaction.detail

import co.yap.modules.dashboard.transaction.detail.models.ItemTransactionDetail
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.enums.TransactionDetailItem
import co.yap.yapcore.helpers.extentions.TransactionDetailFactory

interface TransactionDetailItemsComposer {
    fun compose(): List<ItemTransactionDetail>
}

class TransactionDetailComposer(private val transaction: Transaction) :
    TransactionDetailItemsComposer {
    private val transactionDetailFactory: TransactionDetailFactory by lazy {
        TransactionDetailFactory(transaction)
    }


    override fun compose(): List<ItemTransactionDetail> {
        return transactionDetailItemList().filter {
            it.visibility == true
        }
    }

    private fun transactionDetailItemList(): MutableList<ItemTransactionDetail> {
        return arrayListOf(
            makeTransactionDetailItem(TransactionDetailItem.CARD_NUMBER),
            makeTransactionDetailItem(TransactionDetailItem.TRANSFER_AMOUNT),
            makeTransactionDetailItem(TransactionDetailItem.EXCHANGE_RATE),
            makeTransactionDetailItem(TransactionDetailItem.SENDER),
            makeTransactionDetailItem(TransactionDetailItem.RECEIVER),
            makeTransactionDetailItem(TransactionDetailItem.SENT_RECEIVED),
            makeTransactionDetailItem(TransactionDetailItem.FEES),
            makeTransactionDetailItem(TransactionDetailItem.VAT),
            makeTransactionDetailItem(TransactionDetailItem.TOTAL),
            makeTransactionDetailItem(TransactionDetailItem.REFERENCE_NUMBER),
            makeTransactionDetailItem(TransactionDetailItem.REMARKS)
        )
    }

    private fun makeTransactionDetailItem(
        tag: TransactionDetailItem
    ): ItemTransactionDetail {
        return ItemTransactionDetail(
            label = transactionDetailFactory.label(tag),
            value = transactionDetailFactory.value(tag),
            visibility = transactionDetailFactory.isShowItem(tag)
        )
    }
}