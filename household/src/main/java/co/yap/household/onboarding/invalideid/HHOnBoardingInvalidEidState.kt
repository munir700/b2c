package co.yap.household.onboarding.invalideid

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHOnBoardingInvalidEidState @Inject constructor():BaseState(),IHHOnBoardingInvalidEid.State {
    override var contactPhone: MutableLiveData<String> =MutableLiveData()
}
