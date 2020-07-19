package co.yap.household.onboard.onboarding.existinghousehold

import android.app.Application
import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.yapcore.BaseState
@Deprecated("")
class ExistingHouseHoldState(val application: Application) : BaseState(), IExistingHouseHold.State {

    @get:Bindable
    override var firstName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    override var lastName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }

}
