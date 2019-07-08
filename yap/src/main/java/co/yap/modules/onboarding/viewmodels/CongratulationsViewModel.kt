package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.states.CongratulationsState

class CongratulationsViewModel(application: Application) :
    OnboardingChildViewModel<ICongratulations.State>(application),
    ICongratulations.ViewModel {

    override val state: CongratulationsState = CongratulationsState()


    override fun handlePressOnCompleteVerification() {

    }

    override fun onCreate() {
        super.onCreate()
        state.nameList[0] = parentViewModel!!.onboardingData.firstName
        state.ibanNumber = parentViewModel!!.onboardingData.ibanNumber
    }
}