package co.yap.household.onboarding.onboardmobile

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonboardingMobileBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class HHOnBoardingMobileFragment :
    BaseNavViewModelFragment<FragmentHhonboardingMobileBinding, IHHOnBoardingMobile.State, HHOnBoardingMobileVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhonboarding_mobile
}
