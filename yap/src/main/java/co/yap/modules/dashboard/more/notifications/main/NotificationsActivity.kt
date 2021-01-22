package co.yap.modules.dashboard.more.notifications.main

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator

class NotificationsActivity : BaseBindingActivity<INotifications.ViewModel>(),
    INotifications.View, INavigator, IFragmentHolder {
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@NotificationsActivity,
            R.id.more_notification_nav_host_fragment
        )
    override val viewModel: INotifications.ViewModel
        get() = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_notification

}