package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.states.PhoneVerificationSignInState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class PhoneVerificationSignInViewModel(application: Application) :
    BaseViewModel<IPhoneVerificationSignIn.State>(application), IPhoneVerificationSignIn.ViewModel {

    override val state: PhoneVerificationSignInState = PhoneVerificationSignInState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnSendButton() {
        nextButtonPressEvent.value = true
    }

    override fun handlePressOnResendOTP() {

    }
}