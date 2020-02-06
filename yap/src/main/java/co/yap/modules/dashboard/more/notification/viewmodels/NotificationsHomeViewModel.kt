package co.yap.modules.dashboard.more.notification.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.notification.adaptor.NotificationsAdaptor
import co.yap.modules.dashboard.more.notification.interfaces.INotificationHome
import co.yap.modules.dashboard.more.notification.states.NotificationsHomeState
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.SingleClickEvent
import com.leanplum.Leanplum

class NotificationsHomeViewModel(application: Application) :
    NotificationsBaseViewModel<INotificationHome.State>(application),
    INotificationHome.ViewModel {
    override val state: NotificationsHomeState = NotificationsHomeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val adapter: NotificationsAdaptor = NotificationsAdaptor(mutableListOf())

    override fun loadNotifications() {
        launch {
            state.loading = true
            val notifications = ArrayList<Notification>()
            Leanplum.getInbox().allMessages().forEach {
                notifications.add(
                    Notification(
                        it.title,
                        "",
                        "",
                        "",
                        it.imageFilePath,
                        "",
                        it.deliveryTimestamp.toString(),
                        it.isRead
                    )
                )
            }
            state.hasRecords.set(!notifications.isNullOrEmpty())
            adapter.setList(notifications)
            state.loading = false
        }
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}