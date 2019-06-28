package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase

interface IMobile {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnNext()
        fun validateMobileNumber()
    }

    interface State : IBase.State {
        var mobile: String
        var mobileError: String
        var valid: Boolean
    }
}