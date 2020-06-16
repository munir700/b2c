package co.yap.modules.dashboard.store.household.contact

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHAddUserContactState : BaseState(), IHHAddUserContact.State {
    override var phone: MutableLiveData<String> = MutableLiveData()
    override var confirmPhone: MutableLiveData<String> = MutableLiveData()
}
