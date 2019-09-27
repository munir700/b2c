package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.states.YapCardsState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
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
        if (MyUserManager.getCards() == null || MyUserManager.getCards()?.isEmpty()!!) {
            getCardsList()
        } else {
            manipulateList(MyUserManager.getCards()!!)
        }
    }

    private fun getCardsList() {

        launch {
            state.loading = true
            when (val response = repository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {

                        MyUserManager.cards.value = response.data.data

                        if (state.enableAddCard.get())
                            MyUserManager.cards.value?.add(getAddCard())

                        manipulateList(MyUserManager.getCards()!!)

                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun manipulateList(data: ArrayList<Card>) {
        updateCardCount(data.size - 1)
        state.cardList.set(data)
        state.cardList.notifyChange()
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

    private fun getAddCard(): Card {

        return Card(
            newPin = "",
            cardType = "DEBIT",
            uuid = "542 d2ef0 -9903 - 4 a19 -a691 - 12331357f f15",
            physical = false,
            active = false,
            cardName = Constants.addCard,
            status = "ACTIVE",
            shipmentStatus = "SHIPPED",
            deliveryStatus = "BOOKED",
            blocked = false,
            delivered = false,
            cardSerialNumber = "1000000000612",
            maskedCardNo = "5381 23 * * * * * * 5744",
            atmAllowed = true,
            onlineBankingAllowed = true,
            retailPaymentAllowed = true,
            paymentAbroadAllowed = true,
            accountType = "B2C_ACCOUNT",
            expiryDate = "09/24",
            cardBalance = "0.00",
            cardScheme = "Master Card",
            currentBalance = "0.00",
            availableBalance = "0.00",
            customerId = "1100000000071",
            accountNumber = "1199999000000071",
            productCode = "CD",
            pinCreated = true
        )
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}