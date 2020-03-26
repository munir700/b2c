package co.yap.household.dashboard.subaccounts.main

import co.yap.yapcore.IBase

interface IHouseHoldAccounts {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State

}