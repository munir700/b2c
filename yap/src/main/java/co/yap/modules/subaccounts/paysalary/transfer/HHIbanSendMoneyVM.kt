package co.yap.modules.subaccounts.paysalary.transfer

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHIbanSendMoneyVM @Inject constructor(override val state: IHHIbanSendMoney.State) :
    DaggerBaseViewModel<IHHIbanSendMoney.State>(), IHHIbanSendMoney.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}