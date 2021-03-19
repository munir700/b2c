package co.yap.modules.dashboard.transaction.detail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.transaction.detail.adaptor.TransactionDetailItemAdapter
import co.yap.modules.dashboard.transaction.detail.composer.TransactionDetailComposer
import co.yap.modules.dashboard.transaction.detail.models.TransactionDetail
import co.yap.modules.dashboard.transaction.receipt.adapter.TransactionReceiptAdapter
import co.yap.networking.transactions.requestdtos.TotalPurchaseRequest
import co.yap.networking.transactions.responsedtos.ReceiptModel
import co.yap.networking.transactions.responsedtos.TotalPurchases
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionDetails {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
        var clickEvent: SingleClickEvent
        var transaction: ObservableField<Transaction>
        var adapter: TransactionReceiptAdapter
        var responseReciept: MutableLiveData<ArrayList<String>>
        fun deleteReceipt(position: Int)
        fun getReceiptTitle(list: List<ReceiptModel>): String
        fun getAddReceiptOptions(): ArrayList<BottomSheetItem>
        fun setAdapterList(receiptLis: List<String>)
        fun getReceiptItems(receiptList: List<String>): List<ReceiptModel>
        fun receiptItemName(index: Int): String
        fun composeTransactionDetail(transaction: Transaction)
        fun getTotalPurchaseRequest() : TotalPurchaseRequest
        fun requestAllApis()
        var itemsComposer: TransactionDetailComposer
        var transactionAdapter: TransactionDetailItemAdapter
        var totalPurchase : ObservableField<TotalPurchases>
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
        var transactionData: ObservableField<TransactionDetail>
        var coverImage: ObservableInt
        var showTotalPurchases : ObservableBoolean
        var showErrorMessage : ObservableBoolean
    }
}