package co.yap.modules.dashboard.more.profile.intefaces

import co.yap.yapcore.IBase


interface IUnverifiedChangeEmail {
    interface View:IBase.View<ViewModel>
    interface ViewModel:IBase.ViewModel<State>
    interface State: IBase.State
}