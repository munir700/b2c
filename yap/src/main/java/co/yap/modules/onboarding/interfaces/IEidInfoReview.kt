package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface IEidInfoReview {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State {
        var fullName: String
        var nationality: String
        var dateOfBirth: String
        var gender: String
        var expiryDate: String
    }
}