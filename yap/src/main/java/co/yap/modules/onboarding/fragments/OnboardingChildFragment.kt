package co.yap.modules.onboarding.fragments

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.modules.onboarding.viewmodels.OnboardingViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase

abstract class OnboardingChildFragment<VB : ViewDataBinding,V : IBase.ViewModel<*>> : BaseBindingFragment<VB, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is OnboardingChildViewModel<*>) {
            (viewModel as OnboardingChildViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(OnboardingViewModel::class.java)
        }
    }
}