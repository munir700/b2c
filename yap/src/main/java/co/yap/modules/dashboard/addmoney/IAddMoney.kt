package co.yap.modules.dashboard.addmoney

import co.yap.yapcore.IBase

interface IAddMoney {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}