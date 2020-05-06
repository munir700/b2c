package co.yap.modules.setcardpin.pinflow

import android.app.Application
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.requestdtos.ChangeCardPinRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.StringUtils

/*
* This is open because current passcode view model is using this fragment. (decision remaining)
* */

open class PinViewModel(application: Application) :
    BaseViewModel<IPin.State>(application),
    IPin.ViewModel, IRepositoryHolder<CardsRepository> {
    override val repository: CardsRepository = CardsRepository
    override val forgotPasscodeclickEvent: SingleClickEvent = SingleClickEvent()
    override var mobileNumber: String = ""
    override fun setChangeCardPinFragmentData() {
        state.titleSetPin = getString(Strings.screen_current_card_pin_display_text_heading)
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
    }

    override fun setNewCardPinFragmentdata() {
        state.titleSetPin = getString(Strings.screen_create_card_pin_display_text_heading)
        state.buttonTitle = getString(Strings.screen_create_card_pin_display_button_create_pin)
    }

    override fun setConfirmNewCardPinFragmentData() {
        state.titleSetPin = getString(Strings.screen_confirm_card_pin_display_text_heading)
        state.buttonTitle = getString(Strings.screen_confirm_card_pin_display_button_confirm_pin)
    }

    override var pincode: String = ""
    override val state: PinState = PinState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()


    override fun onCreate() {
        super.onCreate()

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
        success: () -> Unit
    ) {
        launch {
            state.loading = true
            when (val response = repository.changeCardPinRequest(
                ChangeCardPinRequest(
                    oldPin,
                    newPin,
                    confirmPin, cardSerialNumber
                )
            )) {
                is RetroApiResponse.Success -> {
                    success()
                }
                is RetroApiResponse.Error ->
                    state.dialerError = response.error.message
            }
            state.loading = false
        }

    }

    override fun forgotCardPinRequest(cardSerialNumber: String, newPin: String) {

    }

    private fun validateAggressively(): Boolean {
        val isSame = StringUtils.hasAllSameChars(state.pincode)
        val isSequenced = StringUtils.isSequenced(state.pincode)
        if (isSequenced) state.dialerError =
            getString(Strings.screen_confirm_card_pin_display_text_error_sequence)
        if (isSame) state.dialerError =
            getString(Strings.screen_confirm_card_pin_display_text_error_same_digits)
        return !isSame && !isSequenced
    }
}