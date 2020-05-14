package co.yap.app.modules.login.viewmodels

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.app.modules.login.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getColors

class PhoneVerificationSignInViewModel(application: Application) :
    OnboardingChildViewModel<IPhoneVerificationSignIn.State>(application),
    IPhoneVerificationSignIn.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository
    override val state: co.yap.app.modules.login.states.PhoneVerificationSignInState =
        co.yap.app.modules.login.states.PhoneVerificationSignInState(application)
    override val postDemographicDataResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val verifyOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val customersRepository: CustomersRepository = CustomersRepository;
    private val messagesRepository: MessagesRepository = MessagesRepository
    private var token: String? = ""

    override fun handlePressOnSendButton() {
        verifyOtp()
    }

    override fun onCreate() {
        super.onCreate()
        //state.reverseTimer(10,context)
        state.valid = false
    }

    override fun verifyOtp() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.verifyOtpGeneric(
                    VerifyOtpGenericRequest(
                        Constants.ACTION_DEVICE_VERIFICATION,
                        state.otp
                    )
                )) {
                is RetroApiResponse.Success -> {
                    response.data.token?.let {
                        val tokens = it.split("%")
                        token = tokens.first()
                        if (tokens.size > 1)
                            repository.setJwtToken(tokens.last())
                    }
                    val sharedPreferenceManager = SharedPreferenceManager(context)

                    sharedPreferenceManager.save(
                        KEY_IS_USER_LOGGED_IN,
                        true
                    )
                    sharedPreferenceManager.savePassCodeWithEncryption(state.passcode)
                    sharedPreferenceManager.saveUserNameWithEncryption(state.username)
                    verifyOtpResult.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.otp = ""
                    otpUiBlocked(response.error.actualCode)
                    state.loading = false
                }
            }

        }
    }

    override fun handlePressOnResend(context: Context) {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(CreateOtpGenericRequest(Constants.ACTION_DEVICE_VERIFICATION))) {
                is RetroApiResponse.Success -> {
                    state.toast =
                        getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10, context)
                    state.valid = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    otpUiBlocked(response.error.actualCode)
                }
            }
            state.loading = false
        }
    }

    override fun postDemographicData() {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        val deviceId: String? =
            sharedPreferenceManager.getValueString(KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response =
                customersRepository.postDemographicData(
                    DemographicDataRequest(
                        "LOGIN",
                        Build.VERSION.RELEASE,
                        deviceId.toString(),
                        Build.BRAND,
                        if (Utils.isEmulator()) "generic" else Build.MODEL,
                        "Android",
                        token ?: ""
                    )
                )) {
                is RetroApiResponse.Success -> {
                    postDemographicDataResult.value = true
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    private fun otpUiBlocked(errorCode: String) {
        when (errorCode) {
            "1095" -> {
//                state.validateBtn = false
                state.valid = false
                state.color = context.getColors(R.color.disabled)
                state.isOtpBlocked.set(false)
            }
        }
    }
}