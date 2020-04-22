package co.yap.modules.subaccounts.householdsetpin.hhsetpinsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHSetPinSuccessVM @Inject constructor(override val state: IHHSetPinSuccess.State) :
    DaggerBaseViewModel<IHHSetPinSuccess.State>(), IHHSetPinSuccess.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}
