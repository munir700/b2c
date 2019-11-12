package co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import android.util.Log
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ISelectCountry
import co.yap.modules.yapit.sendmoney.addbeneficiary.states.SelectCountryState
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.country.CountriesResponseDTO
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class SelectCountryViewModel(application: Application) :
    SendMoneyBaseViewModel<ISelectCountry.State>(application), ISelectCountry.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: SelectCountryState = SelectCountryState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnSeclectCountry(id: Int) {
        clickEvent.setValue(id)
    }

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
                    var countriesResponseDTO: CountriesResponseDTO = response.data
                    Log.i("countriesResponseDTO", countriesResponseDTO.data.size.toString())
//                    repository.value = response.data.data
//                    here ll get some list of the countries
//                    now that list of countries need to be populated in spinner listing

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }

        }
    }


}