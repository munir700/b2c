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
    }
}