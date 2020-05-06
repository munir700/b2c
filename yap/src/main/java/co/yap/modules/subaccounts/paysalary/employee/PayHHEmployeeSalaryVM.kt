package co.yap.modules.subaccounts.paysalary.employee

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class PayHHEmployeeSalaryVM @Inject constructor(override val state: IPayHHEmployeeSalary.State) :
    DaggerBaseViewModel<IPayHHEmployeeSalary.State>(), IPayHHEmployeeSalary.ViewModel {
    override var customersHHRepository: CustomerHHApi = CustomersHHRepository
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }
        getLastTransaction(state.subAccount.value?.customerUuid)
    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
    }

    override fun getLastTransaction(uuid: String?) {
        launch {
            publishState(State.loading(null))
            when (val response =
                customersHHRepository.getLastTransaction(state.subAccount.value?.accountUuid)) {
                is RetroApiResponse.Success -> {
                    publishState(State.success(null))
                   // state.subscriptionResponseModel.value = response.data.data
                    state.toast=response.data.toString()
                }
                is RetroApiResponse.Error -> {
                    state.toast=response.error.message
                }
            }
        }
    }
}
