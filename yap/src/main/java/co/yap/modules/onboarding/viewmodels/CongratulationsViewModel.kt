package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.states.CongratulationsState
import co.yap.yapcore.SingleClickEvent
import java.util.*
import java.util.concurrent.TimeUnit

class CongratulationsViewModel(application: Application) :
    OnboardingChildViewModel<ICongratulations.State>(application),
    ICongratulations.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: CongratulationsState = CongratulationsState()
    override var elapsedOnboardingTime: Long = 0

    override fun onCreate() {
        super.onCreate()
        // calculate elapsed time for onboarding
        elapsedOnboardingTime = parentViewModel?.onboardingData?.elapsedOnboardingTime ?: 0
        state.nameList[0] = parentViewModel!!.onboardingData.firstName
        state.ibanNumber = maskIbanNumber(parentViewModel!!.onboardingData.ibanNumber.trim())
    }

    override fun onResume() {
        super.onResume()
        setProgress(100)

    }

    override fun handlePressOnCompleteVerification(id: Int) {
        clickEvent.setValue(id)
    }

    private fun maskIbanNumber(unmaskedIban: String): String {
        return if (unmaskedIban.length >= 8) {
            val firstPartIban: String = unmaskedIban.substring(0, 2)
            val secondPartIban: String = unmaskedIban.substring(2, 4)
            val thirdPartIban: String = unmaskedIban.substring(4, 7)
            val fourthPartIban: String = unmaskedIban.substring(7, unmaskedIban.length - 6)
            "$firstPartIban $secondPartIban $thirdPartIban $fourthPartIban******"
        } else {
            unmaskedIban
        }
    }


}