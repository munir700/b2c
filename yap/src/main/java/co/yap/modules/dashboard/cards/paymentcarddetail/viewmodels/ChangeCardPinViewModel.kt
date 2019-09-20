package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.viewmodels.SetCardPinViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class ChangeCardPinViewModel(application: Application) : SetCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.titleSetPin=getString(Strings.screen_current_card_pin_display_text_heading)
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
    }

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            clickEvent.setValue(id)
        }
    }

}