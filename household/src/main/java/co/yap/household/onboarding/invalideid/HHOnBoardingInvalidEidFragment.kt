package co.yap.household.onboarding.invalideid

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingInvalidEidBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHOnBoardingInvalidEidFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingInvalidEidBinding, IHHOnBoardingInvalidEid.State, HHOnBoardingInvalidEidVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_invalid_eid
}
