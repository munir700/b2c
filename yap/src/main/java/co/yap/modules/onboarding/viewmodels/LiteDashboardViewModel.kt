package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.states.LiteDashboardState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class LiteDashboardViewModel(application: Application) : BaseViewModel<ILiteDashboard.State>(application),
    ILiteDashboard.ViewModel, IRepositoryHolder<AuthRepository> {

    override val state: LiteDashboardState = LiteDashboardState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
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
                    clickEvent.setValue(EVENT_LOGOUT_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }

            // Set a little delay in case of no in
            // TODO: Fix this delay issue. It should not be written with a delay
            Handler(Looper.getMainLooper()).postDelayed({state.loading = false}, 500)

        }
    }

    override fun handlePressOnCompleteVerification() {
        clickEvent.setValue(EVENT_PRESS_COMPLETE_VERIFICATION)
    }
}