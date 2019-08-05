package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.viewmodels.ConfirmCardPinViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.fragment_set_card_pin.*

class ConfirmCardPinFragment : SetCardPinFragment() {

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(ConfirmCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        clTermsAndConditions.visibility = View.VISIBLE
        tvTitle.text = Translator.getString(requireContext(), Strings.screen_confirm_card_pin_display_text_title)
        btnAction.text = Translator.getString(requireContext(), Strings.screen_confirm_card_pin_button_create_pin)

        viewModel.pincode = arguments?.let { ConfirmCardPinFragmentArgs.fromBundle(it).pincode } as String
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    if (viewModel.pincode.equals(viewModel.state.pincode)) {
                        showToast("Pin Match")
                    } else {
                        dialer.startAnimationDigits()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}