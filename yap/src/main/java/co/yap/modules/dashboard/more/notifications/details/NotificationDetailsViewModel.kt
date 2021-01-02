package co.yap.modules.dashboard.more.notifications.details

import android.app.Application
import co.yap.yapcore.BaseViewModel

class NotificationDetailsViewModel(application: Application) :
    BaseViewModel<INotificationDetails.State>(application),
    INotificationDetails.ViewModel {
    override val state = NotificationDetailsState()
}