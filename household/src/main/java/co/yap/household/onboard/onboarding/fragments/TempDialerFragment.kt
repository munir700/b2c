package co.yap.household.onboard.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.interfaces.IEmail
import co.yap.household.onboard.onboarding.viewmodels.DialerViewModel

@Deprecated("Not used anymore")
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

