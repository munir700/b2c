package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomerHHApi
import co.yap.networking.customers.CustomersHHRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHProfileVM @Inject constructor(override val state: IHHProfile.State) :
    DaggerBaseViewModel<IHHProfile.State>() {
    private val repository: CustomerHHApi = CustomersHHRepository
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let {
            val subAccount = it.getParcelable<SubAccount>(SubAccount::class.simpleName)
            getHouseholdUser(subAccount)
        }
    }


    fun getHouseholdUser(account: SubAccount?) {
        launch {
            publishState(State.loading(null))
            when (val response = repository.getHouseholdUser(account?.accountUuid)) {
                is RetroApiResponse.Success -> {
                    publishState(State.success(null))
                    state.customer.value = response.data.hhUser
                }
                is RetroApiResponse.Error -> {
                    publishState(State.empty(response.error.message))
                }
            }
        }

    }
}