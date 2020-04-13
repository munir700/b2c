package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.states.VerifyPasscodeState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.ApiError
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.MyUserManager
import java.util.concurrent.TimeUnit

class VerifyPasscodeViewModel(application: Application) :
    BaseViewModel<IVerifyPasscode.State>(application),
    IVerifyPasscode.ViewModel, IRepositoryHolder<AuthRepository> {

    override val forgotPasscodeButtonPressEvent: SingleClickEvent = SingleClickEvent()
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
    override val accountInfo: MutableLiveData<AccountInfo> = MutableLiveData()
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

    private fun getUserName(): String? {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        return if (!SharedPreferenceManager(context).getValueBoolien(
                KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            state.username
        } else {
            sharedPreferenceManager.getDecryptedUserName()
        }
    }

    override fun login() {
        launch {
            state.loading = true
            when (val response = repository.login(state.username, state.passcode)) {
                is RetroApiResponse.Success -> {
                    //loginSuccess.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    //loginSuccess.postValue(false)
                    //state.loading = false
                    //handleAttemptsError(response.error)
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
                    response.data.data?.let {
                        //if (it) state.loading = false
                        validateDeviceResult.postValue(it)
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun getAccountInfo() {
        launch {
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
                        MyUserManager.user = response.data.data[0]
                        accountInfo.postValue(response.data.data[0])
                        trackEventWithAttributes(MyUserManager.user)
                        state.loading = true
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = true
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun createOtp() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(CreateOtpGenericRequest(Constants.ACTION_DEVICE_VERIFICATION))) {
                is RetroApiResponse.Success -> {
                    createOtpResult.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun handlePressOnPressView(id: Int) {
        if (id != R.id.tvForgotPassword) {
            onClickEvent.value = id
        } else {
            val username = getUserName()
            username?.let {
                launch {
                    state.loading = true
                    when (val response = messagesRepository.createForgotPasscodeOTP(
                        CreateForgotPasscodeOtpRequest(
                            Utils.verifyUsername(username),
                            !Utils.isUsernameNumeric(username)
                        )
                    )) {
                        is RetroApiResponse.Success -> {
                            response.data.data?.let {
                                mobileNumber = it
                            }

                            state.loading = false
                            forgotPasscodeButtonPressEvent.setValue(id)
                        }
                        is RetroApiResponse.Error -> {
                            state.toast = response.error.message
                            state.loading = false
                        }
                    }
                }
            } ?: toast(context, "Invalid user name")
        }
    }
}