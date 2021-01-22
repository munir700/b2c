package co.yap.modules.dashboard.more.notifications.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.IBase

interface INotificationsHome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val mNotificationsHomeAdapter: ObservableField<NotificationsHomeAdapter>?
        fun getNotification()
    }

    interface State : IBase.State {
        val mNotifications:MutableLiveData<ArrayList<HomeNotification>>?
    }
}
