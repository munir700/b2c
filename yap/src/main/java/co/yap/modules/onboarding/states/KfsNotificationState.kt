package co.yap.modules.onboarding.states

import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.yapcore.BaseState

class KfsNotificationState : BaseState(), IKfsNotification.State {
    override var allNotificationAccepted: MutableLiveData<Boolean> = MutableLiveData(false)
    override var smsNotificationAccepted: MutableLiveData<Boolean> = MutableLiveData(false)
    override var inappNotificationAccepted: MutableLiveData<Boolean> = MutableLiveData(false)
    override var emailNotificationAccepted: MutableLiveData<Boolean> = MutableLiveData(false)
    override var noNotificationAccepted: MutableLiveData<Boolean> = MutableLiveData(false)
}