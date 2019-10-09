package co.yap.modules.generic_otp

import android.app.Application
import co.yap.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants

class GenericOtpViewModel(application: Application) : ForgotPasscodeOtpViewModel(application) {

    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val repository: MessagesRepository = MessagesRepository
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
                        state.mobileNumber[0]!!, VerifyOtpGenericRequest(action, state.otp)
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
}