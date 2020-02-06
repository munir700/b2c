package co.yap.modules.dashboard.more.notification.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.notification.interfaces.INotificationDetail
import co.yap.modules.dashboard.more.notification.states.NotificationDetailState
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class NotificationDetailViewModel(application: Application) :
    BaseViewModel<INotificationDetail.State>(application), INotificationDetail.ViewModel {

    override val state: NotificationDetailState = NotificationDetailState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnDeleteNotification(id: Int) {
        clickEvent.setValue(id)
    }

    override lateinit var notification: Notification
}