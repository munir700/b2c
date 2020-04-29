package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhSetPinBinding
import co.yap.translation.Strings
import co.yap.widgets.numberkeyboard.NumberKeyboard
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.toast
import kotlinx.android.synthetic.main.fragment_hh_set_pin.*

/*
* Developer guide to use this fragment
*
*
* */
class HHSetPinFragment :
    BaseNavViewModelFragment<FragmentHhSetPinBinding, IHHSetPin.State, HHSetPinVM>(), NumberKeyboard.NumberKeyboardListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        dialer.setListener(this)
//        dialer.setInputView(tvInputField)
        dialer.setInputView(tvInputField)
        dialer.setPassCodeView(passCodeView)
    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                val action =
                    HHSetPinFragmentDirections.actionSetCardPinFragment2ToConfirmCardPinFragment2(
                        SetPinDataModel(
                            screenType = "confirmPin",
                            setPinTitle = getString(Strings.screen_household_set_pin_text_confirm_pin_title),
                            termsAndConditionVisibility = true,
                            buttonTitle = getString(Strings.screen_household_set_pin_text_button_title),
                            forgotPassCodeVisibility = false
                        )
                    )
                findNavController().navigate(action)
            }
            else -> {
                findNavController().navigate(R.id.action_setCardPinFragment2_to_HHSetPinSuccessFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onNumberClicked(number: Int, numbers: String) {
    }

    override fun onLeftAuxButtonClicked() {

    }

    override fun onRightAuxButtonClicked(numbers: String) {
    }
}