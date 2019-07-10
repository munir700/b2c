package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.networking.onboarding.requestdtos.VerifyOtpRequest
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleLiveEvent
import java.util.*
import java.util.concurrent.TimeUnit

class PhoneVerificationViewModel(application: Application) :
    OnboardingChildViewModel<IPhoneVerification.State>(application), IPhoneVerification.ViewModel,
    IRepositoryHolder<ObnoardingRepository> {

    override val state: PhoneVerificationState = PhoneVerificationState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository
    val onboardingRepository: ObnoardingRepository = ObnoardingRepository

    override fun onResume() {
        super.onResume()
        setProgress(40)
    }


    override fun onCreate() {
        super.onCreate()
        state.mobileNumber[0] = parentViewModel!!.onboardingData.formattedMobileNumber
        state.reverseTimer(10)
        state.validResend = false
    }

    override fun handlePressOnSendButton() {
        verifyOtp()
    }

    override fun handlePressOnResendOTP() {
        launch {
            state.loading = true
            when (val response =
                onboardingRepository.createOtp(
                    CreateOtpRequest(
                        parentViewModel!!.onboardingData.countryCode,
                        parentViewModel!!.onboardingData.mobileNo,
                        parentViewModel!!.onboardingData.accountType.toString()
                    )
                )) {
                is RetroApiResponse.Success -> {
                    state.toast = getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
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