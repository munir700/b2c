package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpOnboardingRequest
import co.yap.networking.messages.requestdtos.VerifyOtpOnboardingRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.helpers.extentions.trackEvent
import co.yap.yapcore.leanplum.SignupEvents
import co.yap.yapcore.trackAdjustEvent
import com.leanplum.Leanplum

open class PhoneVerificationViewModel(application: Application) :
    OnboardingChildViewModel<IPhoneVerification.State>(application), IPhoneVerification.ViewModel,
    IRepositoryHolder<MessagesRepository> {

    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()

    override val state: PhoneVerificationState = PhoneVerificationState(application)
    //override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: MessagesRepository = MessagesRepository

    override fun onResume() {
        super.onResume()
        setProgress(40)
    }

    override fun onCreate() {
        super.onCreate()
        state.verificationTitle = getString(Strings.screen_verify_phone_number_display_text_title)
        state.verificationDescription = Strings.screen_verify_phone_number_display_text_sub_title
        state.mobileNumber[0] = parentViewModel?.onboardingData?.formattedMobileNumber
        state.validResend = false
    }

    override fun handlePressOnSendButton(id: Int) {
        verifyOtp(id)
    }

    override fun handlePressOnResendOTP(context: Context) {
        launch {
            state.loading = true
            when (val response =
                repository.createOtpOnboarding(
                    CreateOtpOnboardingRequest(
                        parentViewModel?.onboardingData?.countryCode ?: "",
                        parentViewModel?.onboardingData?.mobileNo ?: "",
                        parentViewModel?.onboardingData?.accountType.toString()
                    )
                )) {
                is RetroApiResponse.Success -> {
                    state.toast =
                        getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10,context)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    private fun verifyOtp(id: Int) {
        launch {
            state.loading = true
            when (val response = repository.verifyOtpOnboarding(
                VerifyOtpOnboardingRequest(
                    parentViewModel?.onboardingData?.countryCode ?: "",
                    parentViewModel?.onboardingData?.mobileNo ?: "",
                    state.otp
                )
            )) {
                is RetroApiResponse.Success -> {
                    trackEvent(SignupEvents.SIGN_UP_OTP_CORRECT.type)
                    trackAdjustEvent(AdjustEvents.SIGN_UP_MOBILE_NUMBER_VERIFIED.type)
                    nextButtonPressEvent.call()
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun setPasscode(passcode: String) {
        parentViewModel?.onboardingData?.passcode = passcode
    }
}