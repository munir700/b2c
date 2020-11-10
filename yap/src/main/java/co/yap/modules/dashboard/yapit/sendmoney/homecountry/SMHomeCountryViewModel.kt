package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.app.Application
import co.yap.R
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.SessionManager

class SMHomeCountryViewModel(application: Application) :
    BaseViewModel<ISMHomeCountry.State>(application), ISMHomeCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository = CustomersRepository

    override var recentsAdapter: CoreRecentTransferAdapter = CoreRecentTransferAdapter(
        context,
        mutableListOf()
    )

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val state: SMHomeCountryState = SMHomeCountryState()

    override fun onCreate() {
        super.onCreate()
        getHomeCountryRecentBeneficiaries()
        state.toolbarTitle = getString(R.string.screen_send_money_home_title)
        state.rightButtonText.set(getString(R.string.screen_send_money_home_display_text_compare))
        state.name?.set("Canada")
        state.countryCode?.set("")
        state.rate?.set("0.357014")
        state.symbol?.set("CAD")
        state.time?.set("04/10/2020, 2:30 PM")
    }

    private fun getHomeCountryRecentBeneficiaries() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getRecentBeneficiaries()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        recentsAdapter.setList(response.data.data.filter { it.country == SessionManager.user?.currentCustomer?.homeCountry })
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        state.viewState.value = response.error.message
                    }
                }
            }
        }
    }
}