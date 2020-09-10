package co.yap.modules.dashboard.store.young.confirmrelationship

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class YoungConfirmRelationshipState : BaseState(), IYoungConfirmRelationship.State{
    override var email: MutableLiveData<String> = MutableLiveData()
}