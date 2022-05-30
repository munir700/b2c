package co.yap.household.setpin.setnewpin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhSetPinBinding
import co.yap.translation.Strings
import co.yap.widgets.numberkeyboard.NumberKeyboard

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHSetPinFragment :
    BaseNavViewModelFragmentV2<FragmentHhSetPinBinding, IHHSetPin.State, HHSetPinVM>(),
    NumberKeyboard.NumberKeyboardListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override val viewModel: HHSetPinVM by viewModels()

    override fun getLayoutId(): Int = R.layout.fragment_hh_set_pin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.includeDialer.dialer.setListener(this)
        viewDataBinding.includeDialer.dialer.setInputView(viewDataBinding.includeDialer.tvInputField)
        viewDataBinding.includeDialer.dialer.setPassCodeView(viewDataBinding.includeDialer.passCodeView)
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnAction -> {
                val action =
                    HHSetPinFragmentDirections.actionSetCardPinFragment2ToConfirmCardPinFragment2(
                        SetPinDataModel(
                            screenType = "confirmPin",
                            pinCode = viewModel.state.pinCode.value.toString(),
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
                viewDataBinding.includeDialer.dialer.reset()
            }
        }
    }

    override fun onNumberClicked(number: Int, numbers: String) {
        viewModel.state.pinCode.value = numbers
        viewModel.state.dialerError.value = ""
    }

    override fun onLeftAuxButtonClicked() {}

    override fun onRightAuxButtonClicked(numbers: String) {}
}

