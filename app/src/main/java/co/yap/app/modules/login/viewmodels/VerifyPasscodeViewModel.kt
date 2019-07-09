package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.states.VerifyPasscodeState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class VerifyPasscodeViewModel(application: Application) : BaseViewModel<IVerifyPasscode.State>(application),
    IVerifyPasscode.ViewModel, IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository
    override val state: VerifyPasscodeState = VerifyPasscodeState()
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val loginSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun login() {
        launch {
            when (val response = repository.login(state.username, state.passcode)) {
                is RetroApiResponse.Success -> {
                    loginSuccess.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }

        }
    }

    override fun validateDevice() {
        launch {
            when (val response = repository.validateDemographicData(state.deviceId)) {
                is RetroApiResponse.Success -> {
                    loginSuccess.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }

        }
    }

    override fun handlePressOnSignInButton() {
//        state.dialerError=state.passcode
        signInButtonPressEvent.value = true
    }
}