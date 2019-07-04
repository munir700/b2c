package co.yap.modules.onboarding.interfaces

import android.text.Spannable
import android.text.SpannableString
import co.yap.yapcore.IBase

interface ICreatePasscode {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State{
        var dialerError:String
        var passcode:String
        var valid: Boolean
    }
}