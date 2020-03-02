package co.yap.modules.dashboard.transaction.interfaces

import androidx.databinding.ObservableField
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton(id: Int)
        fun handlePressOnShareButton(id: Int)
        fun handlePressOnEditNoteClickEvent(id: Int)
        var clickEvent: SingleClickEvent
        var transaction: ObservableField<Content>
    }

    interface State : IBase.State {
        var toolBarTitle: String?
        var txnNoteValue: ObservableField<String>
        var isYtoYTransfer: ObservableField<Boolean>
        var spentVisibility: ObservableField<Boolean>
        var categoryTitle: ObservableField<String>
        var categoryIcon: ObservableField<Int>
    }
}