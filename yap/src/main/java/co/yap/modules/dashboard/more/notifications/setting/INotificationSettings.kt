package co.yap.modules.dashboard.more.notifications.setting

import co.yap.yapcore.IBase

interface INotificationSettings {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State {
        var inAppNotificationsAllowed: Boolean
        var smsNotificationsAllowed: Boolean
        var emailNotificationsAllowed: Boolean
    }
}