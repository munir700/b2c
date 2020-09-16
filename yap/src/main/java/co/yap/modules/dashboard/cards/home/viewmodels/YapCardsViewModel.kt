package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.states.YapCardsState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.managers.MyUserManager

class YapCardsViewModel(application: Application) : BaseViewModel<IYapCards.State>(application),
    IYapCards.ViewModel, IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapCardsState = YapCardsState()
    override val repository: CardsRepository = CardsRepository
    override val cards: MutableLiveData<ArrayList<Card>> = MutableLiveData(arrayListOf())

    override fun getCards() {
        launch {
            state.loading = true
            when (val response = repository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {

                            val cardsList = response.data.data
                            val primaryCard = getPrimaryCard(cardsList)
                            cardsList?.remove(primaryCard)

                            primaryCard?.let {
                                cardsList?.add(0, primaryCard)
                            }
                            if (state.enableAddCard.get())
                                cardsList?.add(getAddCard())

                            cards.value = cardsList
                        }
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun getDebitCard() {
        launch {
            when (val response = repository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val primaryCard = getPrimaryCard(response.data.data)
                            MyUserManager.card.value = primaryCard
                        } else {
                            state.toast = "Debit card not found."
                        }
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
        }
    }

    override fun getPrimaryCard(cards: ArrayList<Card>?): Card? {
        return cards?.firstOrNull { it.cardType == CardType.DEBIT.type }
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

    private fun getAddCard(): Card {
        return Card(
            newPin = "",
            cardType = "DEBIT",
            uuid = "54",
            physical = false,
            active = false,
            cardName = Constants.addCard,
            nameUpdated = false,
            status = "ACTIVE",
            shipmentStatus = "SHIPPED",
            deliveryStatus = "BOOKED",
            blocked = false,
            delivered = false,
            cardSerialNumber = "1000000000612",
            maskedCardNo = "5384",
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


    override fun unFreezeCard(cardSerialNumber: String) {
        launch {
            state.loading = true
            when (val response =
                repository.freezeUnfreezeCard(CardLimitConfigRequest(cardSerialNumber))) {
                is RetroApiResponse.Success -> {
                    Handler().postDelayed({
                        state.loading = false
                        clickEvent.setValue(EVENT_FREEZE_UNFREEZE_CARD)
                    }, 400)

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }

        }
    }
}