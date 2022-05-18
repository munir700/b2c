package co.yap.modules.dashboard.store.young.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungPaymentConfirmationVM @Inject constructor(override val state: YoungPaymentConfirmationState) :
    HiltBaseViewModel<IYoungPaymentConfirmation.State>(), IYoungPaymentConfirmation.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
