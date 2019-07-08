package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.yapcore.BaseState

class PhoneVerificationState : BaseState(), IPhoneVerification.State {

    @get:Bindable
    override var otp: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.otp)
        }
}