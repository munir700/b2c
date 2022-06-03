package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.subaccounts.paysalary.profile.adapter.HHSalaryProfileTransfersAdapter
import co.yap.modules.subaccounts.paysalary.profile.adapter.SalarySetupAdapter
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.networking.transactions.household.TransactionsHHRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.hilt.base.viewmodel.BaseRecyclerAdapterVMV2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHSalaryProfileVM @Inject constructor(override val state: HHSalaryProfileState) :
    BaseRecyclerAdapterVMV2<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override var customersHHRepository: CustomerHHApi = CustomersHHRepository
    override var transactionsHHRepository: TransactionsHHApi = TransactionsHHRepository
    override val transactionAdapter: ObservableField<HHSalaryProfileTransfersAdapter>? =
        ObservableField()
    override val salarySetupAdapter: ObservableField<SalarySetupAdapter>? = ObservableField()
    override var txnFilters: TransactionFilters = TransactionFilters()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onResume() {
        super.onResume()

        getLastNextTransaction(state.subAccount.value?.accountUuid)
        getAllHHProfileTransactions(state.subAccount.value?.accountUuid)
        //getHHTransactionsByPage(state.subAccount.value?.accountUuid, )
    }

    override fun handleOnClick(id: Int) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName) }

    }

    override fun getLastNextTransaction(uuid: String?) {
        launch {
            when (val response =
                transactionsHHRepository.getLastNextTransaction(uuid)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (!it[0].amount.isNullOrEmpty())
                            state.lastSalaryTransfer?.value = it[0]
                        if (!it[1].amount.isNullOrEmpty())
                            state.nextSalaryTransfer?.value = it[1]
//                        state.expense?.value = it[2]
                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }


    private fun mergeReduce(newMap: MutableMap<String?, List<Transaction>>) {
        state.transactionMap?.value?.let { map ->
            val tempMap = mutableMapOf<String?, List<Transaction>>()
            var keyToRemove: String? = null
            tempMap.putAll(newMap)
            newMap.keys.forEach { key ->
                if (map.containsKey(key)) {
                    keyToRemove = key
                    return@forEach
                }
            }
            keyToRemove?.let {
                val newTransaction = newMap.getValue(it)
                val oldTransaction = map.getValue(it).toMutableList()
                oldTransaction.addAll(newTransaction)
                state.transactionMap?.value!![it] = oldTransaction
                tempMap.remove(it)
            }
            state.transactionMap?.value?.putAll(tempMap)
        }
    }

    override fun getAllHHProfileTransactions(accountUUID: String?) {
        launch {
            setStateValue(State.loading(null))
            when (val response =
                transactionsHHRepository.getAllHHProfileTransactions(accountUUID)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { transactionList ->
                        if (transactionList.isNotEmpty()) {
                            state.isTransEmpty.set(false)
                            setStateValue(State.success(""))
                            val transactionMap: MutableMap<String?, List<Transaction>> =
                                transactionList.sortedByDescending { sortedTransaction ->
                                    DateUtils.stringToDate(
                                        sortedTransaction.creationDate ?: "",
                                        DateUtils.SERVER_DATE_FORMAT,
                                        DateUtils.UTC
                                    )?.time
                                }.distinct().groupBy { groupTransaction ->
                                        DateUtils.reformatDate(
                                            groupTransaction.creationDate,
                                            DateUtils.SERVER_DATE_FORMAT,
                                            DateUtils.FORMAT_DATE_MON_YEAR, DateUtils.UTC
                                        )
                                    }.toMutableMap()

                            state.transactionMap?.value?.let {
                                mergeReduce(transactionMap)
                            } ?: run {
                                state.transactionMap?.value = transactionMap

                            }
                        } else {
                            state.isTransEmpty.set(true)
                            setStateValue(State.empty(""))
                        }
                    }?:run {
                        setStateValue(State.empty(""))
                    }
                }
                is RetroApiResponse.Error -> {
                    setStateValue(State.error(""))
                }
            }
        }
    }

    override fun getHHTransactionsByPage(accountUUID: String?, request: HomeTransactionsRequest?,
                                         isLoadMore: Boolean, apiResponse: ((State?, HomeTransactionListData?) -> Unit?)) {
        launch {
            publishState(State.loading(null))
            when (val response =
                transactionsHHRepository.getHHTransactionsByPage(accountUUID, request)) {
                is RetroApiResponse.Success -> {
                    response.data.data.let {
                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

     override fun getPaginationListener(): PaginatedRecyclerView.Pagination? {
        return object : PaginatedRecyclerView.Pagination() {
            override fun onNextPage(page: Int) {
                state.transactionRequest?.number = page
                getHHTransactionsByPage(state.subAccount.value?.accountUuid, state.transactionRequest, page != 0) { state, date ->
                    notifyPageLoaded()
                    if (date?.last == true || state?.status == Status.IDEAL || state?.status == Status.ERROR) {
                        notifyPaginationCompleted()
                    }
                }
            }
        }
    }
}