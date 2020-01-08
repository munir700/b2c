package co.yap.household.onboarding.dashboard.main.interfaces

import co.yap.yapcore.IBase

interface IHouseholdDashboard {
    interface View : IBase.View<ViewModel> {
        fun closeDrawer()
        fun openDrawer()
        fun toggleDrawer()
        fun enableDrawerSwipe(enable: Boolean)
    }

    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State

}