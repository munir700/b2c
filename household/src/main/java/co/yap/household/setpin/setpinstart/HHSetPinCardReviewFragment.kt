package co.yap.household.setpin.setpinstart

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhSetPinCardReviewBinding
import co.yap.household.setpin.setnewpin.SetPinDataModel
import co.yap.translation.Strings

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHSetPinCardReviewFragment :
    BaseNavViewModelFragmentV2<FragmentHhSetPinCardReviewBinding, IHHSetPinCardReview.State, HHSetPinCardReviewVM>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override val viewModel: HHSetPinCardReviewVM by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_card_review

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
//        viewModel.getCard()
    }

    override fun onClick(id: Int) {
        val action =
            HHSetPinCardReviewFragmentDirections.actionHHSetPinCardReviewFragmentToSetCardPinFragment2(
                SetPinDataModel(
                    screenType = "setPin",
                    setPinTitle = getString(Strings.screen_household_set_pin_text_title),
                    termsAndConditionVisibility = true,
                    buttonTitle = getString(Strings.screen_household_set_pin_text_button_title),
                    forgotPassCodeVisibility = false
                )
            )
        findNavController().navigate(action)
    }
}
