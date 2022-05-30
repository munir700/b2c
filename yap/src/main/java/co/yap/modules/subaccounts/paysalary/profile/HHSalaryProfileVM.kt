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

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun onResume() {
        super.onResume()
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
                        if (!it[0].amount.isNullOrEmpty())
                            state.lastSalaryTransfer?.value = it[0]
                        if (!it[2].amount.isNullOrEmpty())
                            state.nextSalaryTransfer?.value = it[2]
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