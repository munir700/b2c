package co.yap.modules.dashboard.more.notifications.details

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.BaseState

class NotificationDetailsState : BaseState(), INotificationDetails.State {
    @get:Bindable
    override var notification: HomeNotification? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.notification)
        }
}