package co.yap.household.dashboard.cards

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

class MyCardVM @Inject constructor(override var state: IMyCard.State) :
    BaseRecyclerAdapterVM<Transaction, IMyCard.State>(), IMyCard.ViewModel {
    private val cardsRepository: CardsApi = CardsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        addData(loadJSONDummyList())
    }

    override fun freezeUnfreezeCard(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response =
                cardsRepository.freezeUnfreezeCard(
                    CardLimitConfigRequest(
                        state.card?.value?.cardSerialNumber ?: ""
                    )
                )) {
                is RetroApiResponse.Success -> {
                    Handler().postDelayed({
                        state.loading = false
                        success()
                    }, 400)

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }

        }
    }

    override fun getCardDetails(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response =
                cardsRepository.getCardDetails(state.card?.value?.cardSerialNumber ?: "")) {
                is RetroApiResponse.Success -> {
                    state.cardDetail?.value = response.data.data
                    success()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun getPrimaryCard(success: () -> Unit) {
        launch {
            when (val response = cardsRepository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val primaryCard = getPrimaryCard(response.data.data)
                            state.card?.value = primaryCard
                            success()
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
        return cards?.firstOrNull()
    }

    fun checkFreezeUnfreezeStatus() {
        state.card?.value?.blocked?.let {
            if (it) {
                context.showTextUpdatedAbleSnackBar(
                    msg = getString(Strings.screen_cards_display_text_freeze_card),
                    marginTop = context.dimen(R.dimen.toolbar_height),
                    length = Snackbar.LENGTH_INDEFINITE,
                    clickListener = View.OnClickListener {
                        freezeUnfreezeCard() {
                            checkFreezeUnfreezeStatus()
                        }
                    }
                )
                state.cardStatus.value = Translator.getString(
                    context,
                    Strings.screen_cards_button_unfreeze_card
                )

            } else {
                cancelAllSnackBar()
                state.cardStatus.value = Translator.getString(
                    context,
                    Strings.screen_household_my_card_screen_menu_freeze_card_text
                )
            }
        }
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

    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.setValue(id)
    }

}
