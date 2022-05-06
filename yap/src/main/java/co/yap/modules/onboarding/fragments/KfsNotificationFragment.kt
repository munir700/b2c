package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentKfsNotifcationBinding
import co.yap.modules.onboarding.enums.NotificationType
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.modules.onboarding.viewmodels.KfsNotificationViewModel
import co.yap.translation.Strings
import co.yap.yapcore.helpers.customAlertDialog

class KfsNotificationFragment :
    OnboardingChildFragment<FragmentKfsNotifcationBinding, IKfsNotification.ViewModel>(),
    CompoundButton.OnCheckedChangeListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_kfs_notifcation
    override val viewModel: KfsNotificationViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun getViewBinding() = getDataBindingView<FragmentKfsNotifcationBinding>()
    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.cb3 -> enableAllNotifications(
                NotificationType.IN_APP_NOTIFICATION,
                isChecked,
                enableAll = false
            )

            R.id.cb2 -> enableAllNotifications(
                NotificationType.EMAIL_NOTIFICATION,
                isChecked,
                enableAll = false
            )

            R.id.cb1 -> enableAllNotifications(
                NotificationType.SMS_NOTIFICATION,
                isChecked,
                enableAll = false
            )

            R.id.rb1 -> enableAllNotifications(
                NotificationType.ALL_NOTIFICATION,
                isChecked,
                enableAll = isChecked
            )

            R.id.rb2 -> enableAllNotifications(
                NotificationType.NONE_NOTIFICATION,
                isChecked,
                enableAll = false
            )

        }
        viewModel.state.valid.set(viewModel.isAnyOfNotificationSelected() || viewModel.state.notificationMap[NotificationType.NONE_NOTIFICATION] ?: false)
    }

    private fun enableAllNotifications(
        key: NotificationType,
        isChecked: Boolean,
        enableAll: Boolean
    ) {
        viewModel.setUpNotificationOnCheck(
            key,
            isChecked,
            enableAll
        ) { map ->
            getViewBinding().rb1.isChecked =
                map[NotificationType.ALL_NOTIFICATION] ?: false
            getViewBinding().cb1.isChecked =
                map[NotificationType.SMS_NOTIFICATION] ?: false
            getViewBinding().cb2.isChecked =
                map[NotificationType.EMAIL_NOTIFICATION] ?: false
            getViewBinding().cb3.isChecked =
                map[NotificationType.IN_APP_NOTIFICATION] ?: false
            getViewBinding().rb2.isChecked = map[NotificationType.NONE_NOTIFICATION] ?: false

        }
    }

    private val clickListenerHandler = Observer<Int> { id ->
        if (viewModel.state.notificationMap[NotificationType.NONE_NOTIFICATION] == true) showAlertDialog()
        else viewModel.signUp {
            navigateBack()
        }
    }

    fun showAlertDialog() {
        requireContext().customAlertDialog(
            title = getString(Strings.screen_kfs_notification_accept_no_notification_label),
            message = getString(Strings.screen_kfs_notification_accept_no_notification_note),
            positiveButton = getString(Strings.common_text_ok),
            positiveCallback = {
                viewModel.signUp {
                    navigateBack()
                }
            },
        )
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)
        getViewBinding().rb2.setOnCheckedChangeListener(this)
        getViewBinding().rb1.setOnCheckedChangeListener(this)
        getViewBinding().cb1.setOnCheckedChangeListener(this)
        getViewBinding().cb2.setOnCheckedChangeListener(this)
        getViewBinding().cb3.setOnCheckedChangeListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObserver(clickListenerHandler)
    }
}
