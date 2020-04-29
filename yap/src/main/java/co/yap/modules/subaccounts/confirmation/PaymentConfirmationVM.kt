package co.yap.modules.subaccounts.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class PaymentConfirmationVM @Inject constructor(override val state: IPaymentConfirmation.State) :
    DaggerBaseViewModel<IPaymentConfirmation.State>() {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        bundle?.let { state.subAccount.value = it.getParcelable(SubAccount::class.simpleName) }

        //This is temporary
        state.recurringPaymentScreen.value = true
    }

}