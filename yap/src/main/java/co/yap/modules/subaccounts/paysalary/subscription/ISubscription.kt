package co.yap.modules.subaccounts.paysalary.subscription

import co.yap.yapcore.IBase

interface ISubscription {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}