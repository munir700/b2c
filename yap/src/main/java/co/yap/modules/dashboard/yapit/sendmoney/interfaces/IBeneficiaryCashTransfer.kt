package co.yap.modules.dashboard.yapit.sendmoney.interfaces

import co.yap.yapcore.IBase

interface IBeneficiaryCashTransfer {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}