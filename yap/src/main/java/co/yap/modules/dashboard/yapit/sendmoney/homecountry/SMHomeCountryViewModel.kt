package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.FxRateRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
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
    override val state: SMHomeCountryState = SMHomeCountryState()
    override var homeCountry: Country? = null
    override var benefitsList: ArrayList<String> = ArrayList()
    override var recentsAdapter: CoreRecentTransferAdapter = CoreRecentTransferAdapter(
        context,
        mutableListOf()
    )
    override var fxRateResponse: MutableLiveData<FxRateResponse.Data> = MutableLiveData()

    override var benefitsAdapter: SMHomeCountryBenefitsAdapter =
        SMHomeCountryBenefitsAdapter(benefitsList)

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(R.string.screen_send_money_home_title)
        state.rightButtonText.set(getString(R.string.screen_send_money_home_display_text_compare))
        homeCountry = SessionManager.getCountries()
            .find { it.isoCountryCode2Digit == SessionManager.user?.currentCustomer?.homeCountry ?: "" }
        homeCountry?.let { populateData(it) }
        benefitsList.add(getString(R.string.screen_send_money_home_display_text_send_money_home))
        benefitsList.add(getString(R.string.screen_send_money_home_display_text_get_best_rates))
    }

   override fun populateData(hc: Country) {
       state.name?.set(hc.getName())
       state.countryCode?.set(hc.isoCountryCode2Digit)
       state.rate?.set("0.357014")
       state.symbol?.set(hc.getCurrency()?.code)
       state.time?.set("04/10/2020, 2:30 PM")
   }

    override fun getHomeCountryRecentBeneficiaries() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getRecentBeneficiaries()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        response.data.data.forEach {
                            it.name = it.fullName()
                            it.profilePictureUrl = it.beneficiaryPictureUrl
                            it.type = it.beneficiaryType
                            it.isoCountryCode = it.country
                        }
                        val homeCountryList:List<Beneficiary> =response.data.data.filter { it.country == homeCountry?.isoCountryCode2Digit }
                        recentsAdapter.setList(homeCountryList)
                        state.isNoRecentsBeneficiries.set(
                            recentsAdapter.getDataList().isNullOrEmpty()
                        )
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

    override fun updateHomeCountry(success: () -> Unit) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response =
                repository.updateHomeCountry(homeCountry = homeCountry?.isoCountryCode2Digit ?: "")
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        success.invoke()
                    }

                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        state.viewState.value = response.error.message
                    }
                }
            }

        }
    }

    override fun getFxRates() {
        launch(Dispatcher.Background){
            state.viewState.postValue(true)
            val response = repository.updateFxRate(FxRateRequest(other_bank_country = homeCountry?.isoCountryCode2Digit.toString()))
            launch {
                when(response){
                    is RetroApiResponse.Success ->{
                        fxRateResponse.value = response.data.data
                        state.loading = false
                    }
                    is RetroApiResponse.Error ->{
                        state.viewState.value = false
                        state.viewState.value = response.error.message
                    }
                }

            }
        }
    }

}