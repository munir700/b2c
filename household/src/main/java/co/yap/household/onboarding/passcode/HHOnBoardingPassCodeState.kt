package co.yap.household.onboarding.passcode

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHOnBoardingPassCodeState @Inject constructor(): BaseState(), IHHOnBoardingPassCode.State {
    override var passCode: MutableLiveData<String> = MutableLiveData()
}