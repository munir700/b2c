package co.yap.household.onboarding.onboardmobile

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHOnBoardingMobileState @Inject constructor() : BaseState(), IHHOnBoardingMobile.State {
    override var phone: MutableLiveData<String>? = MutableLiveData()
    override var countryCode: MutableLiveData<String>? = MutableLiveData("971")

}
