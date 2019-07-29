package co.yap.app.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.translation.Strings

class ForgotPasscodeViewModel(application: Application) : PhoneVerificationViewModel(application) {

    override fun onCreate() {
        super.onCreate()
        state.verificationTitle=getString(Strings.screen_verify_phone_number_display_text_title)

    }

}