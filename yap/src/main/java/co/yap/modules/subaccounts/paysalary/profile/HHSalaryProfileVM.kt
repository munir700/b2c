package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.modules.subaccounts.paysalary.profile.adapter.HHSalaryProfileTransfersAdapter
import co.yap.modules.subaccounts.paysalary.profile.adapter.SalarySetupAdapter
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.networking.transactions.household.TransactionsHHRepository
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.widgets.State
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import javax.inject.Inject

class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    BaseRecyclerAdapterVM<PaySalaryModel, IHHSalaryProfile.State>(), IHHSalaryProfile.ViewModel {
    override var customersHHRepository: CustomerHHApi = CustomersHHRepository
    override var transactionsHHRepository: TransactionsHHApi = TransactionsHHRepository
    override val transactionAdapter: ObservableField<HHSalaryProfileTransfersAdapter>? =
        ObservableField()
    override val salarySetupAdapter: ObservableField<SalarySetupAdapter>? = ObservableField()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getLastNextTransaction(state.subAccount.value?.accountUuid)
        getAllHHProfileTransactions(state.subAccount.value?.accountUuid)
    }

    override fun handleOnClick(id: Int) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName) }

    }

    override fun getLastNextTransaction(uuid: String?) {
        launch {
//            publishState(State.loading(null))
            when (val response =
                transactionsHHRepository.getLastNextTransaction(uuid)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.lastSalaryTransfer?.value = it[0]
//                        state.nextSalaryTransfer?.value = it[1]
//                        state.expense?.value = it[2]
                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun getAllHHProfileTransactions(accountUUID: String?) {
        launch {
            publishState(State.loading(null))
            when (val response =
                transactionsHHRepository.getAllHHProfileTransactions(accountUUID)) {
                is RetroApiResponse.Success -> {
                    response.data.data.let {
                    }
                }
                is RetroApiResponse.Error -> {
                    publishState(State.error(null))
                }
            }
        }
    }

    override fun getHHTransactionsByPage(accountUUID: String?, request: HomeTransactionsRequest?) {
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
}