package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.SelectCountryState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.CountryModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class SelectCountryViewModel(application: Application) :
    SendMoneyBaseViewModel<ISelectCountry.State>(application), ISelectCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var populateSpinnerData: MutableLiveData<List<Country>> = MutableLiveData()

    override var countries: ArrayList<Country>? = ArrayList()

    override val repository: CustomersRepository = CustomersRepository

    override val state: SelectCountryState = SelectCountryState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onTransparentViewClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnSeclectCountry(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
        getAllCountries()
    }


    fun getAllCountries() {

        launch {
            state.loading = true
            when (val response = repository.getAllCountries()) {
                is RetroApiResponse.Success -> {
                    var countryModel: CountryModel = response.data

                    for (data in countryModel.data) {
                        var country: Country = Country()

                        country!!.id = data.id
                        country!!.setName(data.name)
                        country!!.isoCountryCode2Digit = data.isoCountryCode2Digit
                        country!!.isoCountryCode3Digit = data.isoCountryCode3Digit

                        countries!!.add(country)
                    }


                    populateSpinnerData.setValue(countries)

                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }


    override fun onCountrySelected(pos: Int) {
        if (pos == 0) {
            state.selectedCountry = null
        } else {
            val country: Country = countries!!.get(pos)
            state.selectedCountry = country
        }
    }
}