package co.yap.modules.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.yapcore.BaseState

class PhoneVerificationState : BaseState(), IPhoneVerification.State {
    override var validOtp: Boolean=false
        set(value) {
            field=value
            //notifyPropertyChanged(BR.validOtp)
        }

    val mobileNumber: Array<String?> = arrayOfNulls(1)

    @get:Bindable
    override var otp: String = ""
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.otp)
            validate()
        }


    @get:Bindable
    override var valid: Boolean = false
        get() = validate()
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    private fun validate(): Boolean {
        var vlidateOtp: Boolean = false
         if (!otp.isNullOrEmpty() && otp.length == 4) {
            vlidateOtp = true
            valid=true
        }
        return vlidateOtp
    }
}