package co.yap.modules.dashboard.more.notifications.setting

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentNotificationSettingsBinding
import co.yap.yapcore.BaseBindingFragmentV2

class NotificationSettingsFragment :
    BaseBindingFragmentV2<FragmentNotificationSettingsBinding, INotificationSettings.ViewModel>(),
    INotificationSettings.View, CompoundButton.OnCheckedChangeListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_notification_settings

    override val viewModel: INotificationSettings.ViewModel
        get() = ViewModelProvider(this).get(NotificationSettingsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotificationSettings {
            if (it) {
                viewDataBinding.swNotifications.setOnCheckedChangeListener(this)
                viewDataBinding.swEmail.setOnCheckedChangeListener(this)
                viewDataBinding.swSms.setOnCheckedChangeListener(this)
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.swNotifications -> viewModel.state.inAppNotificationsAllowed = isChecked
            R.id.swEmail -> viewModel.state.emailNotificationsAllowed = isChecked
            R.id.swSms -> viewModel.state.smsNotificationsAllowed = isChecked
        }
        viewModel.saveNotificationSettings()
    }

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> navigateBack()
        }
    }
}
