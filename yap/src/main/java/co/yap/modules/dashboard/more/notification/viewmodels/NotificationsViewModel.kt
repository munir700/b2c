package co.yap.modules.dashboard.more.notification.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.notification.adaptor.NotificationsAdaptor
import co.yap.modules.dashboard.more.notification.interfaces.INotifications
import co.yap.modules.dashboard.more.notification.states.NotificationState
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import com.leanplum.Leanplum

class NotificationsViewModel(application: Application) :
    BaseViewModel<INotifications.State>(application),
    INotifications.ViewModel {

    override val state: NotificationState = NotificationState()
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
                        it.deliveryTimestamp.toString()
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