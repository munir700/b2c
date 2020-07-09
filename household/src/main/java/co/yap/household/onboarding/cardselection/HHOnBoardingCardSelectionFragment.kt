package co.yap.household.onboarding.cardselection

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingCardSelectionBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class HHOnBoardingCardSelectionFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingCardSelectionBinding, IHHOnBoardingCardSelection.State, HHOnBoardingCardSelectionVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_card_selection
}
