package co.yap.modules.dashboard.cards.paymentcarddetail.statments.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.states.PaymentCardDetailState
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces.ICardStatments
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.states.CardStatmentsState
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardStatementsViewModel(application: Application) :
    BaseViewModel<ICardStatments.State>(application),
    ICardStatments.ViewModel {

    override val state: CardStatmentsState = CardStatmentsState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    init {
        loadStatements()
    }

    private fun loadStatements() {
        launch {
//            state.loading = true
//            when (val response = repository.getDebitCards("")) {
//                is RetroApiResponse.Success -> {
//                    if (response.data.data.size != 0) {
//                        state.noOfCard = Translator.getString(
//                            context,
//                            R.string.screen_cards_display_text_cards_count
//                        ).replace("%d", response.data.data.size.toString())
//                        response.data.data.add(getAddCard())
//                        state.cards.value = response.data.data
//                    }
//                }
//                is RetroApiResponse.Error -> state.toast = response.error.message
//            }
//            state.loading = false
        }
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}