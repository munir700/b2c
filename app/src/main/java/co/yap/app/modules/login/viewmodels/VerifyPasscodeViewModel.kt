package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.constants.Constants
import co.yap.app.login.EncryptionUtils
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.states.VerifyPasscodeState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import java.util.regex.Pattern

class VerifyPasscodeViewModel(application: Application) :
    BaseViewModel<IVerifyPasscode.State>(application),
    IVerifyPasscode.ViewModel, IRepositoryHolder<AuthRepository> {

    override val forgotPasscodeButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val repository: AuthRepository = AuthRepository
    override val state: VerifyPasscodeState = VerifyPasscodeState()
    override val signInButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val loginSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val validateDeviceResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val createOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var isFingerprintLogin: Boolean = false
    private val customersRepository: CustomersRepository = CustomersRepository
    override var emailOtp: Boolean = false
    override var mobileNumber: String = ""
    override var EVENT_LOGOUT_SUCCESS: Int = 101


    private val messagesRepository: MessagesRepository = MessagesRepository

    override fun login() {
        launch {
            state.loading = true
            when (val response = repository.login(state.username, state.passcode)) {
                is RetroApiResponse.Success -> {
                    loginSuccess.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    // state.toast = response.error.message
                    loginSuccess.postValue(false)

                }
            }
            state.loading = false
        }
    }

    override fun handlePressOnForgotPasscodeButton(id: Int) {
        var sharedPreferenceManager: SharedPreferenceManager = SharedPreferenceManager(context)
        var username: String = ""
        if (!sharedPreferenceManager.getValueBoolien(
                SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            username = state.username
        } else {
            //TODO need to fix this crash , follow these logs below:
          /*  Process: co.yap.dev, PID: 12664
            kotlin.TypeCastException: null cannot be cast to non-null type kotlin.String
            at co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel.handlePressOnForgotPasscodeButton(VerifyPasscodeViewModel.kt:70)
            at co.yap.app.databinding.FragmentVerifyPasscodeBindingImpl._internalCallbackOnClick(FragmentVerifyPasscodeBindingImpl.java:285)
            at co.yap.app.generated.callback.OnClickListener.onClick(OnClickListener.java:11)
            at android.view.View.performClick(View.java:6663)
            at android.view.View.performClickInternal(View.java:6635)
            at android.view.View.access$3100(View.java:794)
            at android.view.View$PerformClick.run(View.java:26199)
            at android.os.Handler.handleCallback(Handler.java:907)
            at android.os.Handler.dispatchMessage(Handler.java:105)
            at android.os.Looper.loop(Looper.java:216)
            at android.app.ActivityThread.main(ActivityThread.java:7625)
            at java.lang.reflect.Method.invoke(Native Method)
            at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:524)
            at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:987)*/
            username = EncryptionUtils.decrypt(
                context,
                sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
            )!!
        }
        launch {
            state.loading = true
            when (val response = messagesRepository.createForgotPasscodeOTP(
                CreateForgotPasscodeOtpRequest(
                    verifyUsername(username),
                    emailOtp
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
    }

    private fun verifyUsername(enteredUsername: String): String {
        var username = enteredUsername
        if (isUsernameNumeric(username)) {
            emailOtp = false
            if (username.startsWith("+")) {
                username = username.replace("+", "00")
                return username
            } else if (username.startsWith("00")) {
                return username
            } else if (username.startsWith("0")) {
                username = username.substring(1, username.length)
                return username
            } else {
                return username
            }
        } else {
            emailOtp = true
            return username
        }
    }

    fun isUsernameNumeric(username: String): Boolean {
        var inputStr: CharSequence
        var isValid = false
        val expression = "^[0-9+]*\$"

        inputStr = username
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    override fun validateDevice() {
        launch {
            when (val response = customersRepository.validateDemographicData(state.deviceId)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it) state.loading = false
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

    override fun handlePressOnSignInButton() {
        signInButtonPressEvent.postValue(true)
    }


    override fun logout() {
        val deviceId: String? =
            SharedPreferenceManager(context).getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
             when (val response = repository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    forgotPasscodeButtonPressEvent.setValue(EVENT_LOGOUT_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    forgotPasscodeButtonPressEvent.setValue(EVENT_LOGOUT_SUCCESS)
                }
            }
        }
    }

}