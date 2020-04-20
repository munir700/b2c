package co.yap.modules.location.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.modules.location.states.LocationSelectionState
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent

class LocationSelectionViewModel(application: Application) :
    BaseViewModel<ILocationSelection.State>(application),
    ILocationSelection.ViewModel {
    override var isUnNamedLocation: Boolean = false
    override var hasSeletedLocation: Boolean = false
    override var unNamed: String = "Unnamed"
    override var defaultHeading: String = ""
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var isMapExpanded: MutableLiveData<Boolean> = MutableLiveData()
    override val state: LocationSelectionState = LocationSelectionState(application)

    override var address: Address? = null
    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
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
            state.subHeadingTitle.set(
                Translator.getString(
                    getApplication(),
                    R.string.screen_meeting_location_display_text_add_manual_address_subtitle
                )
            )
            state.addressTitle = ""
            if (state.placeSubTitle.get()?.toLowerCase()
                    ?.contains(unNamed.toLowerCase()) == true
            ) {
                state.addressSubtitle.set("")
            }
        } else {
            state.addressTitle = state.placeSubTitle.get() ?: ""
            state.addressSubtitle.set(state.placeTitle.get() ?: "")
            address?.address1 = state.addressTitle
            address?.address2 = state.addressSubtitle.get()
            state.headingTitle.set(address?.address1)
            state.subHeadingTitle.set(
                Translator.getString(
                    getApplication(),
                    R.string.screen_meeting_location_display_text_selected_subtitle
                )
            )
        }
    }
}