package co.yap.modules.subaccounts.paysalary.employee

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class PayHHEmployeeSalaryVM @Inject constructor(override val state: IPayHHEmployeeSalary.State) :
    DaggerBaseViewModel<IPayHHEmployeeSalary.State>(), IPayHHEmployeeSalary.ViewModel {
    override var customersHHRepository: CustomerHHApi = CustomersHHRepository
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        state.subAccount.value?.let {
            getSchedulePayment(it.accountUuid)
            getLastTransaction(it.accountUuid)
        }
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }
    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
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
//            publishState(State.loading(null))
            when (val response =
                customersHHRepository.getSchedulePayment(uuid, "Salary")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.recurringTransaction?.value =
                            it.find { s -> s.isRecurring == true && s.recurringInterval?.isNotEmpty()!! }
                        state.futureTransaction?.value =
                            it.find { s -> s.isRecurring == false && s.recurringInterval?.isEmpty()?:false }
                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    override fun getLastTransaction(uuid: String?) {
        launch {
            when (val response =
                customersHHRepository.getLastTransaction(uuid, "Salary")) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.lastTransaction?.value = it
                    }
                }
                is RetroApiResponse.Error -> {
                }
            }
        }
    }
}
