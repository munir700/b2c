package co.yap.household.onboarding.onboarding.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.fragments.OnboardingChildFragment
import co.yap.household.onboarding.onboarding.interfaces.IEmail
import co.yap.household.onboarding.onboarding.viewmodels.DialerViewModel
import kotlinx.android.synthetic.main.dialer_layout.*

class TempDialerFragment : OnboardingChildFragment<IEmail.ViewModel>() {


    override fun getBindingVariable(): Int = BR.dialerViewModel

    override fun getLayoutId(): Int = R.layout.dialer_layout

    override val viewModel: IEmail.ViewModel
        get() = ViewModelProviders.of(this).get(DialerViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.animationStartEvent.removeObservers(this)
        super.onDestroyView()
    }


    override fun onBackPressed(): Boolean = true
}

