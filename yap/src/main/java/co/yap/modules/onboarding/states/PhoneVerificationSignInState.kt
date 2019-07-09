package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.yapcore.BaseState

class PhoneVerificationSignInState : BaseState(), IPhoneVerificationSignIn.State {

    @get:Bindable
    override var otp: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.otp)
        }

    @get:Bindable
    override var passcode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.passcode)
        }


    @get:Bindable
    override var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }
}