package co.yap.household.onboarding.onboardemail

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingEmailBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class HHOnBoardingEmailFragment : BaseNavViewModelFragment<FragmentHhonBoardingEmailBinding , IHHOnBoardingEmail.State,HHOnBoardingEmailVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_email
}
