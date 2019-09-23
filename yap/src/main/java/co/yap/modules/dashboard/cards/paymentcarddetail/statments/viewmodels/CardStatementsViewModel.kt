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
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardStatementsViewModel(application: Application) :
    BaseViewModel<ICardStatments.State>(application),
    ICardStatments.ViewModel {

    override lateinit var card: Card
    private val transactionRepository : TransactionsRepository = TransactionsRepository
    override val state: CardStatmentsState = CardStatmentsState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    init {
        loadStatements()
    }

    private fun loadStatements() {
        launch {
            state.loading = true
            when (val response = transactionRepository.getCardStatements(card.cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}