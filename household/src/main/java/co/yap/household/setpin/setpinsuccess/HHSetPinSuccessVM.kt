package co.yap.household.setpin.setpinsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHSetPinSuccessVM @Inject constructor(override val state: IHHSetPinSuccess.State) :
    DaggerBaseViewModel<IHHSetPinSuccess.State>(), IHHSetPinSuccess.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
