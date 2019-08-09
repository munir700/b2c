package co.yap.modules.setcardpin.viewmodels

import android.app.Application
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CreateCardPinRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

open class ConfirmCardPinViewModel(application: Application) : SetCardPinViewModel(application),
    IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun setCardPin() {
        launch {
            state.loading = true
            when (val response = repository.createCardPin(
                CreateCardPinRequest(state.pincode),
                MyUserManager.cardSerialNumber.toString()
            )) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_SET_CARD_PIN_SUCCESS)
                    MyUserManager.user?.notificationStatuses = "CARD_ACTIVATED"
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}