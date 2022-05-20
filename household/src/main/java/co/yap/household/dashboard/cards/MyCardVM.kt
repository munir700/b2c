package co.yap.household.dashboard.cards

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.household.R
import co.yap.household.dashboard.home.HomeTransactionAdapter
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.CardLimitConfigRequest
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.State
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MyCardVM @Inject constructor(
    override var state: MyCardState,
    private val repository: TransactionsRepository
) : HiltBaseViewModel<IMyCard.State>(), IMyCard.ViewModel {
    private val cardsRepository: CardsApi = CardsRepository
    override val transactionAdapter: ObservableField<HomeTransactionAdapter>? = ObservableField()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        requestTransactions(state.transactionRequest, false)
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

    override fun getPrimaryCard(success: (Boolean) -> Unit) {
        launch {
            when (val response = cardsRepository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            val primaryCard = getPrimaryCard(response.data.data)
                            state.card?.value = primaryCard
                            success.invoke(true)
                        } else {
                            state.toast = "Primary card not found.^${AlertType.TOAST.name}"
                            success.invoke(false)
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.TOAST.name}"
                    success.invoke(false)
                }
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

    override fun requestTransactions(
        transactionRequest: HomeTransactionsRequest?,
        isLoadMore: Boolean, apiResponse: ((State) -> Unit?)?
    ) {
        launch {
            publishState(State.loading(null))
            when (val response =
                repository.getHouseHoldAccountTransactions(state.transactionRequest)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.transaction.isNotEmpty()) {
                        publishState(State.success(null))
                        apiResponse?.invoke(State.success(null))
                        state.transactionMap?.value =
                            response.data.data.transaction.distinct().groupBy { t ->
                                DateUtils.reformatStringDate(
                                    t.creationDate,
                                    DateUtils.SERVER_DATE_FORMAT,
                                    DateUtils.FORMAT_DATE_MON_YEAR, DateUtils.UTC
                                )
                            }
                        transactionAdapter?.get()?.setTransactionData(state.transactionMap?.value)
                    } else {
                        apiResponse?.invoke(State.empty(null))
                        publishState(State.empty(null))
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    apiResponse?.invoke(State.error(null))
                    publishState(State.error(null))
                }
            }
        }
    }

    override fun handleOnClick(id: Int) {

    }

    override fun getPaginationListener(): PaginatedRecyclerView.Pagination? {
        return object : PaginatedRecyclerView.Pagination() {
            override fun onNextPage(page: Int) {
                notifyPageLoaded()
                if (page == 50) {
                    notifyPaginationCompleted()
                }
            }
        }
    }

}
