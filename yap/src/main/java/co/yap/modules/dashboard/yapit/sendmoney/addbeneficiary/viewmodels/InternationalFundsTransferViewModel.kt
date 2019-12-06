package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.InternationalFundsTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class InternationalFundsTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<IInternationalFundsTransfer.State>(application),
    IInternationalFundsTransfer.ViewModel,
    IRepositoryHolder<CustomersRepository> {


    override val repository: CustomersRepository = CustomersRepository

    override val state: InternationalFundsTransferState = InternationalFundsTransferState(application)

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
        toggleAddButtonVisibility(false)
    }
}