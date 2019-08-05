package co.yap.app.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.app.modules.forgotpasscode.states.ForgotPasscodeOtpState
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpOnboardingRequest
import co.yap.networking.messages.requestdtos.VerifyOtpOnboardingRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class ForgotPasscodeOtpViewModel(application: Application) : BaseViewModel<IForgotPasscodeOtp.State>(application),IForgotPasscodeOtp.ViewModel {
    override val state: ForgotPasscodeOtpState = ForgotPasscodeOtpState(application)
    override val nextButtonPressEvent: SingleClickEvent= SingleClickEvent()
//    override val repository: MessagesRepository = MessagesRepository

    override fun onCreate() {
        super.onCreate()
        state.verificationTitle = getString(Strings.screen_verify_phone_number_display_text_title)


        state.verificationDescription = Strings.screen_verify_phone_number_display_text_sub_title
//        state.mobileNumber[0] = parentViewModel!!.onboardingData.formattedMobileNumber
        state.mobileNumber[0] = "5021902"
        state.reverseTimer(10)
        state.validResend = false
    }

    override fun handlePressOnSendButton(id :Int) {
        verifyOtp(id)
    }

    override fun handlePressOnResendOTP(id:Int) {
        //nextButtonPressEvent.setValue(id)
        state.toast="resend otp detected"

        launch {
            state.loading = true
            /*when (val response =
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
            }*/
            state.loading = false
        }

    }

    override fun setPasscode(passcode: String) {
    }
    private fun verifyOtp(id:Int) {
        launch {
            state.loading = true
         /*   when (val response = repository.verifyOtpOnboarding(
                VerifyOtpOnboardingRequest(
                    parentViewModel!!.onboardingData.countryCode,
                    parentViewModel!!.onboardingData.mobileNo,
                    state.otp
                )
            )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.call()
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }*/
            state.loading = false
        }
    }
}