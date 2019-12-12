package co.yap.modules.dashboard.yapit.sendmoney.interfaces

import co.yap.yapcore.IBase

interface IBeneficiaryCashTransfer {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var toolBarVisibility: Boolean?
        var leftButtonVisibility: Boolean?
        var rightButtonVisibility: Boolean?
        var toolBarTitle: String?
    }
}