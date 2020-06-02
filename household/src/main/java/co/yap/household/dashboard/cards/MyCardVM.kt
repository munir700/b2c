package co.yap.household.dashboard.cards

import android.os.Bundle
import android.os.Handler
import androidx.navigation.NavController
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.helpers.Utils
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

class MyCardVM @Inject constructor(override var state: IMyCard.State) :
    BaseRecyclerAdapterVM<Transaction, IMyCard.State>(), IMyCard.ViewModel {
    private val cardsRepository: CardsApi = CardsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var cardDetail: CardDetail = CardDetail()
    override var card: Card? = null


    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        addData(loadJSONDummyList())

    }

    override fun freezeUnfreezeCard() {
        launch {
            state.loading = true
            when (val response =
                cardsRepository.freezeUnfreezeCard(CardLimitConfigRequest(card?.cardSerialNumber!!))) {
                is RetroApiResponse.Success -> {
                    Handler().postDelayed({
                        state.loading = false
                        clickEvent.setValue(EVENT_FREEZE_UNFREEZE_CARD)
                    }, 400)

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }

        }
    }

    override fun getCardDetails() {
        launch {
            state.loading = true
            when (val response =
                cardsRepository.getCardDetails(card?.cardSerialNumber!!)) {
                is RetroApiResponse.Success -> {
                    cardDetail = response.data.data
                    clickEvent.setValue(EVENT_CARD_DETAILS)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun getPrimaryCard() {
        launch {
            when (val response = cardsRepository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val primaryCard = getPrimaryCard(response.data.data)
                            card = primaryCard
                        } else {
                            state.toast = "Primary card not found.^${AlertType.TOAST.name}"
                        }
                    }
                }
                is RetroApiResponse.Error ->
                    state.toast = "${response.error.message}^${AlertType.TOAST.name}"
            }
        }
    }


    private fun getPrimaryCard(cards: ArrayList<Card>?): Card? {
        return cards?.firstOrNull { it.cardType == CardType.DEBIT.type }
    }

    private fun loadJSONDummyList(): ArrayList<Transaction> {
        val benefitsModelList: ArrayList<Transaction> = ArrayList<Transaction>()
        val mainObj = JSONObject(Utils.loadJsonFromAssets(context, "card_transactions.json"))
        val mainDataList = mainObj.getJSONObject("data")
        val content = mainDataList.getJSONArray("content")
        if (content != null) {
            for (i in 0 until content.length()) {
                val gson = Gson()
                val transactionData = gson.fromJson(content.getString(i), Transaction::class.java)
                benefitsModelList.add(transactionData)
            }
        }
        return benefitsModelList
    }

    /*override fun getDummyCard(): Card? {
        return Card(
            cardType = "PREPAID",
            uuid = "b4ba4040-d904-4742-96aa-374ce6ed6112",
            physical = false,
            active = false,
            cardName = "Hassnain Ali",
            status = "HOTLISTED",
            blocked = false,
            delivered = false,
            cardSerialNumber = "1000000002095",
            maskedCardNo = "5370 38** **** 7529",
            atmAllowed = true,
            onlineBankingAllowed = true,
            retailPaymentAllowed = true,
            paymentAbroadAllowed = true,
            accountType = "B2C_ACCOUNT",
            expiryDate = "11/22",
            cardBalance = "0.00",
            cardScheme = "Master Card",
            currentBalance = "0.00",
            availableBalance = "0.00",
            customerId = "3000000000112",
            accountNumber = "0188000000469",
            productCode = "CS",
            pinCreated = false,
            deliveryStatus = "ORDERED",
            shipmentStatus = null,
            nameUpdated = true,
            newPin = "",
            frontImage = "",
            backImage = ""
        )
    }*/

    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.setValue(id)
    }

}
