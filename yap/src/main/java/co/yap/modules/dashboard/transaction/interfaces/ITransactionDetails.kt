package co.yap.modules.dashboard.transaction.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton(id: Int)
        fun handlePressOnShareButton(id: Int)
        fun handlePressOnEditNoteClickEvent(id: Int)
        var clickEvent: SingleClickEvent

    }

    interface State : IBase.State {
        var toolBarTitle: String
        var transactionTitle: String
        var spentTitle: String
        var spentAmount: String
        var feeTitle: String
        var feeAmount: String
        var totalTitle: String
        var totalAmount: String
        var addNoteTitle: String
        var noteValue: String
    }
}