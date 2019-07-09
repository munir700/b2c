package co.yap.modules.onboarding.interfaces

import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerificationSignIn {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnSendButton()
        fun handlePressOnResendOTP()
        fun onEditorActionListener(): TextView.OnEditorActionListener
    }

    interface State : IBase.State {
        var otp: String
        var valid: Boolean
    }
}