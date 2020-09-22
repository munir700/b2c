package co.yap.household.onboarding.passcode

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHOnBoardingPassCodeState : BaseState(), IHHOnBoardingPassCode.State {
    override var passCode: MutableLiveData<String> = MutableLiveData()
}