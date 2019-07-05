package co.yap.modules.onboarding.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.viewmodels.MobileViewModel
import co.yap.yapcore.BaseBindingFragment


class MobileFragment : OnboardingChildFragment<IMobile.ViewModel>() {

    override fun getBindingVariable(): Int = BR.mobileViewModel
    override fun getLayoutId(): Int = R.layout.fragment_mobile

    override val viewModel: IMobile.ViewModel
        get() = ViewModelProviders.of(this).get(MobileViewModel::class.java)


}