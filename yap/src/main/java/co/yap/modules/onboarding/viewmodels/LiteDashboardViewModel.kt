package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.states.LiteDashboardState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class LiteDashboardViewModel(application: Application) : BaseViewModel<ILiteDashboard.State>(application),
    ILiteDashboard.ViewModel {

    override val state: LiteDashboardState = LiteDashboardState()
    override val logoutSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnLogout() {
        logoutSuccess.value = true
    }


}