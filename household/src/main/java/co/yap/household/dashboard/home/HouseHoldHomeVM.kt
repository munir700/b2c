package co.yap.household.dashboard.home

import android.os.Bundle
import android.os.Handler
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.CardsRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.UTC
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class HouseHoldHomeVM @Inject constructor(
    override var state: IHouseholdHome.State,
    private val repository: TransactionsRepository
) : DaggerBaseViewModel<IHouseholdHome.State>(), IHouseholdHome.ViewModel {
    private val cardsRepository: CardsApi = CardsRepository
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionAdapter: ObservableField<HomeTransactionAdapter>? = ObservableField()
    override var notificationAdapter = ObservableField<HHNotificationAdapter>()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        requestTransactions(state.transactionRequest, false)
        getPrimaryCard()
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun requestTransactions(
        transactionRequest: HomeTransactionsRequest?,
        isLoadMore: Boolean, apiResponse: ((State?, HomeTransactionListData?) -> Unit?)?
    ) {
        launch {
            if (!isLoadMore)
                publishState(State.loading(null))
            when (val response =
                repository.getAccountTransactions(state.transactionRequest)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.transaction.isNotEmpty()) {
                        publishState(State.success(null))

                        state.transactionMap?.value =
                            response.data.data.transaction.distinct().groupBy { t ->
                                DateUtils.reformatStringDate(
                                    t.creationDate,
                                    SERVER_DATE_FORMAT,
                                    FORMAT_DATE_MON_YEAR, UTC
                                )
                            }
                        transactionAdapter?.get()?.setTransactionData(state.transactionMap?.value)
//                        apiResponse?.invoke(State.success(null),response.data.data)
                    } else {
//                        if (!isLoadMore)
                            publishState(State.empty(null))
//                        apiResponse?.invoke(State.success(null),response.data.data)
                    }
                    apiResponse?.invoke(stateLiveData.value, response.data.data)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    publishState(State.error(null))
                    apiResponse?.invoke(stateLiveData.value, null)

                }
            }
        }
    }

    override fun getPrimaryCard() {
        launch {
            state.accountActivateLiveData?.value = State.loading(null)
            when (val response = cardsRepository.getDebitCards("")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { it ->
                        state.accountActivateLiveData?.value = State.success(null)
                        if (it.isNotEmpty()) {
                            state.card?.value =
                                response.data.data?.firstOrNull { it.cardType == CardType.DEBIT.type }
                            notificationAdapter.notifyChange()
                            notificationAdapter.get()?.setData(
                                NotificationHelper.getNotifications(
                                    MyUserManager.user, state.card?.value, context
                                )
                            )
                        } else {
                            state.toast = "Primary card not found.^${AlertType.TOAST.name}"
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.accountActivateLiveData?.value = State.success(null)
                    state.toast = "${response.error.message}^${AlertType.TOAST.name}"
                }
            }
        }
    }

    override fun getPaginationListener(): PaginatedRecyclerView.Pagination? {
        return object : PaginatedRecyclerView.Pagination() {
            override fun onNextPage(page: Int) {
                state.transactionRequest?.number = page
                requestTransactions(state.transactionRequest, page != 0) { state, date ->
                    notifyPageLoaded()
                    if (date?.last == true || state?.status == Status.EMPTY) {
                        notifyPaginationCompleted()
                    }
                }
            }
        }
    }
}
