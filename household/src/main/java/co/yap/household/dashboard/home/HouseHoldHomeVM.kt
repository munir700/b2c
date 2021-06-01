package co.yap.household.dashboard.home

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.networking.cards.CardsApi
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FORMAT
import co.yap.yapcore.helpers.DateUtils.UTC
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import javax.inject.Inject

class HouseHoldHomeVM @Inject constructor(
    override var state: IHouseholdHome.State,
    private val repository: TransactionsRepository,
    private val expandableItemManager: RecyclerViewExpandableItemManager
) : DaggerBaseViewModel<IHouseholdHome.State>(), IHouseholdHome.ViewModel {
    private val cardsRepository: CardsApi = CardsRepository
    override var transactionAdapter: ObservableField<HomeTransactionAdapter>? = ObservableField()
    override var notificationAdapter = ObservableField<HHNotificationAdapter>()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        //requestTransactions(state.transactionRequest, false)
        getPrimaryCard()
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
                        var tempMap: Map<String?, List<Transaction>>
                        tempMap =
                            response.data.data.transaction.sortedByDescending { t ->
                                DateUtils.stringToDate(
                                    t.creationDate ?: "",
                                    SERVER_DATE_FORMAT,
                                    UTC
                                )?.time
                            }
                                .distinct().groupBy { t ->
                                    DateUtils.reformatStringDate(
                                        t.creationDate,
                                        SERVER_DATE_FORMAT,
                                        FORMAT_DATE_MON_YEAR, UTC
                                    )
                                }
                        state.transactionMap?.value = state.transactionMap?.value?.let {
                            tempMap.mergeReduce(other = it)
                        } ?: tempMap

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

    override fun orderHouseHoldPhysicalCardRequest(
        address: Address,
        apiResponse: ((Boolean) -> Unit?)?
    ) {
        launch {
            state.loading = true
            when (val response =
                cardsRepository.orderCard(
                    address
                )) {
                is RetroApiResponse.Success -> {
                    trackEventWithAttributes(SessionManager.user, card_color = address.designCode)
                    apiResponse?.invoke(true)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun getPrimaryCard() {
        launch {
            state.accountActivateLiveData?.value = State.loading(null)

            when (val response = cardsRepository.getDebitCards(CardType.PREPAID.type)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { it ->
                        state.accountActivateLiveData?.value = State.success(null)
                        if (it.isNotEmpty()) {

                            SessionManager.getPrepaidFromList(response.data.data)
                                ?.let { prepaidCard ->
                                    SessionManager.updateCard(prepaidCard)
                                    state.card?.value = prepaidCard
                                }

                            notificationAdapter.notifyChange()
                            notificationAdapter.get()?.setData(
                                NotificationHelper.getNotifications(
                                    SessionManager.user, state.card?.value, context
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

    override fun handleOnClick(id: Int) {
    }
    //    private fun <K, V> Map<K, V>.mergeReduce(other: Map<K, V>, reduce: (V, V) -> V = { a, b -> b }): Map<K, V> {
//        val result = LinkedHashMap<K, V>(this.size + other.size)
//        result.putAll(this)
//        other.forEach { e ->
//            val existing = result[e.key]
//
//            if (existing == null) {
//                result[e.key] = e.value
//            }
//            else {
//                result[e.key] = reduce(e.value, existing)
//            }
//        }
//
//        return result
//    }


}

fun <K, V> Map<K, V>.mergeReduce(other: Map<K, V>, reduce: (V, V) -> V = { a, b -> b }): Map<K, V> {
    val result = LinkedHashMap<K, V>(this.size + other.size)
    result.putAll(this)
    other.forEach { e -> result[e.key] = result[e.key]?.let { reduce(e.value, it) } ?: e.value }
    return result
}
