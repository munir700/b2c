package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.viewmodels.ConfirmCardPinViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.ChangeCardPinRequest
import co.yap.networking.cards.requestdtos.ForgotCardPin
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

open class ConfirmNewCardPinViewModel(application: Application) :
    ConfirmCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()
    private val cardsRepository: CardsRepository = CardsRepository
    private val messagesRepository: MessagesRepository = MessagesRepository

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            if (state.newPin == state.pincode) {
                if (state.flowType != Constants.FORGOT_CARD_PIN_FLOW) {
                    changeCardPinRequest(
                        state.oldPin,
                        state.newPin,
                        state.pincode,
                        state.cardSerialNumber,
                        id
                    )
                } else {
                    clickEvent.postValue(id)
                }


            } else {
                errorEvent.call()

            }
        }
    }


    override fun changeCardPinRequest(
        oldPin: String,
        newPin: String,
        confirmPin: String,
        cardSerialNumber: String,
        id: Int
    ) {
        launch {
            state.loading = true
            when (val response = cardsRepository.changeCardPinRequest(
                ChangeCardPinRequest(
                    oldPin,
                    newPin,
                    confirmPin, cardSerialNumber
                )
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(id)
                }
                is RetroApiResponse.Error ->
                    state.dialerError = response.error.message
            }
            state.loading = false
        }
    }

    override fun forgotCardPinRequest(cardSerialNumber: String, newPin: String, token: String) {
        launch {
            state.loading = true
            when (val response = cardsRepository.forgotCardPin(
                cardSerialNumber,
                ForgotCardPin(newPin, token)
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.postValue(Constants.FORGOT_CARD_PIN_NAVIGATION)
                }
                is RetroApiResponse.Error ->
                    state.dialerError = response.error.message
            }
            state.loading = false
        }
    }
}