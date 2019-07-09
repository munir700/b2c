package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.states.CongratulationsState
import java.util.*
import java.util.concurrent.TimeUnit

class CongratulationsViewModel(application: Application) :
    OnboardingChildViewModel<ICongratulations.State>(application),
    ICongratulations.ViewModel {

    override val state: CongratulationsState = CongratulationsState()
    override var elapsedOnboardingTime: Long = 0
    override fun onCreate() {
        super.onCreate()
        // calculate elapsed time for onboarding
        elapsedOnboardingTime =
            TimeUnit.MILLISECONDS.toSeconds(Date().time - (parentViewModel?.onboardingData?.startTime?.time ?: Date().time))

        state.nameList[0] = parentViewModel!!.onboardingData.firstName
        state.ibanNumber = parentViewModel!!.onboardingData.ibanNumber
    }

    override fun onResume() {
        super.onResume()
        setProgress(100)

    }

    override fun handlePressOnCompleteVerification() {

    }


}