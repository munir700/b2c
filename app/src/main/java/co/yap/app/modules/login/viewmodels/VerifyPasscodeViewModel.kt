package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.states.VerifyPasscodeState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.ApiError
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.enums.AlertType
import java.util.concurrent.TimeUnit

class VerifyPasscodeViewModel(application: Application) :
    BaseViewModel<IVerifyPasscode.State>(application),
    IVerifyPasscode.ViewModel, IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository
    override val state: VerifyPasscodeState = VerifyPasscodeState(application)
    override val onClickEvent: MutableLiveData<Int> = MutableLiveData()
    override val loginSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val validateDeviceResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val createOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var isFingerprintLogin: Boolean = false
    private val customersRepository: CustomersRepository = CustomersRepository
    override var mobileNumber: String = ""
    override var EVENT_LOGOUT_SUCCESS: Int = 101
    private val messagesRepository: MessagesRepository = MessagesRepository

    private fun handleAttemptsError(error: ApiError) {
        when (error.actualCode) {
            "302" -> showAccountBlockedError(getString(Strings.screen_verify_passcode_text_account_locked))
            "303" -> showBlockForSomeTimeError(error.message)
            "1260" -> {
                state.isAccountFreeze.set(true)
                showAccountBlockedError(error.message)
            }
        }
    }

    override fun showAccountBlockedError(errorMessage: String) {
        state.dialerError = errorMessage
        state.isScreenLocked.set(true)
        state.isAccountLocked.set(true)
        state.valid = false
    }

    private fun showBlockForSomeTimeError(message: String) {
        TimeUnit.SECONDS.toMinutes(message.toLongOrNull() ?: 0)
        val totalSeconds = message.toLongOrNull() ?: 0
        startCountDownTimer(totalSeconds)
        state.isScreenLocked.set(true)
        state.isAccountLocked.set(false)
        state.valid = false
    }

    private fun startCountDownTimer(totalSeconds: Long) {
        val timer = object : CountDownTimer(TimeUnit.SECONDS.toMillis(totalSeconds), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                state.dialerError =
                    "Too many attempts. Please wait for ${timerString(
                        minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(
                                        millisUntilFinished
                                    )
                                )
                    )}"
            }

            override fun onFinish() {
                state.isScreenLocked.set(false)
                state.dialerError = ""
                state.valid = false
            }
        }
        timer.start()
    }

    private fun timerString(minutes: Long, seconds: Long): String {
        return String.format(
            "%02d:%02d",
            minutes,
            seconds
        )
    }

    override fun login() {
        launch {
            state.loading = true
            when (val response = repository.login(state.username, state.passcode)) {
                is RetroApiResponse.Success -> {
                    loginSuccess.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    loginSuccess.postValue(false)
                    state.loading = false
                    handleAttemptsError(response.error)
                }
            }
        }
    }

    override fun verifyPasscode() {
        launch {
            state.loading = true
            when (val response = customersRepository.validateCurrentPasscode(state.passcode)) {
                is RetroApiResponse.Success -> {
                    loginSuccess.postValue(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    loginSuccess.postValue(false)
                    state.loading = false
                }
            }
        }
    }

    override fun validateDevice() {
        launch {
            when (val response = customersRepository.validateDemographicData(state.deviceId)) {
                is RetroApiResponse.Success -> {
                    validateDeviceResult.postValue(response.data.data ?: false)
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.loading = false
                }
            }
        }
    }

    override fun createOtp() {
        launch {
            when (val response =
                messagesRepository.createOtpGeneric(CreateOtpGenericRequest(Constants.ACTION_DEVICE_VERIFICATION))) {
                is RetroApiResponse.Success -> {
                    createOtpResult.postValue(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.loading = false
                }
            }
        }
    }

    override fun handlePressOnPressView(id: Int) {
        onClickEvent.value = id
    }
}