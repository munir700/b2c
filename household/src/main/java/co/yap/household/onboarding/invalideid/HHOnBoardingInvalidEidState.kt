package co.yap.household.onboarding.invalideid

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHOnBoardingInvalidEidState:BaseState(),IHHOnBoardingInvalidEid.State {
    override var contactPhone: MutableLiveData<String> =MutableLiveData()
}
