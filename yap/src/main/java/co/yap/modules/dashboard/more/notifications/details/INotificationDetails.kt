package co.yap.modules.dashboard.more.notifications.details

import androidx.lifecycle.MutableLiveData
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface INotificationDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id:Int)
    }

    interface State : IBase.State {
        val notification:HomeNotification?
    }
}