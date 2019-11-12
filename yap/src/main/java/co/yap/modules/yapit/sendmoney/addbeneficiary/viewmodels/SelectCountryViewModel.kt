package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import android.util.Log
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.SelectCountryState
import co.yap.networking.customers.responsedtos.country.utils.CurrencyUtils
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.country.CountriesResponseDTO
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import java.security.AccessController.getContext

class SelectCountryViewModel(application: Application) :
    SendMoneyBaseViewModel<ISelectCountry.State>(application), ISelectCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: SelectCountryState = SelectCountryState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnSeclectCountry(id: Int) {
        clickEvent.setValue(id)
    }

    var countriesResponseDTO: CountriesResponseDTO ?= null

//    "id" : 28,
//    "isoCountryCode2Digit" : "PK",
//    "isoCountryCode3Digit" : "PKR",
//    "name" : "PAKISTAN",
//    "isoNum" : "100",
//    "active" : false,
//    "signUpAllowed" : false


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_add_beneficiary_display_text_title))
        toggleAddButtonVisibility(false)
        getAllCountries()
    }


    fun getAllCountries() {

        launch {
            when (val response = repository.getAllCountries()) {
                is RetroApiResponse.Success -> {
                      countriesResponseDTO = response.data
                    Log.i("countriesResponseDTO", countriesResponseDTO.data.size.toString())
//                    repository.value = response.data.data
//                    here ll get some list of the countries
//                    now that list of countries need to be populated in spinner listing

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }

        }
    }


    fun loadCountries() {
        getRepository().getAllCountries(object : MyCallBack<CountriesResponseDTO>() {
            fun onSuccess(response: CountriesResponseDTO) {
                if (response.getCountries().size() > 0) {
                    getCountries().addAll(response.getCountries())
                    // update flags of all currencies in all countries
                    for (country in getCountries()) {
                        for (currency in country.getSupportedCurrencies()) {
                            val local = CurrencyUtils.getCurrencyByCode(currency.getCode())
                            if (local != null) {
                                currency.setFlag(local!!.getFlag())
                                currency.setSymbol(local!!.getSymbol())
                            }
                        }
                    }

                    // First item should be 'select country'
                    val country = Country()
                    country.setName(getString(R.string.txt_select_country))
                    getCountries().add(0, country)

                    // notify state about countries loaded
                    getState().getCountriesLoadObservable().setValue(true)
                } else {
                    loadCountriesLocally()
                }
            }

            fun onFailure(error: CountriesResponseDTO) {
                loadCountriesLocally()
            }
        })
    }


    private fun loadCountriesLocally() {
        countriesResponseDTO!!.data=CurrencyUtils.getCountriesList(getApplication())
        // notify state about countries loaded
        getState().getCountriesLoadObservable().setValue(true)
    }


}