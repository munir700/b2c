package co.yap.modules.onboarding.fragments

import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentKfsNotifcationBinding
import co.yap.modules.onboarding.interfaces.IKfsNotification
import co.yap.modules.onboarding.viewmodels.KfsNotificationViewModel

class KfsNotificationFragment :
    OnboardingChildFragment<FragmentKfsNotifcationBinding, IKfsNotification.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_kfs_notifcation

    override val viewModel: KfsNotificationViewModel by viewModels()
}