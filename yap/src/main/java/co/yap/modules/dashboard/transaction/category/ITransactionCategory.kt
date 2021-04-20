package co.yap.modules.dashboard.transaction.category

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransactionCategory {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }
    interface State : IBase.State
}