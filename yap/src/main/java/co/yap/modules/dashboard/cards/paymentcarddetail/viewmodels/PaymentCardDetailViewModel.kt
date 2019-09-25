package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import android.os.Handler
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.states.PaymentCardDetailState
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

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
        launch {
            state.balanceLoading = true
            when (val response = cardsRepository.getCardBalance(card.cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                    try {
                        val cardBalance : CardBalance = response.data.data
                        card.availableBalance = cardBalance.availableBalance.toString()
                        state.cardBalance =
                            cardBalance.currencyCode+ " " + Utils.getFormattedCurrency(cardBalance.availableBalance)
                    } catch (e:Exception){
                        e.printStackTrace()
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.balanceLoading = false
        }
    }

    override fun freezeUnfreezeCard() {
        launch {
            state.loading = true
            when (val response = cardsRepository.freezeUnfreezeCard(CardLimitConfigRequest(card.cardSerialNumber))) {
                is RetroApiResponse.Success -> {
                    Handler().postDelayed({
                        state.loading = false
                        clickEvent.setValue(EVENT_FREEZE_UNFREEZE_CARD)
                    }, 400)

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }

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
        launch {
            state.loading = true
            when (val response = cardsRepository.removeCard(CardLimitConfigRequest(card.cardSerialNumber))) {
                is RetroApiResponse.Success -> {
                    clickEvent.setValue(EVENT_REMOVE_CARD)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }



}