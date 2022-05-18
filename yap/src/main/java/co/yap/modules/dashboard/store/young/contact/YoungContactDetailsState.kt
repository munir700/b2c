package co.yap.modules.dashboard.store.young.contact

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class YoungContactDetailsState @Inject constructor(): BaseState(), IYoungContactDetails.State {
    override var email: MutableLiveData<String> = MutableLiveData()
    override var contactName: MutableLiveData<String> = MutableLiveData("Lina")
    override var phone: MutableLiveData<String> = MutableLiveData()
    override var countryCode: MutableLiveData<String> = MutableLiveData("971")

}