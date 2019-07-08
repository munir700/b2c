package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IName {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnNext()
    }

    interface State : IBase.State {
        var dummyStrings: Array<String>
        var firstName: String
        var firstNameError: String
        var lastName: String
        var lastNameError: String
        var valid: Boolean
    }
}