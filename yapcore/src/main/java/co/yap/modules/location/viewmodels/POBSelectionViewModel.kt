package co.yap.modules.location.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.states.POBSelectionState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

class POBSelectionViewModel(application: Application) :
    BaseViewModel<IPOBSelection.State>(application),
    IPOBSelection.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPOBSelection.State = POBSelectionState()
    override var populateSpinnerData: MutableLiveData<List<Country>> = MutableLiveData()
    override var countries: ArrayList<Country> = ArrayList()

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
                        populateSpinnerData.value = Utils.parseCountryList(response.data.data)
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