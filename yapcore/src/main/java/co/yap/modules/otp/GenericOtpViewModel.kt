package co.yap.modules.otp

import android.app.Application
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils

class GenericOtpViewModel(application: Application) :
    BaseViewModel<IGenericOtp.State>(application = application), IGenericOtp.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    private val repository: MessagesRepository = MessagesRepository
    override var destination: String? = ""
    override var emailOtp: Boolean? = false
    override val state: GenericOtpState = GenericOtpState(application = application)

    override fun onCreate() {
        super.onCreate()
        when (state.otpDataModel?.otpAction) {
            Constants.CHANGE_EMAIL -> {
                state.verificationTitle =
                    getString(Strings.screen_email_verification_display_text_heading)
                state.verificationDescription =
                    Strings.screen_verify_phone_number_display_text_sub_title
            }
            Constants.FORGOT_CARD_PIN_ACTION -> {
                state.verificationTitle =
                    getString(Strings.screen_forgot_pin_display_text_heading)
                state.verificationDescription =
                    Strings.screen_verify_phone_number_display_text_sub_title
            }

            else -> {
                state.verificationTitle =
                    getString(Strings.screen_forgot_passcode_otp_display_text_heading)
                state.verificationDescription =
                    Strings.screen_verify_phone_number_display_text_sub_title

            }
        }
    }

    override fun handlePressOnButtonClick(id: Int) {
        if (id == R.id.btnDone) {
            verifyOtp(id)
        } else if (id == R.id.btnResend) {
            if (state.otpDataModel?.otpAction == Constants.CHANGE_MOBILE_NO) {
                createOtpForPhoneNumber()
            } else {
                createOtp(true)
            }
        }
    }

    private fun verifyOtp(id: Int) {

        if (state.otpDataModel?.otpAction == Constants.CHANGE_MOBILE_NO) {
            launch {
                state.loading = true
                when (val response =
                    repository.verifyOtpGenericWithPhone(
                        state.mobileNumber[0]!!.replace(" ", "").replace("+", "00"),
                        VerifyOtpGenericRequest(state.otpDataModel?.otpAction ?: "", state.otp)
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        clickEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.errorMessage = response.error.message
                        errorEvent.call()
//                        state.toast = response.error.message
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
                            state.otpDataModel?.otpAction ?: "",
                            state.otp
                        )
                    )) {
                    is RetroApiResponse.Success -> {
                        clickEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.errorMessage = response.error.message
                        errorEvent.call()
//                        state.toast = response.error.message
                        state.loading = false
                    }
                }
                state.loading = false
            }
        }
    }

    override fun createOtp(resend: Boolean) {
        launch {
            state.loading = true
            when (val response =
                repository.createOtpGeneric(
                    createOtpGenericRequest = CreateOtpGenericRequest(
                        state.otpDataModel?.otpAction ?: ""
                    )
                )) {
                is RetroApiResponse.Success -> {
                    if (resend) {
                        state.toast =
                            getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    }
                    state.reverseTimer(10)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    state.errorMessage = response.error.message
                    errorEvent.call()

                    //state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun initializeData() {
        createOtp()
        state.otpDataModel?.mobileNumber?.let {
            if (it.startsWith("00")) {
                state.mobileNumber[0] =
                    it.replaceRange(
                        0,
                        2,
                        "+"
                    )
            } else if (it.startsWith("+")) {
                state.mobileNumber[0] =
                    Utils.getFormattedPhone(it)
            } else {
                state.mobileNumber[0] =
                    Utils.formatePhoneWithPlus(it)
            }
        }
    }

    private fun createOtpForPhoneNumber() {

        launch {
            state.loading = true
            when (val response =
                repository.createOtpGenericWithPhone(
                    phone = state.mobileNumber[0]!!.replace(
                        " ",
                        ""
                    ).replace("+", "00"),
                    createOtpGenericRequest = CreateOtpGenericRequest(Constants.CHANGE_MOBILE_NO)
                )) {
                is RetroApiResponse.Success -> {
                    state.toast =
                        getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    state.errorMessage = response.error.message
                    errorEvent.call()
//                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

}
