package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.states.YapCardsState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.cards.responsedtos.SPayCardData
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.wallet.encriptions.utils.EncodingUtils
import co.yap.wallet.samsung.SamsungPayWalletManager
import co.yap.wallet.samsung.getTestPayloadForSamsung
import co.yap.widgets.Status
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.delay
import java.nio.charset.StandardCharsets

class YapCardsViewModel(application: Application) :
    YapDashboardChildViewModel<IYapCards.State>(application),
    IYapCards.ViewModel, IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository
    override val state: YapCardsState = YapCardsState()
    override val cards: MutableLiveData<ArrayList<Card>> = MutableLiveData(arrayListOf())
    lateinit var adapter: YapCardsAdaptor

    fun setupAdaptor(context: Context) {
        adapter = YapCardsAdaptor(context, mutableListOf())
    }

    override fun getCards() {
        launch {
            state.loading = true
            when (val response = repository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val cardsList = response.data.data
                            val primaryCard = SessionManager.getDebitFromList(cardsList)
                            cardsList?.remove(primaryCard)
                            primaryCard?.let {
                                cardsList?.add(0, primaryCard)
                            }
                            if (state.enableAddCard.get())
                                cardsList?.add(getAddCard())
                            cards.value = cardsList
                        }
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getUpdatedCard(cardPosition: Int, card: (Card?) -> Unit) {
        launch {
            when (val response = repository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {

                            val cardsList = response.data.data
                            val primaryCard = SessionManager.getDebitFromList(cardsList)
                            cardsList?.remove(primaryCard)

                            primaryCard?.let {
                                cardsList?.add(0, primaryCard)
                            }
                            if (state.enableAddCard.get())
                                cardsList?.add(getAddCard())

                            card(cardsList?.get(cardPosition))
                        }
                    }
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                    card(null)
                }
            }
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
            cardSerialNumber = "100",
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
            customerId = "10",
            accountNumber = "100",
            productCode = "CD",
            pinCreated = true
        )
    }

    override fun unFreezeCard(cardSerialNumber: String, success: () -> Unit) {
        launch {
            state.loading = true
            when (val response =
                repository.freezeUnfreezeCard(CardLimitConfigRequest(cardSerialNumber))) {
                is RetroApiResponse.Success -> {
                    delay(500)
                    state.loading = false
                    success.invoke()
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    override fun getCardTokenForSamsungPay(success: (SPayCardData?) -> Unit) {
        launch {
            state.loading = true
            when (val response =
                repository.getCardTokenForSamsungPay()) {
                is RetroApiResponse.Success -> {
                    success.invoke(response.data.data)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    override fun getCardDetails(cardSerialNumber: String, success: (CardDetail?) -> Unit) {
        launch(Dispatcher.Main) {
            state.loading = true
            when (val response = repository.getCardDetails(cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                    addYapCardToSamsungPay(response.data.data)
                    success.invoke(response.data.data)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }

        }
    }

    private fun addYapCardToSamsungPay(cardDetail: CardDetail?) {
        context.getTestPayloadForSamsung(
            cardDetail?.cardNumber,
            cardDetail?.expiryDate?.split("/")?.get(1),
            cardDetail?.expiryDate?.split("/")?.get(0),
            cardDetail?.displayName
        ) { paylaod ->
            val data = paylaod.toByteArray(StandardCharsets.UTF_8)
            val finalPayload = EncodingUtils.base64Encode(data)

            SamsungPayWalletManager.getInstance(context)
                .addYapCardToSamsungPay(finalPayload) {
                    state.loading = false
                    when (it.status) {
                        Status.ERROR -> state.toast = "${it.message}^${AlertType.DIALOG.name}"
                        Status.LOADING -> state.toast = "${it.message}^${AlertType.DIALOG.name}"
                        else -> state.toast = "${it.message}^${AlertType.DIALOG.name}"
                    }
                }
        }
    }

    override fun getAllSamSungCardsCards(cardSerialNumber: String, success: (CardDetail?) -> Unit) {
        SamsungPayWalletManager.getInstance(context).getAllCards { samsungPayStatus, mutableList ->

        }
    }
}
