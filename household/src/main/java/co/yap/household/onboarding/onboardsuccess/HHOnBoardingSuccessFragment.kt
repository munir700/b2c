package co.yap.household.onboarding.onboardsuccess

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonboardingSuccessBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHOnBoardingSuccessFragment : BaseNavViewModelFragment<FragmentHhonboardingSuccessBinding , IHHOnBoardingSuccess.State , HHOnBoardingSuccessVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhonboarding_success
}
