package co.yap.app.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.modules.onboarding.viewmodels.CreatePasscodeViewModel

class CreateNewPasscodeViewModel(application: Application):CreatePasscodeViewModel(application) {
    override fun handlePressOnCreatePasscodeButton() {
        if (validateAggressively()) {
            nextButtonPressEvent.value = true
        }
    }
}