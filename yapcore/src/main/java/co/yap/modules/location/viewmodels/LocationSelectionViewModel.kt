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
import com.google.android.gms.maps.model.LatLng

class LocationSelectionViewModel(application: Application) :
    BaseViewModel<ILocationSelection.State>(application),
    ILocationSelection.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: LocationSelectionState = LocationSelectionState(application)

    override var address: Address? = null
    override var lastKnowLocation: MutableLiveData<LatLng> = MutableLiveData()

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        state.toolbarVisibility = true
    }

    override fun onLocationSelected() {
        state.addressTitle = state.placeSubTitle.get() ?: ""
        state.addressSubtitle.set(state.placeTitle.get() ?: "")
        address?.address1 = state.addressTitle
        address?.address2 = state.addressSubtitle.get()
        state.headingTitle.set(state.addressTitle)
        state.subHeadingTitle.set(
            Translator.getString(
                getApplication(),
                R.string.screen_meeting_location_display_text_selected_subtitle
            )
        )
    }
}