package co.yap.modules.dashboard.store.young.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungPaymentConfirmationVM @Inject constructor(override val state: IYoungPaymentConfirmation.State) :
    DaggerBaseViewModel<IYoungPaymentConfirmation.State>(), IYoungPaymentConfirmation.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
    }
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}
