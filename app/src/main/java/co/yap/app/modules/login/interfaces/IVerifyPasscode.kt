package co.yap.app.modules.login.interfaces

import android.text.TextWatcher
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IVerifyPasscode {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnSignInButton()
        fun login()
        val signInButtonPressEvent: SingleLiveEvent<Boolean>
        val loginSuccess: SingleLiveEvent<Boolean>
    }

    interface State : IBase.State {
        var username: String
        var dialerError: String
        var passcode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        fun validationPasscode(passcodeText: String)
        var sequence: Boolean
        var similar: Boolean
    }
}