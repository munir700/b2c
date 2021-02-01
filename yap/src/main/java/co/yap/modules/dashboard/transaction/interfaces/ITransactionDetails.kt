package co.yap.modules.dashboard.transaction.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionDetails {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnEditNoteClickEvent(id: Int)
        var clickEvent: SingleClickEvent
        var transaction: ObservableField<Transaction>
        fun getTransferType(transaction: Transaction): String
        fun getTransferCategoryTitle(transaction: Transaction?): String
        fun getTransferCategoryIcon(transaction: Transaction?): Int
        fun getSpentAmount(transaction: Transaction?): Double
        fun getCalculatedTotalAmount(transaction: Transaction?): Double
        fun getForeignAmount(transaction: Transaction?): Double
        fun getLocation(transaction: Transaction?): String

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
        var noteVisibility : ObservableBoolean
    }
}