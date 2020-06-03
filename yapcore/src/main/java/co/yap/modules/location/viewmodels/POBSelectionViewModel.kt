package co.yap.modules.location.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.location.POBCountry
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.states.POBSelectionState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class POBSelectionViewModel(application: Application) :
    BaseViewModel<IPOBSelection.State>(application),
    IPOBSelection.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPOBSelection.State = POBSelectionState()
    override var populateSpinnerData: MutableLiveData<List<POBCountry>> = MutableLiveData()
    override var countries: ArrayList<POBCountry> = ArrayList()
    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override val repository: CustomersRepository = CustomersRepository

    override fun onCreate() {
        super.onCreate()
        getAllCountries()
    }

    private fun getAllCountries() {
        if (!countries.isNullOrEmpty()) {
            populateSpinnerData.setValue(countries)
        } else {
            launch {
                state.loading = true
                when (val response = repository.getAllCountries()) {
                    is RetroApiResponse.Success -> {
                        val sortedList = response.data.data?.sortedWith(compareBy { it.name })
                        sortedList?.let { it ->
                            countries.clear()
                            countries.add(
                                0,
                                POBCountry(name = getString(Strings.screen_add_beneficiary_display_text_select_country))
                            )
                            populateSpinnerData.value = countries
                            countries.addAll(it.map {
                                POBCountry(
                                    name = it.name,
                                    isoCountryCode2Digit = it.isoCountryCode2Digit,
                                    isoCountryCode3Digit = it.isoCountryCode3Digit
                                )
                            })
                        }
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


}