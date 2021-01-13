package co.yap.modules.dashboard.more.notifications.home

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.managers.SessionManager
import com.leanplum.Leanplum

class NotificationsHomeViewModel(application: Application) :
    BaseViewModel<INotificationsHome.State>(application),
    INotificationsHome.ViewModel {
    override val state = NotificationsHomeState()
    override val mNotificationsHomeAdapter: ObservableField<NotificationsHomeAdapter>? =
        ObservableField()

    override fun onCreate() {
        super.onCreate()
        //getNotification()
    }
    override fun getNotification() {
        SessionManager.user?.let { account ->
            SessionManager.card.value?.let { card ->
                mNotificationsHomeAdapter?.get()?.setData(
                    NotificationHelper.getNotifications(
                        account,
                        card, context
                    )
                )
            }
        }

    }
//    fun loadNotifications() {
//        launch {
//            state.loading = true
//            val notifications = ArrayList<Notification>()
//            Leanplum.getInbox().allMessages()
//            Leanplum.getInbox().allMessages().forEach {
//                notifications.add(
//                    Notification(
//                        it.title,
//                        if (null != it.data && it.data.has("description")) it.data.getString("description") else "",
//                        "",
//                        "",
//                        it.imageFilePath,
//                        "",
//                        it.deliveryTimestamp.toString(),
//                        it.isRead,
//                        it.messageId
//                    )
//                )
//            }
//            state.hasRecords.set(!notifications.isNullOrEmpty())
//            adapter.setList(notifications)
//            state.loading = false
//        }
//    }
}