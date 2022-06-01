package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.HouseHoldLastNextSalary
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.requestdtos.REQUEST_PAGE_SIZE
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHSalaryProfileState @Inject constructor(): BaseState(), IHHSalaryProfile.State {

    override var filterCount: ObservableField<Int> = ObservableField()
    override var isTransEmpty: ObservableField<Boolean> = ObservableField(true)
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var lastSalaryTransfer: MutableLiveData<HouseHoldLastNextSalary>? = MutableLiveData()
    override var nextSalaryTransfer: MutableLiveData<HouseHoldLastNextSalary>? = MutableLiveData()
    override var expense: MutableLiveData<HouseHoldLastNextSalary>? = MutableLiveData()
    override var transactionMap: MutableLiveData<MutableMap<String?, List<Transaction>>>? = MutableLiveData()
    override var transactionRequest: HomeTransactionsRequest? =
        HomeTransactionsRequest(
            size = REQUEST_PAGE_SIZE,
            searchField = null
        )
}