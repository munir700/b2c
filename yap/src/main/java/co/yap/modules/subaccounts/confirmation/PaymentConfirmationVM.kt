package co.yap.modules.subaccounts.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class PaymentConfirmationVM @Inject constructor(override val state: IPaymentConfirmation.State) :
    DaggerBaseViewModel<IPaymentConfirmation.State>() {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

}