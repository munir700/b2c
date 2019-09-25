package co.yap.modules.dashboard.cards.reportcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentChildViewModel
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.states.ReportOrStolenCardState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardsHotlistRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class ReportLostOrStolenCardViewModels(application: Application) :
    AddPaymentChildViewModel<IRepostOrStolenCard.State>(application),
    IRepostOrStolenCard.ViewModel,
    IRepositoryHolder<CardsRepository> {

    private val transactionRepository: TransactionsRepository = TransactionsRepository

    override var HOT_LIST_REASON: Int = 2
    val REASON_DAMAGE: Int = 2
    val REASON_LOST_STOLEN: Int = 4

    override val CARD_REORDER_SUCCESS: Int = 5000
    override var cardFee: String = "50"

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: ReportOrStolenCardState = ReportOrStolenCardState()
    override val repository: CardsRepository = CardsRepository

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(getString(Strings.screen_report_card_display_text_title))

    }

    override fun handlePressOnDamagedCard(id: Int) {
        state.valid = true
        HOT_LIST_REASON = REASON_DAMAGE
        clickEvent.setValue(id)

    }

    override fun handlePressOnLostOrStolen(id: Int) {
        state.valid = true
        HOT_LIST_REASON = REASON_LOST_STOLEN
        clickEvent.setValue(id)

    }

    override fun handlePressOnReportAndBlockButton(id: Int) {
        clickEvent.setValue(id)
    }


    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun requestConfirmBlockCard(card: Card) {

        val cardsHotlistReequest: CardsHotlistRequest =
            CardsHotlistRequest(card.cardSerialNumber, HOT_LIST_REASON.toString())

        launch {
            state.loading = true
            when (val response = repository.reportAndBlockCard(cardsHotlistReequest)) {
                is RetroApiResponse.Success -> {

                    if (state.cardType.equals(
                            Translator.getString(
                                context!!,
                                Strings.screen_spare_card_landing_display_text_virtual_card
                            )
                        )
                    ) {
                        state.loading = false
                        toggleToolBarVisibility(false)
                        clickEvent.setValue(CARD_REORDER_SUCCESS)
                    } else {
                        getPhysicalCardFee()
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message

/*                  //remove
                    state.loading = false
                    toggleToolBarVisibility(false)
                    clickEvent.setValue(CARD_REORDER_SUCCESS)*/
                }
            }
            state.loading = false
        }
    }

    override fun getPhysicalCardFee() {
        launch {
            when (val response = transactionRepository.getCardFee("physical")) {
                is RetroApiResponse.Success -> {
                    cardFee = response.data.data.currency + " " + response.data.data.amount
                    toggleToolBarVisibility(false)
                    clickEvent.setValue(CARD_REORDER_SUCCESS)

                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

}