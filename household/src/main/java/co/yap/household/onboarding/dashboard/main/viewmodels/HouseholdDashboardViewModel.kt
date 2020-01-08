package co.yap.household.onboarding.dashboard.main.viewmodels

import android.app.Application
import co.yap.household.onboarding.dashboard.main.interfaces.IHouseholdDashboard
import co.yap.household.onboarding.dashboard.main.states.HouseholdDashboardState
import co.yap.yapcore.BaseViewModel


class HouseholdDashboardViewModel(application: Application) :
    BaseViewModel<IHouseholdDashboard.State>(application), IHouseholdDashboard.ViewModel {
    override val state: HouseholdDashboardState = HouseholdDashboardState()
}