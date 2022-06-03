package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.subaccounts.paysalary.profile.adapter.HHSalaryProfileTransfersAdapter
import co.yap.modules.subaccounts.paysalary.profile.adapter.SalarySetupAdapter
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.responsedtos.HouseHoldLastNextSalary
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.IBase

interface IHHSalaryProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var customersHHRepository: CustomerHHApi
        var transactionsHHRepository: TransactionsHHApi
        val transactionAdapter: ObservableField<HHSalaryProfileTransfersAdapter>?
        val salarySetupAdapter: ObservableField<SalarySetupAdapter>?
        var txnFilters: TransactionFilters
        fun getLastNextTransaction(uuid: String?)
        fun getAllHHProfileTransactions(accountUUID: String?)
        fun getHHTransactionsByPage(accountUUID: String?, request: HomeTransactionsRequest?,
                                    isLoadMore: Boolean, apiResponse: ((co.yap.widgets.State?, HomeTransactionListData?) -> Unit?))
        fun getPaginationListener(): PaginatedRecyclerView.Pagination?
    }

    interface State : IBase.State {
        var filterCount: ObservableField<Int>
        var isTransEmpty: ObservableField<Boolean>
        var subAccount: MutableLiveData<SubAccount>
        var transactionRequest: HomeTransactionsRequest?
        var lastSalaryTransfer: MutableLiveData<HouseHoldLastNextSalary>?
        var nextSalaryTransfer: MutableLiveData<HouseHoldLastNextSalary>?
        var expense: MutableLiveData<HouseHoldLastNextSalary>?
        var transactionMap: MutableLiveData<MutableMap<String?, List<Transaction>>>?
    }
}