package co.yap.household.dashboard2.main

import co.yap.yapcore.IBase

interface IHouseholdDashboard {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface State : IBase.State {
    }
}