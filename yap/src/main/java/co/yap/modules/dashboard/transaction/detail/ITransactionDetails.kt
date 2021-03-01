package co.yap.modules.dashboard.transaction.detail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.transaction.detail.adaptor.TransactionDetailItemAdapter
import co.yap.modules.dashboard.transaction.detail.models.TransactionDetail
import co.yap.modules.dashboard.transaction.receipt.adapter.TransactionReceiptAdapter
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.networking.transactions.responsedtos.transactionreciept.TransactionReceipt
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.util.*

interface ITransactionDetails {
    interface View : IBase.View<ViewModel> {
      fun  setObservers()
      fun  removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnEditNoteClickEvent(id: Int)
        fun handlePressOnView(id: Int)
        var clickEvent: SingleClickEvent
        var transaction: ObservableField<Transaction>
        var adapter: TransactionReceiptAdapter
        var responseReciept: MutableLiveData<TransactionReceipt>
        var transactionAdapter: TransactionDetailItemAdapter
        fun deleteReceipt(position: Int)
        fun getAllReceipts()
        fun getReceiptTitle(list: List<ReceiptModel>): String
        fun getAddReceiptOptions(): ArrayList<BottomSheetItem>
        fun setAdapterList(receiptLis: List<String>)
        fun getForeignAmount(transaction: Transaction?): Double
        fun getReceiptItems(receiptLis: List<String>): List<ReceiptModel>
        fun isShowReceiptSection(transaction: Transaction): Boolean
        fun receiptItemName(index: Int): String
        var itemsComposer: MutableLiveData<TransactionDetailComposer>
    }

    interface State : IBase.State {
        var txnNoteValue: ObservableField<String>
        var isTransferTxn: ObservableField<Boolean>
        var spentVisibility: ObservableField<Boolean>
        var transactionNoteDate: String?
        var noteVisibility: ObservableBoolean
        var receiptVisibility: ObservableBoolean
        var receiptTitle: ObservableField<String>
        var isTransactionInProcessOrRejected: ObservableBoolean
        var coverImage: ObservableField<Int>
        var transactionData: ObservableField<TransactionDetail>
    }
}