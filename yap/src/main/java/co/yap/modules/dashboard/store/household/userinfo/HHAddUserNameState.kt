package co.yap.modules.dashboard.store.household.userinfo

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHAddUserNameState @Inject constructor() : BaseState(), IHHAddUserName.State {
    override var firstName: MutableLiveData<String> = MutableLiveData()
    override var lastName: MutableLiveData<String> = MutableLiveData()
}