package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.states.YapCardsState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class YapCardsViewModel(application: Application) : BaseViewModel<IYapCards.State>(application),
    IYapCards.ViewModel, IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapCardsState = YapCardsState()
    override val repository: CardsRepository = CardsRepository

    init {
        state.enableAddCard.set(
            MyUserManager.user?.notificationStatuses.equals(co.yap.modules.onboarding.constants.Constants.USER_STATUS_CARD_ACTIVATED)
        )
    }

    override fun getCards() {

        launch {
            state.loading = true
            when (val response = repository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {
                        MyUserManager.cards.value = response.data.data
                        state.listUpdated.value = true
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun updateCardCount(size: Int) {
        val message = Translator.getString(
            context,
            R.string.screen_cards_display_text_cards_count
        ).replace("%d", size.toString())
        if (size == 1)
            state.noOfCard = message.substring(0, message.length - 1)
        else
            state.noOfCard = message
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}