package co.yap.modules.dashboard.cards.paymentcarddetail.forgotcardpin.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.forgotcardpin.activities.ForgotCardPinActivity
import co.yap.modules.dashboard.more.profile.fragments.CurrentPasscodeFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentSetCardPinBinding

class VerifyCurrentPasscodeFragment : CurrentPasscodeFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.forgotTextVisibility = false
        if (activity is ForgotCardPinActivity)
            (activity as ForgotCardPinActivity).preventTakeDeviceScreenShot.value = true
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action =
                        VerifyCurrentPasscodeFragmentDirections.actionVerifyCurrentPasscodeFragmentToSetNewCardPinFragment2(
                            Constants.FORGOT_CARD_PIN_FLOW
                        )
                    findNavController().navigate(action)
                }
            }
        })
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

}