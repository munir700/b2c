package co.yap.household.onboarding.onboarding.states

import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.household.onboarding.onboarding.interfaces.INewUserSuccess
import co.yap.yapcore.BaseState

class NewUserCongratulationsState : BaseState(), INewUserSuccess.State {

    override val nameList: Array<String?> = arrayOfNulls(1)

    @get:Bindable
    override var ibanNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.ibanNumber)
        }


    @get:Bindable
    override var onboardingTime: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.onboardingTime)
        }
    @get:Bindable
    override var heading: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.heading)
        }

}