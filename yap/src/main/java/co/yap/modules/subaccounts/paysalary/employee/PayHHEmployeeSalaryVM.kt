package co.yap.modules.subaccounts.paysalary.employee

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class PayHHEmployeeSalaryVM @Inject constructor(override val state: IPayHHEmployeeSalary.State) : DaggerBaseViewModel<IPayHHEmployeeSalary.State>() {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

}