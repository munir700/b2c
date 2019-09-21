package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.states.PaymentCardDetailState
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class PaymentCardDetailViewModel(application: Application) :
    BaseViewModel<IPaymentCardDetail.State>(application),
    IPaymentCardDetail.ViewModel {

    override val state: PaymentCardDetailState = PaymentCardDetailState()
    private val cardsRepository: CardsRepository = CardsRepository
    override lateinit var card: Card
    override lateinit var cardDetail : CardDetail
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context)


    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getCardBalance() {
    }

    override fun freezeUnfreezeCard() {
        launch {
            state.loading = true
            when (val response = cardsRepository.freezeUnfreezeCard(CardLimitConfigRequest(card.cardSerialNumber))) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_FREEZE_UNFREEZE_CARD)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun getCardDetails() {
        launch {
            state.loading = true
            when (val response = cardsRepository.getCardDetails(card.cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                    cardDetail = response.data.data
                    clickEvent.setValue(EVENT_CARD_DETAILS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun removeCard() {
    }



}