package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.viewmodels.ConfirmCardPinViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.ChangeCardPinRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class ConfirmNewCardPinViewModel(application: Application) :
    ConfirmCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private val cardsRepository: CardsRepository = CardsRepository
    override fun onCreate() {
        super.onCreate()
        state.titleSetPin = getString(Strings.screen_confirm_card_pin_display_text_heading)
        state.buttonTitle = getString(Strings.screen_confirm_card_pin_display_button_confirm_pin)
    }

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            changeCardPinRequest(state.oldPin, state.newPin, state.pincode)
        }
    }


    override fun changeCardPinRequest(oldPin: String, newPin: String, confirmPin: String) {
        launch {
            state.loading = true
            when (val response = cardsRepository.changeCardPinRequest(
                ChangeCardPinRequest(
                    oldPin,
                    newPin,
                    confirmPin
                )
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.call()
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}