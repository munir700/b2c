package co.yap.modules.dashboard.yapit.y2y.transfer.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IY2YFundsTransferSuccess {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnDashboardButton(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var transferredAmount: String
        var title: String
    }
}