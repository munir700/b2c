package co.yap.modules.subaccounts.paysalary.profile

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject


class HHSalaryProfileVM @Inject constructor(override val state: IHHSalaryProfile.State) :
    DaggerBaseViewModel<IHHSalaryProfile.State>() {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }


}