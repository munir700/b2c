package co.yap.modules.dashboard.transaction.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.transaction.TransactionReceiptAdapter
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.networking.transactions.responsedtos.transactionreciept.TransactionReceipt
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.util.*

interface ITransactionDetails {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnEditNoteClickEvent(id: Int)
        fun handlePressOnView(id: Int)
        var clickEvent: SingleClickEvent
        var transaction: ObservableField<Transaction>
        var adapter: TransactionReceiptAdapter
        var responseReciept: MutableLiveData<TransactionReceipt>
        fun addNewReceipt(receipt: ReceiptModel)
        fun deleteReceipt(position: Int)
        fun getAllReceipts()
        fun getAddReceiptOptions(): ArrayList<BottomSheetItem>
        fun setAdapterList(receiptLis: List<String>)
    }

    interface State : IBase.State {
        var txnNoteValue: ObservableField<String>
        var isTransferTxn: ObservableField<Boolean>
        var spentVisibility: ObservableField<Boolean>
        var categoryTitle: ObservableField<String>
        var categoryIcon: ObservableField<Int>
        var transactionTitle: ObservableField<String>
        var transactionNoteDate: String?
        val editNotePrefixText: String get() = "Note added "
        var noteVisibility: ObservableBoolean
        var receiptVisibility: ObservableBoolean
        var receiptLabel: ObservableField<String>
    }
}