package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.models.MyUserManager
import co.yap.modules.onboarding.states.LiteDashboardState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class LiteDashboardViewModel(application: Application) : BaseViewModel<ILiteDashboard.State>(application),
    ILiteDashboard.ViewModel, IRepositoryHolder<AuthRepository> {

    override val state: LiteDashboardState = LiteDashboardState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: AuthRepository = AuthRepository
    val customerRepository: CustomersRepository = CustomersRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    override fun onCreate() {
        super.onCreate()
        getAccountInfo()
    }

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
            Handler(Looper.getMainLooper()).postDelayed({ state.loading = false }, 500)

        }
    }


    override fun getAccountInfo() {
        launch {
            state.loading = true
            when (val response = customerRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    MyUserManager.user = response.data.data[0]
                    clickEvent.setValue(EVENT_GET_ACCOUNT_INFO_SUCCESS)
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun handlePressOnCompleteVerification() {
        clickEvent.setValue(EVENT_PRESS_COMPLETE_VERIFICATION)
    }

    override fun handlePressOnsetCardPin() {
        clickEvent.setValue(EVENT_PRESS_SET_CARD_PIN)
    }

}