package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.SetNewPinViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class UpdateNewPasscodeViewModel(application: Application) : SetNewPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val forgotPasscodeclickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()
        state.titleSetPin = "Enter your new 4-digit \n passcode"
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        state.forgotTextVisibility = true
    }

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            clickEvent.setValue(id)
        }
    }

    override fun handlePressOnForgotPasscodeButton(id: Int) {
        forgotPasscodeclickEvent.postValue(id)
    }
}