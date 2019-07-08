package co.yap.modules.onboarding.interfaces

import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import co.yap.yapcore.IBase

interface ICreatePasscode {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnCreatePasscodeButton()
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