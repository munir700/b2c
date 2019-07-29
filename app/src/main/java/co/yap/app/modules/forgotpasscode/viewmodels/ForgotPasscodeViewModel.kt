package co.yap.app.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpOnboardingRequest
import co.yap.networking.messages.requestdtos.VerifyOtpOnboardingRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleLiveEvent

class ForgotPasscodeViewModel(application: Application) : PhoneVerificationViewModel(application) {

    override fun onCreate() {
        super.onCreate()
        state.verificationTitle=getString(Strings.screen_verify_phone_number_display_text_title)


        state.verificationTitle = getString(Strings.screen_verify_phone_number_display_text_title)
        state.verificationDescription = Strings.screen_verify_phone_number_display_text_sub_title
        state.mobileNumber[0] = parentViewModel!!.onboardingData.formattedMobileNumber
        state.reverseTimer(10)
        state.validResend = false
    }


    override val state: PhoneVerificationState = PhoneVerificationState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: MessagesRepository = MessagesRepository

    override fun onResume() {
        super.onResume()
        setProgress(40)
    }

    override fun handlePressOnSendButton() {
        verifyOtp()
    }

    override fun handlePressOnResendOTP() {
        launch {
            state.loading = true
            when (val response =
                repository.createOtpOnboarding(
                    CreateOtpOnboardingRequest(
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
            when (val response = repository.verifyOtpOnboarding(
                VerifyOtpOnboardingRequest(
                    parentViewModel!!.onboardingData.countryCode,
                    parentViewModel!!.onboardingData.mobileNo,
                    state.otp
                )
            )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.value = true
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun setPasscode(passcode: String) {
        parentViewModel!!.onboardingData.passcode = passcode
    }

}