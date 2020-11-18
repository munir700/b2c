package co.yap.modules.dashboard.addionalinfo.interfaces

import co.yap.yapcore.IBase

interface ISelectDocument {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}