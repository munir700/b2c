package co.yap.app.modules.login.interfaces

import android.text.TextWatcher
import co.yap.yapcore.IBase

interface IVerifyPasscode {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnSignInButton()
    }

    interface State : IBase.State {
        var dialerError: String
        var passcode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        fun validationPasscode(passcodeText: String)
        var sequence: Boolean
        var similar: Boolean
    }
}