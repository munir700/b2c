package co.yap.app.modules.forgotpasscode.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IForgotPasscodeOtp {
    interface View:IBase.View<ViewModel>
    interface ViewModel:IBase.ViewModel<State>{
        val nextButtonPressEvent: SingleClickEvent
        fun handlePressOnSendButton(id: Int)
        fun handlePressOnResendOTP(id:Int)
        fun setPasscode(passcode: String)

    }
    interface State : IBase.State {
        //views
        var verificationTitle: String
        var verificationDescription: String

        //properties
        var otp: String
        var valid: Boolean
        var timer: String
        var validResend: Boolean
        fun reverseTimer(Seconds: Int)
        var color: Int
    }
}