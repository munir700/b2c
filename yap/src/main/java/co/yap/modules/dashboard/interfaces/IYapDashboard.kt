package co.yap.modules.dashboard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface IYapDashboard {

    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnNavigationItem(id: Int)
    }
    interface View : IBase.View<ViewModel>
}