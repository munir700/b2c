package co.yap.household.dashboard.expense

import co.yap.yapcore.IBase

interface IHouseHoldExpense {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}