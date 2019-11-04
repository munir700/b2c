package co.yap.modules.dashboard.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.sendmoney.addbeneficiary.states.AddBeneficiaryStates
import co.yap.modules.dashboard.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class AddBeneficiaryViewModel(application: Application) :
    SendMoneyBaseViewModel<IAddBeneficiary.State>(application), IAddBeneficiary.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: AddBeneficiaryStates = AddBeneficiaryStates(getApplication())

    override var clickEvent: SingleClickEvent = SingleClickEvent()



    override fun handlePressOnAddNow() {

    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_send_money_display_text_title))
        toggleAddButtonVisibility(false)
    }
}