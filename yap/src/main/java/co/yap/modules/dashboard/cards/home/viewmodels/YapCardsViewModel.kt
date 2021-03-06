package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.cards.cardlist.CardListAdapter
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.interfaces.SwipeUpClick
import co.yap.modules.dashboard.cards.home.states.YapCardsState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.wallet.encriptions.utils.EncodingUtils
import co.yap.wallet.samsung.SamsungPayWalletManager
import co.yap.wallet.samsung.isSamsungPayFeatureEnabled
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.managers.SessionManager
import com.google.gson.GsonBuilder
import com.samsung.android.sdk.samsungpay.v2.card.CardManager
import kotlinx.coroutines.delay

class YapCardsViewModel(application: Application) :
    YapDashboardChildViewModel<IYapCards.State>(application),
    IYapCards.ViewModel, IRepositoryHolder<CardsRepository> {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CardsRepository = CardsRepository
    override val state: YapCardsState = YapCardsState()
    override val cards: MutableLiveData<ArrayList<Card>> = MutableLiveData(arrayListOf())
    lateinit var adapter: YapCardsAdaptor
    override var selectedCardPosition: Int = 0
    override val cardAdapter: ObservableField<CardListAdapter>? = ObservableField()

    fun setupAdaptor(context: Context, swipeUpClick: SwipeUpClick) {
        adapter = YapCardsAdaptor(context, mutableListOf(), swipeUpClick)
    }

    override fun getCards() {
        state.showIndicator.set(false)
        launch {
            state.loading = true
            when (val response = repository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val cardsList = response.data.data
                            val primaryCard = SessionManager.getDebitFromList(cardsList)
                            cardsList?.remove(primaryCard)
                            cardsList?.sortByDescending { card ->
                                DateUtils.stringToDate(
                                    card.createdDate ?: "",
                                    DateUtils.SERVER_DATE_FORMAT,
                                    DateUtils.UTC
                                )?.time
                            }
                            primaryCard?.let {
                                cardsList?.add(0, primaryCard)
                            }
                            if (state.enableAddCard.get())
                                cardsList?.add(getAddCard())
                            cards.value = cardsList
                            state.showIndicator.set(true)
                            state.totalCardsCount.set(cardsList?.size?.minus(1))
                            state.enableLeftIcon.set(true)
                            if (context.isSamsungPayFeatureEnabled())
                                checkCardAddedOnSamSungWallet(cards.value)
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

    override fun getUpdatedCard(cardPosition: Int, serailnum: String, card: (Card?) -> Unit) {
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
                            state.showIndicator.set(true)
                            card(cardsList?.firstOrNull { card ->
                                card.cardSerialNumber == serailnum
                            })
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
            pinCreated = true,
            isAddCardIndex = true,
            frontImage = "",
            backImage = ""
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

    override fun getSamsungPayloadAndAddCard(
        cardSerialNumber: String,
        success: (String?, State) -> Unit
    ) {
        launch {
            state.loading = true
            when (val response =
                repository.getCardTokenForSamsungPay(cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                    val toJson =
                        GsonBuilder().disableHtmlEscaping().create()
                            .toJson(response.data.data)
                    val finalPayload =
                        EncodingUtils.base64Encode(toJson.toByteArray(Charsets.UTF_8))
                    SamsungPayWalletManager.getInstance(context)
                        .addYapCardToSamsungPay(finalPayload) {
                            state.loading = false
                            when (it.status) {
                                Status.ERROR -> state.toast =
                                    "${it.message}^${AlertType.DIALOG.name}"
                                Status.LOADING -> state.toast =
                                    "${it.message}^${AlertType.DIALOG.name}"
                                else -> {
                                    checkCardAddedOnSamSungWallet(adapter.getDataList())
                                    state.toast = "${it.message}^${AlertType.DIALOG.name}"
                                }
                            }
                            success.invoke(finalPayload, it)
                        }
                }
                is RetroApiResponse.Error -> {
                    success.invoke(null, State.error(""))
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
//        context.getTestPayloadForSamsung(
//            cardDetail?.cardNumber,
//            cardDetail?.expiryDate?.split("/")?.get(1),
//            cardDetail?.expiryDate?.split("/")?.get(0),
//            cardDetail?.displayName
//        ) { paylaod ->
//            val data = paylaod.toByteArray(StandardCharsets.UTF_8)
//            val finalPayload = EncodingUtils.base64Encode(data)
//            SamsungPayWalletManager.getInstance(context)
//                .addYapCardToSamsungPay(finalPayload) {
//                    state.loading = false
//                    when (it.status) {
//                        Status.ERROR -> state.toast = "${it.message}^${AlertType.DIALOG.name}"
//                        Status.LOADING -> state.toast = "${it.message}^${AlertType.DIALOG.name}"
//                        else -> {
//                            checkCardAddedOnSamSungWallet(adapter.getDataList())
//                            state.toast = "${it.message}^${AlertType.DIALOG.name}"
//                        }
//                    }
//                }
//        }
    }

    private fun checkCardAddedOnSamSungWallet(yapCards: MutableList<Card>?) {
        SamsungPayWalletManager.getInstance(context)
            .getAllCards { samsungPayStatus, allSPayCarad, status ->
                if (status?.status == Status.SUCCESS) {
                    allSPayCarad?.let { sPayCars ->
                        val firstListObjectIds =
                            sPayCars.map { it.cardInfo.getString(CardManager.EXTRA_LAST4_FPAN) }
                                .toSet()
                        val yapSPayCars: List<Card>? =
                            yapCards?.filter {
                                firstListObjectIds.contains(
                                    it.maskedCardNo.takeLast(
                                        4
                                    )
                                )
                            }
                        yapSPayCars?.forEachIndexed { index, card ->
                            card.isAddedSamsungPay = true
                            adapter.getDataList()
                                .find { it.cardSerialNumber == card.cardSerialNumber }?.isAddedSamsungPay =
                                true
                            adapter.notifyItemChanged(adapter.getDataList().indexOf(card))
                        }
                    }
                }
            }
    }

    fun getSPayCardFormYapCard(
        card: Card, sPaycard: (com.samsung.android.sdk.samsungpay.v2.card.Card?) -> Unit
    ) {
        SamsungPayWalletManager.getInstance(context)
            .getAllCards { samsungPayStatus, allSPayCarad, status ->
                status?.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            sPaycard.invoke(allSPayCarad?.let {
                                it.firstOrNull { c ->
                                    c.cardInfo.getString(CardManager.EXTRA_LAST4_FPAN)
                                        ?.contains(card.maskedCardNo.takeLast(4))!!
                                }
                            })
                        }
                        else -> {
                            state.toast = "${it.message}^${AlertType.DIALOG.name}"
                        }
                    }
                }
            }
    }


    override fun openFavoriteCard(cardId: String?, success: (State) -> Unit) {
        state.loading = true
        SamsungPayWalletManager.getInstance(context).openFavoriteCard(cardId) {
            state.loading = false
            when (it.status) {
                Status.ERROR -> state.toast = "${it.message}^${AlertType.DIALOG.name}"
                else -> {
                }
            }
        }
    }

    override fun removeCard(card: Card?) {
        val cardExist = cards.value?.find { cardRemoved ->
            cardRemoved.cardSerialNumber == card?.cardSerialNumber
        }
        if (cardExist != null) {
            selectedCardPosition = adapter.getDataList().indexOf(cardExist)
            adapter.removeItemAt(selectedCardPosition)
            adapter.notifyDataSetChanged()
            updateCardCount(adapter.itemCount - if (state.enableAddCard.get()) 1 else 0)
            state.totalCardsCount.set(adapter.itemCount - if (state.enableAddCard.get()) 1 else 0)
        }
    }

    val list: MutableList<CoreBottomSheetData> = mutableListOf()

    override var cardDetail: ObservableField<CardDetail> = ObservableField()

    override fun getCardDetail(cardSerialNumber: String, successCallback: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.getCardDetails(cardSerialNumber)) {
                is RetroApiResponse.Success -> {
                    cardDetail.set(response.data.data)
                    loadBottomSheetData(cardDetail.get())
                    successCallback()
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
            state.loading = false
        }
    }

    private fun loadBottomSheetData(cardDetails: CardDetail?) {
        list.clear()
        var cardNumber: String? = ""
        cardDetails?.let { card ->
            card.cardNumber.let {
                if (card.cardNumber?.trim()?.contains(" ") == true) {
                    cardNumber = card.cardNumber
                } else {
                    if (card.cardNumber?.length == 16) {
                        val formattedCardNumber: StringBuilder =
                            StringBuilder(card.cardNumber ?: "")
                        formattedCardNumber.insert(4, " ")
                        formattedCardNumber.insert(9, " ")
                        formattedCardNumber.insert(14, " ")
                        cardNumber = formattedCardNumber.toString()
                    }
                }
            }
            list.add(
                CoreBottomSheetData(
                    subTitle = cardNumber,
                    content = cardDetails.expiryDate,
                    subContent = cardDetails.cvv
                )
            )
        }
    }

    override fun isListDisplay(): Boolean {
        return state.isListView.value?.equals(true) == true
    }
}
