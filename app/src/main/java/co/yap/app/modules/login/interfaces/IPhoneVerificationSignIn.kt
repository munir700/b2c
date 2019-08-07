package co.yap.app.modules.login.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerificationSignIn {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        val verifyOtpResult: SingleLiveEvent<Boolean>
        val postDemographicDataResult: SingleLiveEvent<Boolean>
        fun postDemographicData()
        fun handlePressOnResend()
        fun handlePressOnSendButton()
        fun verifyOtp()
    }

    interface State : IBase.State {
        var otp: String
        var passcode: String
        var username: String
        var timer: String
        var valid: Boolean
        var validateBtn: Boolean
        fun reverseTimer(Seconds: Int)
        var color: Int
    }
}