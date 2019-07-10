package co.yap.modules.onboarding.interfaces

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
        fun reverseTimer(Seconds: Int)
        var color:Int
    }
}