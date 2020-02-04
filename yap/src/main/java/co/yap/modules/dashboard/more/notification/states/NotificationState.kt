package co.yap.modules.dashboard.more.notification.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.notification.interfaces.INotifications
import co.yap.yapcore.BaseState

class NotificationState : BaseState(), INotifications.State {

    override var hasRecords: ObservableField<Boolean> = ObservableField()
}