package co.yap.household.onboarding

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class OnboardingViewModelHouseHold(application: Application) :
    BaseViewModel<IOnboarding.State>(application),
    IOnboarding.ViewModel {

    override val state: OnBoardingState= OnBoardingState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnBackButton() {

    }

}