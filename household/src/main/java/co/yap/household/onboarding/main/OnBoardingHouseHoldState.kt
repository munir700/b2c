package co.yap.household.onboarding.main

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class OnBoardingHouseHoldState : BaseState(), IOnBoardingHouseHold.State {
    override var totalProgress: MutableLiveData<Int> = MutableLiveData(100)
    override var currentProgress: MutableLiveData<Int> = MutableLiveData(0)
}