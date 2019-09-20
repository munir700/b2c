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

class YapCardsViewModel(application: Application) : BaseViewModel<IYapCards.State>(application),
    IYapCards.ViewModel, IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapCardsState = YapCardsState()
    override val repository: CardsRepository = CardsRepository

    init {
        getCards()
    }

    override fun getCards() {
        launch {
            state.loading = true
            when (val response = repository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.size != 0) {
                        val dummyList = response.data.data
                        //dummyList.add(getDummyCards(1))
                        //dummyList.add(getDummyCards(2))
                        //dummyList.add(getDummyCards(3))
                        state.cards.value = dummyList
                        state.noOfCard = Translator.getString(
                            context,
                            R.string.screen_cards_display_text_cards_count
                        ).replace("%d", dummyList.size.toString())
                    }
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }

    }

    private fun getDummyCards(type: Int): Card {

        return when (type) {
            1 -> return Card(
                newPin = "",
                cardType = "DEBIT",
                uuid = "542 d2ef0 -9903 - 4 a19 -a691 - 12331357f f15",
                physical = false,
                active = false,
                cardName = "WRYUU FGHH",
                status = "ACTIVE",
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
            2 -> return Card(
                newPin = "",
                cardType = "DEBIT",
                uuid = "542 d2ef0 -9903 - 4 a19 -a691 - 12331357f f15",
                physical = false,
                active = false,
                cardName = "WRYUU FGHH",
                status = "ACTIVE",
                blocked = false,
                delivered = true,
                cardSerialNumber = "1000000000612",
                maskedCardNo = "5381 23 * * * * * * 5744",
                atmAllowed = true,
                onlineBankingAllowed = true,
                retailPaymentAllowed = true,
                paymentAbroadAllowed = true,
                accountType = "B2C_ACCOUNT",
                expiryDate = "09/18",
                cardBalance = "0.00",
                cardScheme = "Master Card",
                currentBalance = "0.00",
                availableBalance = "0.00",
                customerId = "1100000000071",
                accountNumber = "1199999000000071",
                productCode = "CD",
                pinCreated = true
            )
            else -> return Card(
                newPin = "",
                cardType = "DEBIT",
                uuid = "542 d2ef0 -9903 - 4 a19 -a691 - 12331357f f15",
                physical = false,
                active = false,
                cardName = "WRYUU FGHH",
                status = "ACTIVE",
                blocked = true,
                delivered = true,
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

    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}