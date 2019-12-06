package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.BeneficiaryAccountDetailsState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class BeneficiaryAccountDetailsViewModel(application: Application) :
    SendMoneyBaseViewModel<IBeneficiaryAccountDetails.State>(application),
    IBeneficiaryAccountDetails.ViewModel/*,
    IRepositoryHolder<CustomersRepository>*/ {

    override val state: BeneficiaryAccountDetailsState = BeneficiaryAccountDetailsState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAddBank(id: Int) {
        clickEvent.setValue(id)
    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
    }
}