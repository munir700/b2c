package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHProfileVM @Inject constructor(override val state: IHHProfile.State) :
    DaggerBaseViewModel<IHHProfile.State>() {
    private val repository: CustomersRepository = CustomersRepository
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    fun getHouseholdUser(account: SubAccount) {
        launch {
            when (val response = repository.getHouseholdUser(account.accountUuid)) {
                is RetroApiResponse.Success -> {
                }
                is RetroApiResponse.Error -> {
                }
            }
        }

    }
}