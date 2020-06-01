package co.yap.household.setpin.setpinstart

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhSetPinCardReviewBinding
import co.yap.household.setpin.setnewpin.SetPinDataModel
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHSetPinCardReviewFragment :
    BaseNavViewModelFragment<FragmentHhSetPinCardReviewBinding, IHHSetPinCardReview.State, HHSetPinCardReviewVM>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_card_review

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.getCard()
        viewModel.clickEvent.observe(this, observer)
    }

    var observer = Observer<Int> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObservers(this)
    }
}