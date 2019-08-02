package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface IEidInfoReview {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

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