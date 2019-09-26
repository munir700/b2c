package co.yap.modules.dashboard.changeemailmore.interfaces

import co.yap.yapcore.IBase

interface IChangeEmail {
    interface View:IBase.View<ViewModel>
    interface ViewModel:IBase.ViewModel<State>
    interface State:IBase.State
}