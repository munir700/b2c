package co.yap.modules.forgotpasscode.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IForgotPasscodeOtp {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun loadData()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleClickEvent
        fun handlePressOnSendButton(id: Int)
        fun handlePressOnResendOTP(id: Int)
        fun setPasscode(passcode: String)
       // var mobileNumber: String
        var destination: String
        var emailOtp: Boolean
    }

    interface State : IBase.State {
        //views
        var verificationTitle: String
        var verificationDescription: String
        var mobileNumber: Array<String?>
        //properties
        var otp: String
        var valid: Boolean
        var timer: String
        var validResend: Boolean
        fun reverseTimer(Seconds: Int)
        var color: Int
    }
}