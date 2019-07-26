package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerification {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnSendButton()
        fun handlePressOnResendOTP()
        fun setPasscode(passcode: String)
    }

    interface State : IBase.State {
        //views
        var verificationTitle:String
        var verificationDescription:String

        //properties
        var otp: String
        var valid: Boolean
        var timer: String
        var validResend: Boolean
        fun reverseTimer(Seconds: Int)
        var color:Int
    }
}