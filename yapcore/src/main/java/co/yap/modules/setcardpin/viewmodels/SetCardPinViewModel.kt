package co.yap.modules.setcardpin.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.states.SetCardPinState
import co.yap.translation.Strings

import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.StringUtils

open class SetCardPinViewModel(application: Application) :
    BaseViewModel<ISetCardPin.State>(application),
    ISetCardPin.ViewModel {

    override val forgotPasscodeclickEvent: SingleClickEvent = SingleClickEvent()
    override var mobileNumber: String = ""
    override var pincode: String = ""
    override val state: SetCardPinState = SetCardPinState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()


    override fun onCreate() {
        super.onCreate()
        state.titleSetPin = getString(Strings.screen_set_card_pin_display_text_title)
        state.buttonTitle = getString(Strings.screen_set_card_pin_button_create_pin)
    }


    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            clickEvent.setValue(id)
        }
    }

    override fun handlePressOnForgotPasscodeButton(id: Int) {
        //forgotPasscodeclickEvent.postValue(id)
    }

    override fun setCardPin(cardSerialName: String) {}
    override fun changeCardPinRequest(
        oldPin: String,
        newPin: String,
        confirmPin: String,
        cardSerialNumber: String,
        id: Int
    ) {
    }

    override fun forgotCardPinRequest(cardSerialNumber: String, newPin: String, token: String) {

    }

    fun validateAggressively(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.pincode)
        val isSequenced = StringUtils.isSequenced(state.pincode)
        if (isSequenced) state.dialerError =
            getString(Strings.screen_confirm_card_pin_display_text_error_sequence)
        if (isSame) state.dialerError =
            getString(Strings.screen_confirm_card_pin_display_text_error_same_digits)
        return !isSame && !isSequenced
    }

}