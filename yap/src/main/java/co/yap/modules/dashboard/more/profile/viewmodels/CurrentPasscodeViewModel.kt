package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ChangeCardPinViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast

class CurrentPasscodeViewModel(application: Application) : ChangeCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var errorEvent: SingleClickEvent = SingleClickEvent()
    override val forgotPasscodeclickEvent: SingleClickEvent = SingleClickEvent()
    private val messagesRepository: MessagesRepository = MessagesRepository
    private val customersRepository: CustomersRepository = CustomersRepository

    override var mobileNumber: String = ""
    var token: String = ""

    override fun onCreate() {
        super.onCreate()
        state.titleSetPin = getString(Strings.screen_current_passcode_display_text_heading)
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        state.forgotTextVisibility = true
    }

    override fun handlePressOnNextButton(id: Int) {
        validateCurrentPasscode(id)
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
                        state.loading = false
                    }
                }
            }
        } ?: toast(context, "Invalid user name")
    }

    private fun validateCurrentPasscode(id: Int) {
        launch {
            state.loading = true
            when (val response = customersRepository.validateCurrentPasscode(state.pincode)) {
                is RetroApiResponse.Success -> {
                    token = response.data.token ?: ""
                    clickEvent.setValue(id)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    errorEvent.call()
                    state.loading = false
                }
            }
        }
    }
}