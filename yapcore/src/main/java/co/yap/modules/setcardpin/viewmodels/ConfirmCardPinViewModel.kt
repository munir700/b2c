package co.yap.modules.setcardpin.viewmodels

import android.app.Application
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent

open class ConfirmCardPinViewModel(application: Application) : SetCardPinViewModel(application),
    IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setCardPin(cardSerialName: String) {
        launch {
            state.loading = true
            when (val response = repository.createCardPin(
                CreateCardPinRequest(state.pincode),
                cardSerialName
            )) {
                is RetroApiResponse.Success -> {
                    kotlinx.coroutines.delay(600)
                    clickEvent.setValue(EVENT_SET_CARD_PIN_SUCCESS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
}