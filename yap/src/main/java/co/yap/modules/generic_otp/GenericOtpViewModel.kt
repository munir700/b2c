package co.yap.modules.generic_otp

import android.app.Application
import co.yap.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class GenericOtpViewModel(application: Application) : ForgotPasscodeOtpViewModel(application) {

    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val repository: MessagesRepository = MessagesRepository
    private val messagesRepository: MessagesRepository = MessagesRepository
    override var action: String = ""

    override fun handlePressOnSendButton(id: Int) {
        verifyOtp(id)
    }

    private fun verifyOtp(id: Int) {

        if (action == Constants.CHANGE_MOBILE_NO) {
            launch {
                state.loading = true
                when (val response =
                    repository.verifyOtpGenericWithPhone(
                        state.mobileNumber[0]!!.replace(" ","").replace("+",""), VerifyOtpGenericRequest(action, state.otp)
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        nextButtonPressEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        state.loading = false
                    }
                }
                state.loading = false
            }
        } else {
            launch {
                state.loading = true
                when (val response =
                    repository.verifyOtpGeneric(
                        VerifyOtpGenericRequest(
                            action,
                            state.otp
                        )
                    )) {
                    is RetroApiResponse.Success -> {
                        nextButtonPressEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        state.loading = false
                    }
                }
                state.loading = false
            }
        }
    }

    override fun handlePressOnResendOTP(id: Int) {
        if(action==Constants.CHANGE_EMAIL){
            createOtp()
        }else if (action==Constants.CHANGE_MOBILE_NO){
            createOtpForPhoneNumber()
        }
    }



    private fun createOtp() {
        launch {
            state.loading = true
            when (val response =
                messagesRepository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        Constants.CHANGE_EMAIL
                    )
                )) {
                is RetroApiResponse.Success -> {
                    state.toast=getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    private fun createOtpForPhoneNumber() {

        launch {

            state.loading = true
            when (val response =
                messagesRepository.createOtpGenericWithPhone(phone = state.mobileNumber[0]!!.replace(" ", "").replace("+",""),createOtpGenericRequest = CreateOtpGenericRequest(Constants.CHANGE_MOBILE_NO))) {
                is RetroApiResponse.Success -> {
                    state.toast=getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }
}