package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class PhoneVerificationViewModel(application: Application) : OnboardingChildViewModel<IPhoneVerification.State>(application), IPhoneVerification.ViewModel {

    override val state: PhoneVerificationState = PhoneVerificationState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setProgress(40)
    }

    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun handlePressOnResendOTP() {

    }
}