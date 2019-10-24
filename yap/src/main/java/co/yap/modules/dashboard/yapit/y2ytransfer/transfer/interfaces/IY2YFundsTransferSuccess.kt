package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces

import co.yap.yapcore.IBase

interface IY2YFundsTransferSuccess {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnDashboardButton(id: Int)
    }

    interface State : IBase.State {
        var transferredAmount: String
    }
}