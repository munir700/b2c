package co.yap.household.onboarding.dashboard.main.viewmodels

import android.app.Application
import co.yap.household.onboarding.dashboard.main.interfaces.IHouseholdDashboard
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

abstract class HouseholdDashboardBaseViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {

    var parentViewModel: IHouseholdDashboard.ViewModel? = null

}