package co.yap.household.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.household.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.household.onboarding.viewmodels.OnboardingViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase


abstract class OnboardingChildFragment<V : IBase.ViewModel<*>> : BaseBindingFragment<V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is OnboardingChildViewModel<*>) {
            (viewModel as OnboardingChildViewModel<*>).parentViewModel =
                ViewModelProviders.of(activity!!).get(OnboardingViewModel::class.java)
        }
    }
}