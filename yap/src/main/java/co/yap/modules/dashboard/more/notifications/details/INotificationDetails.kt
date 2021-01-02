package co.yap.modules.dashboard.more.notifications.details

import androidx.lifecycle.MutableLiveData
import co.yap.networking.notification.HomeNotification
import co.yap.yapcore.IBase

interface INotificationDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
        val notification:HomeNotification?
    }
}