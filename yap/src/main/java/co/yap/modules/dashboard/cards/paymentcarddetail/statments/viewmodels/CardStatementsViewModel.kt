package co.yap.modules.dashboard.cards.paymentcarddetail.statments.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces.ICardStatments
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.states.CardStatementsState
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardStatementsViewModel(application: Application) :
    BaseViewModel<ICardStatments.State>(application),
    ICardStatments.ViewModel {

    private val transactionRepository: TransactionsRepository = TransactionsRepository
    override val state: CardStatementsState = CardStatementsState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override lateinit var card: Card

    override fun loadStatements(serialNumber: String) {
        launch {
            state.loading = true
            when (val response =
                transactionRepository.getCardStatements(serialNumber)) {
                is RetroApiResponse.Success -> {
                    state.statements.set(response.data.data)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}