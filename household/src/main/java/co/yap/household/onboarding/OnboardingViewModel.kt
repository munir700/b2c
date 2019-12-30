package co.yap.household.onboarding

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class OnboardingViewModel(application: Application) : BaseViewModel<IOnboarding.State>(application),
    IOnboarding.ViewModel {

    override val state: IOnboarding.State = OnBoardingState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnBackButton() {

    }

}