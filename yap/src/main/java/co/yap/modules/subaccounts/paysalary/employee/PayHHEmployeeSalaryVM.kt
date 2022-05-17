package co.yap.modules.subaccounts.paysalary.employee

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.subaccounts.paysalary.enums.TransactionCategory
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.networking.transactions.household.TransactionsHHRepository
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PayHHEmployeeSalaryVM @Inject constructor(override val state: PayHHEmployeeSalaryState) :
    HiltBaseViewModel<IPayHHEmployeeSalary.State>(), IPayHHEmployeeSalary.ViewModel {
    override var customersHHRepository: CustomerHHApi = CustomersHHRepository
    override var transactionsHHRepository: TransactionsHHApi = TransactionsHHRepository
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        state.subAccount.value?.let {
            getSchedulePayment(it.accountUuid)
            getLastTransaction(it.accountUuid)
        }
    }

    override fun handleOnClick(id: Int) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName) }
    }

//TODO parallel Api Call mechnisam need complete
//    fun parallelRequest(
//        successHandler: (MutableList<ApiResponse?>?) -> Unit,
//        failureHandler: (Throwable?) -> Unit
//    ) {
//        GlobalScope.launch {
//            val sa: MutableList<Deferred<T>> = mutableListOf()
//            val asa: MutableList<RetroApiResponse<T>> = mutableListOf()
//            sa.add(async { customersHHRepository.getLastNextTransaction(state.subAccount.value?.accountUuid) })
//            sa.add(async { customersHHRepository.getLastNextTransaction(state.subAccount.value?.accountUuid) })
//            sa.add(async { customersHHRepository.getLastNextTransaction(state.subAccount.value?.accountUuid) })
//            sa.forEach { t ->
//            }
//        }
//
//        GlobalScope.launch {
//            val getObject1Task =
//                async { customersHHRepository.getLastNextTransaction(state.subAccount.value?.accountUuid) }
//            val getObject2Task =
//                customersHHRepository.getSchedulePayment(state.subAccount.value?.accountUuid)
//            getObject1Task.onAwait
//        }
//        GlobalScope.launch {
//            try {
//                val getObject1Task =
//                    async { customersHHRepository.getLastNextTransaction(state.subAccount.value?.accountUuid) }
//                val getObject2Task =
//                    async { customersHHRepository.getSchedulePayment(state.subAccount.value?.accountUuid) }
//
//                successHandler(getObject1Task.await().body(), getObject2Task.await().body())
//            } catch (exception: Exception) {
//            }
//        }
//
//    }

    override fun getSchedulePayment(uuid: String?) {
        launch {
            when (val response =
                customersHHRepository.getSchedulePayment(uuid,
                    TransactionCategory.Salary.category)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.recurringTransaction?.value =
                            it.find { s -> s.isRecurring == true && s.recurringInterval?.isNotEmpty()!! }
                        state.futureTransaction?.value =
                            it.find { s -> s.isRecurring == false && s.recurringInterval?.isEmpty() ?: false }
                    }
                }
            }
        }
    }

    override fun getLastTransaction(uuid: String?) {
        launch {
            when (val response =
                transactionsHHRepository.getLastTransaction(uuid,
                    TransactionCategory.TRANSACTION.category)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.lastTransaction?.value = it
                    }
                }
            }
        }
    }
}
