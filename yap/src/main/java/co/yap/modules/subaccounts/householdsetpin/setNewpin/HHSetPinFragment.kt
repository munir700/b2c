package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhSetPinBinding
import co.yap.translation.Strings
import co.yap.widgets.numberkeyboard.NumberKeyboard
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import kotlinx.android.synthetic.main.include_layout_number_keyboard.*

class HHSetPinFragment :
    BaseNavViewModelFragment<FragmentHhSetPinBinding, IHHSetPin.State, HHSetPinVM>(), NumberKeyboard.NumberKeyboardListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
        dialer.setListener(this)
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
                            pinCode = state.pinCode.value.toString(),
                            setPinTitle = getString(Strings.screen_household_set_pin_text_confirm_pin_title),
                            termsAndConditionVisibility = true,
                            buttonTitle = getString(Strings.screen_household_set_pin_text_button_title),
                            forgotPassCodeVisibility = false
                        )
                    )
                findNavController().navigate(action)
            }
            viewModel.eventSuccess -> {
                navigateForwardWithAnimation(HHSetPinFragmentDirections.actionSetCardPinFragment2ToHHSetPinSuccessFragment())
            }
            viewModel.eventFailure -> {
                dialer.reset()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onNumberClicked(number: Int, numbers: String) {
        state.pinCode.value = numbers
        state.dialerError.value = ""
    }

    override fun onLeftAuxButtonClicked() {

    }

    override fun onRightAuxButtonClicked(numbers: String) {
    }
}