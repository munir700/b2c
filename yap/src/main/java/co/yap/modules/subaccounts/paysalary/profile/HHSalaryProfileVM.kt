package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.app.YAPApplication
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
import co.yap.yapcore.enums.TransactionStatus
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
        state.transactionRequest.householdUUID = state.subAccount.value?.accountUuid
        state.transactionRequest.txnCategories = arrayListOf("Expense", "Other", "Salary", "Bonus")
        getLastNextTransaction(state.subAccount.value?.accountUuid)
        //getAllHHProfileTransactions(state.subAccount.value?.accountUuid)

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
                        state.lastSalaryTransfer?.value =
                            it.find { it.txnCategory.equals("Salary") }

                        state.nextSalaryTransfer?.value =
                            it.find { it.txnCategory.equals("Next_Scheduled_Salary") }

                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }


    private fun mergeReduce(newMap: Map<String?, List<Transaction>>) {
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

   override fun setTransactionRequest(filters: TransactionFilters?) {
        filters?.let {
            txnFilters = it
            YAPApplication.homeTransactionsRequest.number = 0
            YAPApplication.homeTransactionsRequest.size = YAPApplication.pageSize
            YAPApplication.homeTransactionsRequest.txnType = it.getTxnType()
            YAPApplication.homeTransactionsRequest.amountStartRange = it.amountStartRange
            YAPApplication.homeTransactionsRequest.amountEndRange = it.amountEndRange
            YAPApplication.homeTransactionsRequest.title = null
            YAPApplication.homeTransactionsRequest.totalAppliedFilter = it.totalAppliedFilter
            YAPApplication.homeTransactionsRequest.categories = it.categories
            YAPApplication.homeTransactionsRequest.statues =
                if (it.pendingTxn == true) arrayListOf(
                    TransactionStatus.PENDING.name,
                    TransactionStatus.IN_PROGRESS.name
                ) else null
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
                            state.isTransEmpty.value = false
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

                            state.transactionMap?.value?.let { newMap->
                                mergeReduce(newMap)
                            } ?: run {
                                state.transactionMap?.value = transactionMap

                            }
                        } else {
                            state.isTransEmpty.value=true
                            setStateValue(State.empty(""))
                        }
                    }?:run {
                        state.isTransEmpty.value=true
                        setStateValue(State.empty(""))
                    }
                }
                is RetroApiResponse.Error -> {
                    setStateValue(State.error(""))
                }
            }
        }
    }

    override fun getHHTransactionsByPage(request: HomeTransactionsRequest,
                                         isLoadMore: Boolean, apiResponse: ((State?, HomeTransactionListData?) -> Unit?)) {
        launch {
            setStateValue(State.loading(null))
            val response =
                transactionsHHRepository.getHHTransactionsByPage(request)
            when (response) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.transaction.isNotEmpty()) {
                        state.isTransEmpty.value = false
                        setStateValue(State.success(null))
                        val tempMap: Map<String?, List<Transaction>> =
                            response.data.data.transaction.sortedByDescending { transactionList ->
                                DateUtils.stringToDate(
                                    transactionList.creationDate ?: "",
                                    DateUtils.SERVER_DATE_FORMAT,
                                    DateUtils.UTC
                                )?.time
                            }.distinct().groupBy { groupTransaction ->
                                    DateUtils.reformatStringDate(
                                        groupTransaction.creationDate,
                                        DateUtils.SERVER_DATE_FORMAT,
                                        DateUtils.FORMAT_DATE_MON_YEAR, DateUtils.UTC
                                    )
                                }
                        state.transactionMap?.value = (((state.transactionMap?.value?.let { newMap ->
                            mergeReduce(newMap)
                        } ?: tempMap) as MutableMap<String?, List<Transaction>>?) )

                        transactionAdapter?.get()?.setTransactionData(state.transactionMap?.value)
                    } else {
                        setStateValue(State.empty(null))
                        state.isTransEmpty.value = state.transactionMap?.value?.isEmpty()?:true
                    }
                    apiResponse.invoke(stateLiveData.value, response.data.data)
                }
                is RetroApiResponse.Error -> {
                    setStateValue(State.error(null))
                    apiResponse.invoke(stateLiveData.value, null)

                }
            }
        }
    }

     override fun getPaginationListener(): PaginatedRecyclerView.Pagination {
        return object : PaginatedRecyclerView.Pagination() {
            override fun onNextPage(page: Int) {
                state.transactionRequest.number = page
                getHHTransactionsByPage(state.transactionRequest, page != 0) { state, date ->
                    notifyPageLoaded()
                    if (date?.last == true || state?.status == Status.IDEAL || state?.status == Status.ERROR) {
                        notifyPaginationCompleted()
                    }
                }
            }
        }
    }
    fun resetFilters() {
        with(state.transactionRequest) {
            amountStartRange = null
            amountEndRange = null
            txnType = null
            title = null
            categories = null
            cardDetailsRequired = null
        }
    }
}