package co.yap.household.onboarding.welcome

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingWelcomeBinding
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHOnBoardingWelcomeFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingWelcomeBinding, IHHOnBoardingWelcome.State, HHOnBoardingWelcomeVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_welcome
    override fun toolBarVisibility() = false
    override fun setDisplayHomeAsUpEnabled() = true
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnGetStarted -> {
                arguments?.putInt(Constants.INDEX, 20)
                navigateForwardWithAnimation(
                    HHOnBoardingWelcomeFragmentDirections.toHHOnBoardingMobileFragment(),
                    arguments,
                    null
                )
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
