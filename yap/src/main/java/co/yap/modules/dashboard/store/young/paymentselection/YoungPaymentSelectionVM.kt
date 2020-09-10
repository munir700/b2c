package co.yap.modules.dashboard.store.young.paymentselection

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungPaymentSelectionVM @Inject constructor(override val state: IYoungPaymentSelection.State) :
    DaggerBaseViewModel<IYoungPaymentSelection.State>(), IYoungPaymentSelection.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
    override val clickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}