package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.states.EmailState
import co.yap.yapcore.SingleLiveEvent

class EmailViewModel(application: Application) : OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel {

    override val state: EmailState = EmailState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun handlePressOnNext() {
        nextButtonPressEvent.postValue(true)
    }
}