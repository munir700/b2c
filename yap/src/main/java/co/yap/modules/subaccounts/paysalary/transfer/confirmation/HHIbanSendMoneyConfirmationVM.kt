package co.yap.modules.subaccounts.paysalary.transfer.confirmation

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHIbanSendMoneyConfirmationVM @Inject constructor(override val state: HHIbanSendMoneyConfirmationState) :
    HiltBaseViewModel<IHHIbanSendMoneyConfirmation.State>(),
    IHHIbanSendMoneyConfirmation.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName)
            state.request.value = it.getParcelable(IbanSendMoneyRequest::class.java.simpleName)
        }
    }

    override fun handleOnClick(id: Int) {
    }
}
