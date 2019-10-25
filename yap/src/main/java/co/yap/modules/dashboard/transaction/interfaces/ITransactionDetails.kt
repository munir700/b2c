package co.yap.modules.dashboard.transaction.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton(id: Int)
        fun handlePressOnShareButton(id: Int)
        var clickEvent: SingleClickEvent

    }

    interface State : IBase.State {
        var toolBarTitle: String
    }
}