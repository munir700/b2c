package co.yap.modules.dashboard.store.young.confirmrelationship

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class YoungConfirmRelationshipState @Inject constructor() : BaseState(), IYoungConfirmRelationship.State {
    override var realtion: MutableLiveData<String> = MutableLiveData("Parent")
    override var valid: MutableLiveData<Boolean> = MutableLiveData()
    override var switchChecked: MutableLiveData<Boolean> = MutableLiveData()
    override var relationSelected: MutableLiveData<Int> = MutableLiveData()
}