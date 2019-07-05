package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.states.MobileState

class MobileViewModel(application: Application) : OnboardingChildViewModel<IMobile.State>(application),
    IMobile.ViewModel {

    override val state: MobileState = MobileState(application)

    override fun onResume() {
        super.onResume()
        setProgress(20)
    }

    override fun handlePressOnNext() {
        setProgress(30)
        state.valid = false

    }
}