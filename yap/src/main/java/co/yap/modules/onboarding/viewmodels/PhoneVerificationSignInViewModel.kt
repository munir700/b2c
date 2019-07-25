package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import co.yap.app.login.EncryptionUtils
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.states.PhoneVerificationSignInState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.CreateOtpOnboardingRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class PhoneVerificationSignInViewModel(application: Application) :
    BaseViewModel<IPhoneVerificationSignIn.State>(application), IPhoneVerificationSignIn.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository
    override val state: PhoneVerificationSignInState = PhoneVerificationSignInState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val postDemographicDataResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val verifyOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val customersRepository: CustomersRepository = CustomersRepository;
    private val messagesRepository: MessagesRepository = MessagesRepository;

    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun onCreate() {
        super.onCreate()
        state.reverseTimer(10)
        state.valid = false
    }

    override fun verifyOtp() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.verifyOtpGeneric(
                    VerifyOtpGenericRequest(
                        Constants.ACTION_DEVICE_VERIFICATION,
                        state.otp)
                )) {
                is RetroApiResponse.Success -> {
                    val sharedPreferenceManager = SharedPreferenceManager(context)

                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)

                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_PASSCODE,
                        EncryptionUtils.encrypt(context, state.passcode)!!
                    )
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_USERNAME,
                        EncryptionUtils.encrypt(context, state.username)!!
                    )
                    verifyOtpResult.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }

        }
    }

    override fun handlePressOnResend() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(CreateOtpGenericRequest(Constants.ACTION_DEVICE_VERIFICATION))) {
                is RetroApiResponse.Success -> {
                    state.toast = getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.valid = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun postDemographicData() {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        val deviceId: String? = sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response =
                customersRepository.postDemographicData(
                    DemographicDataRequest(
                        "LOGIN",
                        Build.VERSION.RELEASE,
                        deviceId.toString(),
                        Build.BRAND,
                        Build.MODEL,
                        "Android"
                    )
                )) {
                is RetroApiResponse.Success -> {
                    postDemographicDataResult.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

}