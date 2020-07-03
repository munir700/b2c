package co.yap.modules.subaccounts.paysalary.transfer

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHIbanSendMoneyVM @Inject constructor(override val state: IHHIbanSendMoney.State) :
    DaggerBaseViewModel<IHHIbanSendMoney.State>(), IHHIbanSendMoney.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName) }
    }
}