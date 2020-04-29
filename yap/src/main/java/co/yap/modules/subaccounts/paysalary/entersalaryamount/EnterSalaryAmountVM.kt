package co.yap.modules.subaccounts.paysalary.entersalaryamount

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class EnterSalaryAmountVM @Inject constructor(override var state: IEnterSalaryAmount.State) :
    DaggerBaseViewModel<IEnterSalaryAmount.State>(), IEnterSalaryAmount.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }
    }
}