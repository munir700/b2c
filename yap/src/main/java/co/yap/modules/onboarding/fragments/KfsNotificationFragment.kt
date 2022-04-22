package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentKfsNotifcationBinding
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

    private fun addObservers() {
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)
        getViewBinding().rb2.setOnCheckedChangeListener(this)
        getViewBinding().rb1.setOnCheckedChangeListener(this)
        getViewBinding().cb1.setOnCheckedChangeListener(this)
        getViewBinding().cb2.setOnCheckedChangeListener(this)
        getViewBinding().cb3.setOnCheckedChangeListener(this)
    }

    private fun getViewBinding() = getDataBindingView<FragmentKfsNotifcationBinding>()
    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.cb3 -> {
                viewModel.parentViewModel?.state?.inappNotificationAccepted?.value = isChecked
                enableAllNotifications(false)
            }
            R.id.cb2 -> {
                viewModel.parentViewModel?.state?.emailNotificationAccepted?.value = isChecked
                enableAllNotifications(false)
            }
            R.id.cb1 -> {
                viewModel.parentViewModel?.state?.smsNotificationAccepted?.value = isChecked
                enableAllNotifications(false)
            }
            R.id.rb1 -> {
                viewModel.parentViewModel?.state?.allNotificationAccepted?.value = isChecked
                enableAllNotifications(isChecked)
            }
            R.id.rb2 -> {
                viewModel.parentViewModel?.state?.noNotificationAccepted?.value = isChecked
                if (isChecked) viewModel.revertAllAppNotifications()
                enableAllNotifications(false)
            }
        }
        viewModel.state.valid.set(viewModel.isAnyNotificationSelected() || viewModel.parentViewModel?.state?.noNotificationAccepted?.value == true)
    }

    private fun enableAllNotifications(isEnabled: Boolean = true) {
        viewModel.parentViewModel?.state?.let { parentState ->
            with(parentState) {
                if (isEnabled) viewModel.enableAllAppNotifications()
                getViewBinding().rb1.isChecked =
                    if (isEnabled) isEnabled else viewModel.isAnyNotificationSelected()
                getViewBinding().cb1.isChecked =
                    if (isEnabled) isEnabled else smsNotificationAccepted.value ?: false
                getViewBinding().cb2.isChecked =
                    if (isEnabled) isEnabled else emailNotificationAccepted.value ?: false
                getViewBinding().cb3.isChecked =
                    if (isEnabled) isEnabled else inappNotificationAccepted.value ?: false
                getViewBinding().rb2.isChecked =
                    if (isEnabled) isEnabled.not() else noNotificationAccepted.value ?: false && viewModel.isAnyNotificationSelected()
                        .not()
            }
        }
    }

    private val clickListenerHandler = Observer<Int> { id ->
        if (viewModel.parentViewModel?.state?.noNotificationAccepted?.value == true) showAlertDialog()
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
}