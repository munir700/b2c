package co.yap.household.onboarding.onboarding.states

import androidx.databinding.Bindable
import co.yap.household.BR
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.yapcore.BaseState

class HouseHoldNumberRegistrationState : BaseState(), IHouseHoldNumberRegistration.State {

    @get:Bindable
    override var welcomeHeading: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.welcomeHeading)
        }
    @get:Bindable
    override var numberConfirmationValue: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.numberConfirmationValue)
        }
    @get:Bindable
    override var parentName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.parentName)
        }
    @get:Bindable
    override var phoneNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
        }
    @get:Bindable
    override var buttonTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }
    @get:Bindable
    override var buttonValidation: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonValidation)
        }
    @get:Bindable
    override var showErrorMessage: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showErrorMessage)
        }

}