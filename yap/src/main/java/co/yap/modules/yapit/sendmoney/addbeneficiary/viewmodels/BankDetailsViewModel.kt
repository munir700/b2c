package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.BankDetailsState
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class BankDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBankDetails.State>(application), IBankDetails.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val state: BankDetailsState = BankDetailsState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAddBank(id: Int) {

    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
    }
}