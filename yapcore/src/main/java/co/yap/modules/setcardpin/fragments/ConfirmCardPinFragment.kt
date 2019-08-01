package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.viewmodels.SetCardPinViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.fragment_set_card_pin.*

class ConfirmCardPinFragment : SetCardPinFragment() {

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        clTermsAndConditions.visibility = View.VISIBLE
        tvTitle.text = Translator.getString(requireContext(), Strings.screen_confirm_card_pin_display_text_title)
        btnAction.text = Translator.getString(requireContext(), Strings.screen_confirm_card_pin_button_create_pin)

        showToast(arguments?.let { ConfirmCardPinFragmentArgs.fromBundle(it).pincode } as String)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> showToast("Next Confirm")
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}