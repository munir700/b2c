package co.yap.modules.dashboard.store.household.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldSuccess
import co.yap.yapcore.BaseState

class HouseHoldSuccessState : BaseState(), IHouseHoldSuccess.State {

    @get:Bindable
    override var houseHoldUserName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.houseHoldUserName)
        }
}