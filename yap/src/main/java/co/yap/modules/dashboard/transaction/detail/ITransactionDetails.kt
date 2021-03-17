package co.yap.modules.dashboard.transaction.detail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.transaction.receipt.adapter.TransactionReceiptAdapter
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionDetails {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnEditNoteClickEvent(id: Int)
        fun handlePressOnView(id: Int)
        var clickEvent: SingleClickEvent
        var transaction: ObservableField<Transaction>
        var adapter: TransactionReceiptAdapter
        var responseReciept: MutableLiveData<ArrayList<String>>
        fun deleteReceipt(position: Int)
        fun getAllReceipts()
        fun getReceiptTitle(list: List<ReceiptModel>): String
        fun getAddReceiptOptions(): ArrayList<BottomSheetItem>
        fun setAdapterList(receiptLis: List<String>)
        fun getTransferType(transaction: Transaction): String
        fun getTransferCategoryTitle(transaction: Transaction?): String
        fun getTransferCategoryIcon(transaction: Transaction?): Int
        fun getSpentAmount(transaction: Transaction?): Double
        fun getCalculatedTotalAmount(transaction: Transaction?): Double
        fun getForeignAmount(transaction: Transaction?): Double
        fun getLocation(transaction: Transaction?): String
        fun getStatusIcon(transaction: Transaction?): Int
        fun getReceiptItems(receiptLis: List<String>): List<ReceiptModel>
        fun isShowReceiptSection(transaction: Transaction): Boolean
        fun receiptItemName(index: Int): String
    }

    interface State : IBase.State {
        var txnNoteValue: ObservableField<String>
        var isTransferTxn: ObservableField<Boolean>
        var spentVisibility: ObservableField<Boolean>
        var categoryTitle: ObservableField<String>
        var categoryIcon: ObservableField<Int>
        var transactionTitle: ObservableField<String>
        var exchangeRate: ObservableField<Double>?
        var transactionNoteDate: String?
        val editNotePrefixText: String get() = "Note added "
        var noteVisibility: ObservableBoolean
        var receiptVisibility: ObservableBoolean
        var receiptTitle: ObservableField<String>
    }
}