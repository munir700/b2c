package co.yap.modules.dashboard.more.notifications.details

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class NotificationDetailsViewModel(application: Application) :
    BaseViewModel<INotificationDetails.State>(application),
    INotificationDetails.ViewModel {
    override val state = NotificationDetailsState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}