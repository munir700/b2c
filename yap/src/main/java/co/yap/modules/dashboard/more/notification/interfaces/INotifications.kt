package co.yap.modules.dashboard.more.notification.interfaces

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.notification.adaptor.NotificationsAdaptor
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface INotifications {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun loadNotifications()
        val adapter: NotificationsAdaptor
    }

    interface State : IBase.State {
        var hasRecords: ObservableField<Boolean>
    }
}