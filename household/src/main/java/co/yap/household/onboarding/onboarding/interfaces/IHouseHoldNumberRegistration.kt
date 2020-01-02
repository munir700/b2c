package co.yap.household.onboarding.onboarding.interfaces

import co.yap.yapcore.IBase

interface IHouseHoldNumberRegistration {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        fun populateState()
    }
    interface State : IBase.State {
        var welcomeHeading: String
        var numberConfirmationValue: String
        var parentName: String
        var numberString:String
        var buttonTitle: String
        var buttonValidation: Boolean
    }
}