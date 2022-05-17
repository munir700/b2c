package co.yap.modules.dashboard.store.household.contact

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHAddUserContactState @Inject constructor() : BaseState(), IHHAddUserContact.State {
    override var phone: MutableLiveData<String> = MutableLiveData()
    override var confirmPhone: MutableLiveData<String> = MutableLiveData()
    override var countryCode: MutableLiveData<String> = MutableLiveData("971")
    override var request: MutableLiveData<HouseholdOnboardRequest>? = MutableLiveData()
    override var isMobileVerified: MutableLiveData<Boolean>? = MutableLiveData(true)
}
