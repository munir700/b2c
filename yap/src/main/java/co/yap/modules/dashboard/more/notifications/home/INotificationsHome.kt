package co.yap.modules.dashboard.more.notifications.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.notification.NotificationsApi
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.yapcore.IBase

interface INotificationsHome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val mNotificationsHomeAdapter: ObservableField<NotificationsHomeAdapter>?
        val repository: NotificationsApi
        fun getNotification()
        fun getFcmNotifications()
        fun deleteFcmNotifications(notifId : String)
        fun updateFcmNotifications(notifId : String, isRead : Boolean)
    }

    interface State : IBase.State {
        val mNotifications:MutableLiveData<MutableList<HomeNotification>>?
    }
}
