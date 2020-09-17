package co.yap.household.setpin.setpinsuccess

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPinSuccess {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State
}