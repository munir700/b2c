package co.yap.modules.subaccounts.householdsetpin.hhsetpinstart

import co.yap.yapcore.IBase

interface IHHSetPinCardReview {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}