package co.yap.modules.subaccounts.householdsetpin.hhsetpinstart

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhSetPinCardReviewBinding
import co.yap.modules.subaccounts.householdsetpin.setNewpin.SetPinDataModel
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class HHSetPinCardReviewFragment :
    BaseNavViewModelFragment<FragmentHhSetPinCardReviewBinding, IHHSetPinCardReview.State, HHSetPinCardReviewVM>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin_card_review


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {
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
            //   findNavController().navigate(R.id.action_HHSetPinCardReviewFragment_to_setCardPinFragment2)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObservers(this)
    }
}