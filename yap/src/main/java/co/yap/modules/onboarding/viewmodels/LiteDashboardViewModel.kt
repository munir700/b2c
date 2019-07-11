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
import co.yap.yapcore.SingleLiveEvent

class LiteDashboardViewModel(application: Application) : BaseViewModel<ILiteDashboard.State>(application),
    ILiteDashboard.ViewModel, IRepositoryHolder<AuthRepository> {

    override val state: LiteDashboardState = LiteDashboardState()
    override val logoutSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: AuthRepository = AuthRepository

    override fun handlePressOnLogout() {
        logout()
    }

    override fun logout() {
        launch {
            state.loading = true
            when (val response = repository.logout()) {
                is RetroApiResponse.Success -> {
                    logoutSuccess.value = true
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


}