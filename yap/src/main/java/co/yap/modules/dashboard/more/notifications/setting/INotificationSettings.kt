package co.yap.modules.dashboard.more.notifications.setting

import android.widget.CompoundButton
import co.yap.networking.notification.NotificationsApi
import co.yap.yapcore.IBase

interface INotificationSettings {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        val repository: NotificationsApi
        fun getNotificationSettings(onComplete:(Boolean)->Unit)
        fun saveNotificationSettings()
    }
    interface State : IBase.State {
        var inAppNotificationsAllowed: Boolean?
        var smsNotificationsAllowed: Boolean?
        var emailNotificationsAllowed: Boolean?
    }
}