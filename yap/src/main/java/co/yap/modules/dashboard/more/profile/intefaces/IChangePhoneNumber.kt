package co.yap.modules.dashboard.more.profile.intefaces

import co.yap.yapcore.IBase


interface IChangePhoneNumber {
    interface View:IBase.View<ViewModel>
    interface ViewModel:IBase.ViewModel<State>
    interface State: IBase.State
}