package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IUpdateCardName
import co.yap.modules.dashboard.cards.paymentcarddetail.states.UpdateCardNameState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class UpdateCardNameViewModel (application: Application) :
    BaseViewModel<IUpdateCardName.State>(application),
    IUpdateCardName.ViewModel {


    override val state: UpdateCardNameState = UpdateCardNameState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private val cardsRepository: CardsRepository = CardsRepository
    override lateinit var card: Card

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun updateCardName() {
        launch {
            state.loading = true
            when (val response = cardsRepository.updateCardName(state.cardName, card.cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_UPDATE_CARD_NAME)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

}
