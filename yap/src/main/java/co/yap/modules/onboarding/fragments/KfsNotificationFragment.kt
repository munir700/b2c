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
        viewModel.clickEvent.observe(viewLifecycleOwner, Observer {
            when(id){
                R.id.next_button-> navigate(R.id.action_kfsNotificationFragment_to_congratulationsFragment)
            }
        })
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
                viewModel.state.inappNotificationAccepted.value = isChecked
                enableAllNotifications(false)
            }
            R.id.cb2 -> {
                viewModel.state.emailNotificationAccepted.value = isChecked
                enableAllNotifications(false)
            }
            R.id.cb1 -> {
                viewModel.state.smsNotificationAccepted.value = isChecked
                enableAllNotifications(false)
            }
            R.id.rb1 -> {
                viewModel.state.allNotificationAccepted.value = isChecked
                enableAllNotifications(isChecked)
            }
            R.id.rb2 -> {
                if (isChecked) viewModel.revertAllAppNotifications()
                enableAllNotifications(false)
            }
        }
    }

    private fun enableAllNotifications(isEnabled: Boolean = true) {
        with(viewModel.state) {
            if (isEnabled) viewModel.enableAllAppNotifications()
            getViewBinding().rb1.isChecked =
                if (isEnabled) isEnabled else viewModel.getAllAppNotificationSettings()
            getViewBinding().cb1.isChecked =
                if (isEnabled) isEnabled else smsNotificationAccepted.value ?: false
            getViewBinding().cb2.isChecked =
                if (isEnabled) isEnabled else emailNotificationAccepted.value ?: false
            getViewBinding().cb3.isChecked =
                if (isEnabled) isEnabled else inappNotificationAccepted.value ?: false
            getViewBinding().rb2.isChecked =
                if (isEnabled) isEnabled.not() else noNotificationAccepted.value?:false && viewModel.isAnyNotificationSelected().not()
        }
    }
}