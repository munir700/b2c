package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class HHIbanSendMoneyConfirmationVM @Inject constructor(override val state: IHHIbanSendMoneyConfirmation.State):
    DaggerBaseViewModel<IHHIbanSendMoneyConfirmation.State>(), IHHIbanSendMoneyConfirmation.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }


}