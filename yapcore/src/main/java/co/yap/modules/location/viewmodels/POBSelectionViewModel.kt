package co.yap.modules.location.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.IPOBSelection
import co.yap.modules.location.states.POBSelectionState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.BirthInfoRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.SessionManager

class POBSelectionViewModel(application: Application) :
    LocationChildViewModel<IPOBSelection.State>(application),
    IPOBSelection.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPOBSelection.State = POBSelectionState()
    override var populateSpinnerData: MutableLiveData<ArrayList<Country>> = MutableLiveData()

    override val repository: CustomersRepository = CustomersRepository

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getAllCountries()
    }

    override fun getAllCountries() {
        if (!parentViewModel?.countries.isNullOrEmpty()) {
            populateSpinnerData.setValue(parentViewModel?.countries)
        } else {
            launch(Dispatcher.Background) {
                state.viewState.postValue(true)
                val response = repository.getAllCountries()
                launch {
                    when (response) {
                        is RetroApiResponse.Success -> {
                            populateSpinnerData.value =
                                Utils.parseCountryList(response.data.data, addOIndex = false)
                            parentViewModel?.countries =
                                populateSpinnerData.value as ArrayList<Country>
                            state.viewState.value = false
                        }

                        is RetroApiResponse.Error -> {
                            state.viewState.value = false
                            state.toast = response.error.message
                        }
                    }
                }
            }
        }
    }

    override fun saveDOBInfo(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response = repository.saveBirthInfo(
                BirthInfoRequest(
                    countryOfBirth = state.selectedCountry.get()?.getName() ?: "",
                    cityOfBirth = state.cityOfBirth
                )
            )) {
                is RetroApiResponse.Success -> {
                    success.invoke()
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