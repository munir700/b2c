package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.yapcore.BaseState

class CreatePasscodeState : BaseState(), ICreatePasscode.State {

    override var valid: Boolean = false
        @Bindable get() = validate(passcode)

    @get:Bindable
    override var passcode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.passcode)
            notifyPropertyChanged(BR.valid)

        }
    @get:Bindable
    override var dialerError: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dialerError)
        }


    fun validate(text: String): Boolean {
        if (text.length == 15) {
            dialerError = "Lenght is not 15"
            return true
        }
        return false


    }
}