package co.yap.modules.dashboard.more.notifications.setting

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment

class NotificationSettingsFragment : BaseBindingFragment<INotificationSettings.ViewModel>(),
    INotificationSettings.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_notification_settings

    override val viewModel: INotificationSettings.ViewModel
        get() = ViewModelProviders.of(this).get(NotificationSettingsViewModel::class.java)

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> navigateBack()
        }
    }
}