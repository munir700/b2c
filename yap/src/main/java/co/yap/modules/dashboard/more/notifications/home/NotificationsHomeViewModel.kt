package co.yap.modules.dashboard.more.notifications.home

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.managers.SessionManager

class NotificationsHomeViewModel(application: Application) :
    BaseViewModel<INotificationsHome.State>(application),
    INotificationsHome.ViewModel {
    override val state = NotificationsHomeState()
    override val mNotificationsHomeAdapter: ObservableField<NotificationsHomeAdapter>? =
        ObservableField()

    override fun onCreate() {
        super.onCreate()
        getNotification()
    }

    override fun getNotification() {
        SessionManager.user?.let { account ->
            SessionManager.card.value?.let { card ->
                state.mNotifications?.value =
                    NotificationHelper.getNotifications(
                        account,
                        card, context
                    )
                mNotificationsHomeAdapter?.get()?.setData(
                    state.mNotifications?.value ?: arrayListOf()
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