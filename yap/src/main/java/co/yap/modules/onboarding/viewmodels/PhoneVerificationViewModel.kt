package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.VerifyOtpRequest
import co.yap.yapcore.SingleLiveEvent
import java.util.*
import java.util.concurrent.TimeUnit

class PhoneVerificationViewModel(application: Application) :
    OnboardingChildViewModel<IPhoneVerification.State>(application), IPhoneVerification.ViewModel,
    IRepositoryHolder<ObnoardingRepository> {

    override val state: PhoneVerificationState = PhoneVerificationState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository

    override fun onResume() {
        super.onResume()
        setProgress(40)
    }


    override fun onCreate() {
        super.onCreate()
        state.mobileNumber[0] = parentViewModel!!.onboardingData.formattedMobileNumber
    }

    override fun handlePressOnSendButton() {
        verifyOtp()
    }

    override fun handlePressOnResendOTP() {

    }

    private fun verifyOtp() {
        launch {
            state.loading = true
            when (val response = repository.verifyOtp(
                VerifyOtpRequest(
                    parentViewModel!!.onboardingData.countryCode,
                    parentViewModel!!.onboardingData.mobileNo,
                    state.otp
                )
            )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.value = true
                }
                is RetroApiResponse.Error -> state.error = response.error.message
            }
            state.loading = false
        }
    }

    override fun setPasscode(passcode: String) {
        parentViewModel!!.onboardingData.passcode = passcode
    }
}