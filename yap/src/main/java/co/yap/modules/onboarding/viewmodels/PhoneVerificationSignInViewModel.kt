package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import co.yap.app.constants.Constants
import co.yap.app.login.EncryptionUtils
import co.yap.modules.onboarding.activities.PhoneVerificationSignInActivity
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.states.PhoneVerificationSignInState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.authentication.requestdtos.DemographicDataRequest
import co.yap.networking.authentication.requestdtos.VerifyOtpRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.OnboardingApi
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class PhoneVerificationSignInViewModel(application: Application) :
    BaseViewModel<IPhoneVerificationSignIn.State>(application), IPhoneVerificationSignIn.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository
    val onboardingRepository: ObnoardingRepository = ObnoardingRepository
    override val state: PhoneVerificationSignInState = PhoneVerificationSignInState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val postDemographicDataResult: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val verifyOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun onCreate() {
        super.onCreate()
        state.reverseTimer(10)
        //state.valid=false
    }

    override fun verifyOtp() {
        launch {
            state.loading = true
            when (val response =
                repository.verifyOtp(VerifyOtpRequest(Constants.ACTION_DEVICE_VERIFICATION, state.otp))) {
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
                }
            }
            state.loading = false
        }
    }

    override fun handlePressOnResend() {
        launch {
            state.loading = true
            when (val response =
                onboardingRepository.createOtp(CreateOtpRequest("", "", ""))) {
                is RetroApiResponse.Success -> {
                    state.reverseTimer(10)
                   // state.valid=false
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
                repository.postDemographicData(
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