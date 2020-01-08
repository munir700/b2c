package co.yap.household.onboarding.dashboard.home.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.household.onboarding.dashboard.home.interfaces.IHouseholdHome
import co.yap.household.onboarding.dashboard.home.states.HouseholdHomeState
import co.yap.household.onboarding.dashboard.main.viewmodels.HouseholdDashboardBaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class HouseholdHomeViewModel(application: Application) :
    HouseholdDashboardBaseViewModel<IHouseholdHome.State>(application),
    IHouseholdHome.ViewModel {
    override val state: HouseholdHomeState = HouseholdHomeState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        requestTransactions()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override var viewState: MutableLiveData<Int> = MutableLiveData(Constants.EVENT_LOADING)
    override fun requestTransactions() {
        launch {
            viewState.value = Constants.EVENT_LOADING
            delay(2000)
            viewModelBGScope.async(Dispatchers.IO) {
                viewModelBGScope.close()
            }
            viewState.value = Constants.EVENT_EMPTY
        }
    }
}