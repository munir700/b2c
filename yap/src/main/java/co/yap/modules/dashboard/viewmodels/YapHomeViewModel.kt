package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.states.YapHomeState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class YapHomeViewModel(application: Application) : YapDashboardChildViewModel<IYapHome.State>(application),
    IYapHome.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapHomeState = YapHomeState()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context, this)

    private val cardsRepository: CardsRepository = CardsRepository


    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getDebitCards() {

        launch {
            state.loading = true
            when (val response = cardsRepository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.size != 0) {
                        MyUserManager.cardSerialNumber = response.data.data[0].cardSerialNumber
                        clickEvent.setValue(EVENT_SET_CARD_PIN)
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }

    }

}