package co.yap.app.modules.startup.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import co.yap.BR
import co.yap.app.R
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseBindingFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class WelcomeFragment : BaseBindingFragment<IWelcome.ViewModel>(), IWelcome.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.screen_onboarding_welcome

    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.accountType = getAccountType()
        val pager = view?.findViewById<ViewPager>(R.id.welcome_pager)
        pager?.adapter = co.yap.app.modules.startup.adapters.WelcomePagerAdapter(
            context = requireContext(),
            contents = viewModel.getPages(),
            layout = R.layout.content_onboarding_welcome
        )

        view?.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)?.setViewPager(pager)
        viewModel.onGetStartedPressEvent.observe(this, getStartedButtonObserver)
    }

    override fun onDestroyView() {
        viewModel.onGetStartedPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val getStartedButtonObserver = Observer<Boolean> {
        findNavController().navigate(R.id.action_welcomeFragment_to_onboardingActivity, arguments)
    }

    private fun getAccountType(): AccountType =
        arguments?.getSerializable(getString(R.string.arg_account_type)) as AccountType
}