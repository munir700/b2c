package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.viewmodels.ConfirmCardPinViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast

class UpdateConfirmPasscodeViewModel(application: Application) :
    ConfirmCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val forgotPasscodeclickEvent: SingleClickEvent = SingleClickEvent()
    private val customersRepository: CustomersRepository = CustomersRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override fun onCreate() {
        super.onCreate()
        state.titleSetPin = getString(Strings.screen_confirm_passcode_display_text_heading)
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        state.forgotTextVisibility = true
    }

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            if (state.newPin == state.pincode) {
                changePasscode(id)
            } else {
                errorEvent.call()
            }
        }
    }


    private fun changePasscode(id: Int) {
        launch {
            state.loading = true
            when (val response = customersRepository.changePasscode(state.pincode)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    clickEvent.postValue(id)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun handlePressOnForgotPasscodeButton(id: Int) {

        val sharedPreferenceManager = SharedPreferenceManager(context)
        sharedPreferenceManager.getDecryptedUserName()?.let {
            launch {
                state.loading = true
                when (val response = messagesRepository.createForgotPasscodeOTP(
                    CreateForgotPasscodeOtpRequest(
                        Utils.verifyUsername(it), !Utils.isUsernameNumeric(it)
                    )
                )) {
                    is RetroApiResponse.Success -> {
                        response.data.data?.let {
                            mobileNumber = it
                        }

                        state.loading = false
                        forgotPasscodeclickEvent.postValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        state.loading = false
                    }
                }
            }
        } ?: toast(context, "Invalid user name")
    }
}