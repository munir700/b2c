package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerificationSignIn {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        val verifyOtpResult: SingleLiveEvent<Boolean>
        fun handlePressOnSendButton()
        fun handlePressOnResendOTP()
        fun verifyOtp()
    }

    interface State : IBase.State{
        var otp: String
        var passcode: String
        var username: String
    }
}