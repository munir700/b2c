package co.yap.household.onboarding.onboardemail

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHOnBoardingEmailState : BaseState(), IHHOnBoardingEmail.State {
    override var email: MutableLiveData<String> = MutableLiveData()
}
