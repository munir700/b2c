package co.yap.modules.dashboard.store.young.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungPaymentConfirmationVM @Inject constructor(override val state: IYoungPaymentConfirmation.State) :
    DaggerBaseViewModel<IYoungPaymentConfirmation.State>(), IYoungPaymentConfirmation.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {
    }
}
