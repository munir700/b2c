package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.viewmodels.ConfirmCardPinViewModel
import co.yap.translation.Strings

class ConfirmNewCardPinViewModel(application: Application) :
    ConfirmCardPinViewModel(application) {
    override fun onCreate() {
        super.onCreate()
        state.titleSetPin=getString(Strings.screen_confirm_card_pin_display_text_heading)
        state.buttonTitle = getString(Strings.screen_confirm_card_pin_display_button_confirm_pin)
    }
}