package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEidInfoReview {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnRescanBtn()
        fun handlePressOnConfirmBtn()
    }

    interface State : IBase.State {
        var titleName: Array<String?>
        var fullName: String
        var nationality: String
        var dateOfBirth: String
        var gender: String
        var expiryDate: String

        var fullNameValid: Boolean
        var nationalityValid: Boolean
        var dateOfBirthValid: Boolean
        var genderValid: Boolean
        var expiryDateValid: Boolean
    }
}