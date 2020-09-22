package co.yap.modules.subaccounts.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class PaymentConfirmationVM @Inject constructor(override val state: IPaymentConfirmation.State) :
    DaggerBaseViewModel<IPaymentConfirmation.State>(), IPaymentConfirmation.ViewModel {
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName)
            state.schedulePayment.value = it.getParcelable(SchedulePayment::class.simpleName)
        }

    }

    override fun handleOnClick(id: Int) {

    }
}