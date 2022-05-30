package co.yap.household.setpin.setpinsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHSetPinSuccessVM @Inject constructor(override val state: HHSetPinSuccessState) :
    HiltBaseViewModel<IHHSetPinSuccess.State>(), IHHSetPinSuccess.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
