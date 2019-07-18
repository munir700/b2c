package co.yap.modules.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.adapters.WelcomePagerAdapter
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseBindingFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class WelcomeFragment : BaseBindingFragment<IWelcome.ViewModel>(), IWelcome.View {

    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.screen_onboarding_welcome

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.accountType = getAccountType()
        val pager = view?.findViewById<ViewPager>(R.id.welcome_pager)
        pager?.adapter = WelcomePagerAdapter(
            context = requireContext(),
            contents = viewModel.getPages(),
            layout = R.layout.content_onboarding_welcome
        )

        view?.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)?.setViewPager(pager)
        viewModel.onGetStartedPressEvent.observe(this, getStartedButtonObserver)
    }

    private fun getAccountType(): AccountType =
        arguments?.getSerializable(getString(R.string.arg_account_type)) as AccountType

    private val getStartedButtonObserver = Observer<Boolean> {
        startActivity(OnboardingActivity.newIntent(requireContext(), getAccountType()))
        // finish()
    }

    override fun onDestroyView() {
        viewModel.onGetStartedPressEvent.removeObservers(this)
        super.onDestroyView()
    }

}