package co.yap.household.onboarding.onboardmobile

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHOnBoardingMobileState : BaseState(), IHHOnBoardingMobile.State {
    override var phone: MutableLiveData<String>? = MutableLiveData()
    override var countryCode: MutableLiveData<String>? = MutableLiveData("971")

}
