package co.yap.modules.dashboard.more.notifications.home

import androidx.lifecycle.MutableLiveData
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.BaseState

class NotificationsHomeState : BaseState(), INotificationsHome.State {
    override val mNotifications: MutableLiveData<ArrayList<HomeNotification>>? = MutableLiveData()
}
