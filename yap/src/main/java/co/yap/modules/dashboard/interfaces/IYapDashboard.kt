package co.yap.modules.dashboard.interfaces

import co.yap.yapcore.IBase


interface IYapDashboard {

    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>
    interface View : IBase.View<ViewModel>
}