package co.yap.modules.onboarding.interfaces

import android.content.Context
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerification {

    interface View : IBase.View<ViewModel>{
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        // val nextButtonPressEvent: SingleLiveEvent<Boolean>
        val nextButtonPressEvent: SingleClickEvent

        //  fun handlePressOnSend()
        fun handlePressOnSendButton(id: Int)

        fun handlePressOnResendOTP(context: Context)
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
        fun reverseTimer(Seconds: Int, context: Context)
        var color: Int
    }
}