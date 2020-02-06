package co.yap.modules.dashboard.more.notification.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.notification.interfaces.INotificationSettings
import co.yap.modules.dashboard.more.notification.viewmodels.NotificationSettingsViewModel
import co.yap.yapcore.BaseBindingFragment

class NotificationSettingsFragment : BaseBindingFragment<INotificationSettings.ViewModel>(),
    INotificationSettings.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_notification_settings

    override val viewModel: INotificationSettings.ViewModel
        get() = ViewModelProviders.of(this).get(NotificationSettingsViewModel::class.java)
}