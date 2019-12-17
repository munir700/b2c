package co.yap.modules.dashboard.store.household.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserInfo
import co.yap.yapcore.BaseState

class HouseHoldUserInfoStates : BaseState(), IHouseHoldUserInfo.State {

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }


    @get:Bindable
    override var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)

        }

    @get:Bindable
    override var emailAddress: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailAddress)

        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)

        }
}