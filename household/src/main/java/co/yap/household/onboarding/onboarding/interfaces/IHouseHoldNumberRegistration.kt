package co.yap.household.onboarding.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldNumberRegistration {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun populateState()
        var clickEvent: SingleClickEvent?
        fun handlePressOnConfirm(id: Int)
    }

    interface State : IBase.State {
        var welcomeHeading: String
        var numberConfirmationValue: String
        var parentName: String
        var phoneNumber: String?
        var buttonTitle: String
        var buttonValidation: Boolean
        var showErrorMessage: Boolean
    }
}