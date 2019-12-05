package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferSuccess
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.TransferSuccessState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class TransferSuccessViewModel(application: Application) :
    SendMoneyBaseViewModel<ITransferSuccess.State>(application), ITransferSuccess.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: TransferSuccessState = TransferSuccessState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnGoBackToDashboard(id: Int) {
        clickEvent.setValue(id)
    }


    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(false)
    }
}