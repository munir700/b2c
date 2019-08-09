package co.yap.app.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.modules.onboarding.viewmodels.CreatePasscodeViewModel

class CreateNewPasscodeViewModel(application: Application):CreatePasscodeViewModel(application) {
    override fun handlePressOnCreatePasscodeButton(id:Int) {
        if (validateAggressively()) {
            nextButtonPressEvent.setValue(id)
        }
    }
}