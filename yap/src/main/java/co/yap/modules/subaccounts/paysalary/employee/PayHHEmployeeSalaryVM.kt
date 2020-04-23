package co.yap.modules.subaccounts.paysalary.employee

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class PayHHEmployeeSalaryVM @Inject constructor(override val state: IPayHHEmployeeSalary.State) :
    DaggerBaseViewModel<IPayHHEmployeeSalary.State>(), IPayHHEmployeeSalary.ViewModel {
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
    }
}
