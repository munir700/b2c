package co.yap.modules.location.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.modules.location.interfaces.ILocation
import co.yap.modules.location.states.LocationState
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType

class LocationViewModel(application: Application) :
    BaseViewModel<ILocation.State>(application),
    ILocation.ViewModel {
    override var defaultHeading: String = ""
    override var heading: String = ""
    override var subHeading: String = ""
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedCountry: Country? = null
    override val state: LocationState = LocationState()
    override var address: Address? = null
    override var isOnBoarding: Boolean = false
    override var countries: ArrayList<Country> = ArrayList()
    override var cities: ArrayList<String>? = ArrayList()
    override var amendmentMap: HashMap<String?, List<String>?>? = null
    override var hideProgressToolbar: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    fun showMessage(msg: String) {
        state.toast = "$msg^${AlertType.DIALOG.name}"
    }
}