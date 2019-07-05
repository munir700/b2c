package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IPhoneVerification {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnSendButton()
        fun handlePressOnResendOTP()
    }
    interface State : IBase.State
}