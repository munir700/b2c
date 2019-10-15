package co.yap.modules.dashboard.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.dashboard.sendmoney.addbeneficiary.states.SelectCountryState
import co.yap.modules.dashboard.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class SelectCountryViewModel(application: Application) :
    SendMoneyBaseViewModel<ISelectCountry.State>(application), ISelectCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: SelectCountryState  = SelectCountryState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnAddNow() {

    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
    }
}