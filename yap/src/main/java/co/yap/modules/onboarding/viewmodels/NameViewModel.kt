package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.util.Log
import co.yap.modules.onboarding.interfaces.IName
import co.yap.modules.onboarding.states.NameState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class NameViewModel(application: Application) : OnboardingChildViewModel<IName.State>(application), IName.ViewModel {

    override val state: NameState = NameState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setProgress(60)
    }

    override fun handlePressOnNext() {
        nextButtonPressEvent.postValue(true)
    }
}