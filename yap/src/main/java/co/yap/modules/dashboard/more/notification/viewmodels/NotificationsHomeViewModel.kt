package co.yap.modules.dashboard.more.notification.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.notification.adaptor.NotificationsAdaptor
import co.yap.modules.dashboard.more.notification.interfaces.INotificationHome
import co.yap.yapcore.SingleClickEvent

class NotificationsHomeViewModel(application: Application) :
    NotificationsBaseViewModel<INotificationHome.State>(application),
    INotificationHome.ViewModel {
    override val clickEvent: SingleClickEvent
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val adapter: NotificationsAdaptor
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun handlePressOnView(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadNotifications() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val state: INotificationHome.State
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


}