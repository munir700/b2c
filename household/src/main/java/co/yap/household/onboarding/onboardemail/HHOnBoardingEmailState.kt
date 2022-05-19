package co.yap.household.onboarding.onboardemail

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHOnBoardingEmailState @Inject constructor() : BaseState(), IHHOnBoardingEmail.State {
    override var email: MutableLiveData<String> = MutableLiveData()
}
