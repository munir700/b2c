package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ITransferType
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.TransferTypeState
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class TransferTypeViewModel(application: Application) :
    SendMoneyBaseViewModel<ITransferType.State>(application), ITransferType.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: TransferTypeState = TransferTypeState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnType(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
    }
}
