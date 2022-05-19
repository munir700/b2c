package co.yap.household.onboarding.welcome

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingWelcomeBinding
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHOnBoardingWelcomeFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingWelcomeBinding, IHHOnBoardingWelcome.State, HHOnBoardingWelcomeVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_welcome
    override fun toolBarVisibility() = false
    override fun setDisplayHomeAsUpEnabled() = true

    override val viewModel: HHOnBoardingWelcomeVM by viewModels()

    override fun onClick(id: Int) {
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
}
