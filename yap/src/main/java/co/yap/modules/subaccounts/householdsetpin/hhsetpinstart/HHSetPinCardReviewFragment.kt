package co.yap.modules.subaccounts.householdsetpin.hhsetpinstart

import co.yap.R
import co.yap.BR
import co.yap.databinding.FragmentHhSetPinCardReviewBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHSetPinCardReviewFragment :
    BaseNavViewModelFragment<FragmentHhSetPinCardReviewBinding, IHHSetPinCardReview.State, HHSetPinCardReviewVM>() {
    override fun getBindingVariable(): Int =BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_card_review
}