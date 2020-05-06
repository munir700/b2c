package co.yap.modules.location.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.modules.location.states.LocationSelectionState
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.City
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType

class LocationSelectionViewModel(application: Application) :
    BaseViewModel<ILocationSelection.State>(application),
    ILocationSelection.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var isUnNamedLocation: Boolean = false
    override var hasSeletedLocation: Boolean = false
    override var unNamed: String = "Unnamed"
    override var defaultHeading: String = ""
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var isMapExpanded: MutableLiveData<Boolean> = MutableLiveData()
    override var termsCheckedTime: MutableLiveData<String> = MutableLiveData("")
    override var cities: MutableLiveData<ArrayList<City>> = MutableLiveData()
    override val state: LocationSelectionState = LocationSelectionState(application)
    override val repository: CustomersRepository = CustomersRepository
    override var address: Address? = null

    override fun onCreate() {
        super.onCreate()
        if (state.hasCityFeature.get() == true)
            getCities()
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getCities() {
        launch {
            state.loading = true
            when (val response = repository.getCities()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        cities.value = it
                    } ?: showMessage("No data found")
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.loading = false
                }
            }
        }
    }

    fun showMessage(msg: String) {
        state.toast = "$msg^${AlertType.DIALOG.name}"
    }

    override fun onResume() {
        super.onResume()
        state.toolbarVisibility = true
    }

    override fun onLocationSelected() {
        hasSeletedLocation = true

        if (state.placeTitle.get()?.toLowerCase()?.contains(unNamed.toLowerCase()) == true) {
            state.headingTitle.set(defaultHeading)

            isUnNamedLocation = true
            state.isUnNamed.set(true)
            state.subHeadingTitle.set(
                Translator.getString(
                    getApplication(),
                    R.string.screen_meeting_location_display_text_add_manual_address_subtitle
                )
            )
            state.placeSubTitle.set("")
            state.addressTitle.set("")
            if (state.placeSubTitle.get()?.toLowerCase()
                    ?.contains(unNamed.toLowerCase()) == true
            ) {
                state.addressSubtitle.set("")

            }
        } else {
            state.isUnNamed.set(false)
            state.addressTitle.set(state.placeSubTitle.get() ?: "")
            state.addressSubtitle.set(state.placeTitle.get() ?: "")
            address?.address1 = state.addressTitle.get()
            address?.address2 = state.addressSubtitle.get()
            state.headingTitle.set(state.addressSubtitle.get())
            state.subHeadingTitle.set(
                Translator.getString(
                    getApplication(),
                    R.string.screen_meeting_location_display_text_selected_subtitle
                )
            )
        }

    }
}