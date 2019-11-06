package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryOverview
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.BeneficiaryOverviewState
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class BeneficiaryOverviewViewModel(application: Application) :
    SendMoneyBaseViewModel<IBeneficiaryOverview.State>(application), IBeneficiaryOverview.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: BeneficiaryOverviewState = BeneficiaryOverviewState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnAddNow(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnConfirm(id: Int) {
        clickEvent.setValue(id)
    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_edit_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
    }
}