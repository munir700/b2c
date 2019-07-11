package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.states.LiteDashboardState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class LiteDashboardViewModel(application: Application) : BaseViewModel<ILiteDashboard.State>(application),
    ILiteDashboard.ViewModel, IRepositoryHolder<AuthRepository> {

    override val state: LiteDashboardState = LiteDashboardState()
    override val logoutSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: AuthRepository = AuthRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)


    override fun handlePressOnLogout() {
        logout()
    }


    override fun logout() {
        val deviceId: String? = sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response = repository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    logoutSuccess.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

}