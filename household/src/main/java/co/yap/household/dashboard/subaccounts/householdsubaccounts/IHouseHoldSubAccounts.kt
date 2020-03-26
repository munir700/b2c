package co.yap.household.dashboard.subaccounts.householdsubaccounts

import co.yap.yapcore.IBase

interface IHouseHoldSubAccounts {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}