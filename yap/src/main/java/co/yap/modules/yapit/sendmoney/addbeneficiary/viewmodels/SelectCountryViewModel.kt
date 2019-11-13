package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.CountriesResponseDTO
import co.yap.countryutils.country.Country
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.SelectCountryState
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.CountryModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class SelectCountryViewModel(application: Application) :
    SendMoneyBaseViewModel<ISelectCountry.State>(application), ISelectCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var populateSpinnerData: MutableLiveData<List<Country>> = MutableLiveData()

    override var countries: List<Country>? = null

    override val repository: CustomersRepository = CustomersRepository

    override val state: SelectCountryState = SelectCountryState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnSeclectCountry(id: Int) {
        clickEvent.setValue(id)
    }

    var countriesResponseDTO: CountriesResponseDTO? = null

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

                    countries = countryModel.data as List<Country>
                    populateSpinnerData.setValue(countries)

                    //trigger UI list for spinner

//                    repository.value = response.data.data
//                    here ll get some list of the countries
//                    now that list of countries need to be populated in spinner listing
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }
}