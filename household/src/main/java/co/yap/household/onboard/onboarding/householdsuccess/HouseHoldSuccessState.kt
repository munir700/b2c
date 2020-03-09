package co.yap.household.onboard.onboarding.householdsuccess

import android.app.Application
import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.yapcore.BaseState

class HouseHoldSuccessState(val application: Application) : BaseState(), IHouseHoldSuccess.State {

    @get:Bindable
    override var name: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
}
