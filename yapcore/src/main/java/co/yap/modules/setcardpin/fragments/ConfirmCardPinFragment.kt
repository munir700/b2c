package co.yap.modules.setcardpin.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.viewmodels.ConfirmCardPinViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.R
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.trackEvent
import co.yap.yapcore.helpers.extentions.trackEventWithAttributes
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_set_card_pin.*

open class ConfirmCardPinFragment : SetCardPinFragment() {

    private val args: ConfirmCardPinFragmentArgs by navArgs()
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(ConfirmCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    if (viewModel.pincode == viewModel.state.pincode) {
                        if (activity is SetCardPinWelcomeActivity) {
                            (activity as SetCardPinWelcomeActivity).cardSerialNumber?.let { serialNumber ->
                                viewModel.setCardPin(
                                    serialNumber
                                )
                            }
                        }
                    } else {
                        dialer.startAnimationDigits()
                    }
                }
                viewModel.EVENT_SET_CARD_PIN_SUCCESS -> {
                    trackEvent(KYCEvents.CARD_ACTIVE.type)
                    trackEventWithAttributes(MyUserManager.user, account_active = true)
                    findNavController().navigate(R.id.action_confirmCardPinFragment_to_setCardPinSuccessFragment)
                    MyUserManager.user?.notificationStatuses = AccountStatus.CARD_ACTIVATED.name
                }
            }
        })
    }

    override fun loadData() {
        tvTitle.text = Translator.getString(
            requireContext(),
            Strings.screen_confirm_card_pin_display_text_title
        )
        btnAction.text = Translator.getString(
            requireContext(),
            Strings.screen_confirm_card_pin_button_create_pin
        )
        viewModel.pincode =
            arguments?.let { ConfirmCardPinFragmentArgs.fromBundle(it).pincode } as String

    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}