package co.yap.modules.subaccounts.householdsetpin.hhsetpinsuccess

import co.yap.yapcore.IBase

interface IHHSetPinSuccess {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}