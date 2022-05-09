package co.yap.modules.onboarding.states

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.enums.NotificationType
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.yapcore.BaseState

class KfsNotificationState : BaseState(), IKfsNotification.State {
    override var valid: ObservableBoolean = ObservableBoolean(false)
    override var notificationMap: MutableMap<NotificationType, Boolean?> = mutableMapOf()
    override var isNotificationSaved: MutableLiveData<Boolean> = MutableLiveData(false)
}