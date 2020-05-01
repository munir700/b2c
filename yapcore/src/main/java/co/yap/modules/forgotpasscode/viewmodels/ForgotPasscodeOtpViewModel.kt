package co.yap.modules.forgotpasscode.viewmodels

import android.app.Application
import android.content.Context
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.modules.forgotpasscode.states.ForgotPasscodeOtpState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.VerifyForgotPasscodeOtpRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.getColors

open class ForgotPasscodeOtpViewModel(application: Application) :
    BaseViewModel<IForgotPasscodeOtp.State>(application),
    IForgotPasscodeOtp.ViewModel, IRepositoryHolder<MessagesRepository> {

    override val state: ForgotPasscodeOtpState =
        ForgotPasscodeOtpState(application)
    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val repository: MessagesRepository = MessagesRepository
    //override var mobileNumber: String = "scsd"
    override var destination: String? = ""
    override var emailOtp: Boolean? = false
    override var action: String? = ""
    override var token: String? = ""

    override fun onCreate() {
        super.onCreate()
        state.verificationTitle = getString(Strings.screen_forgot_passcode_otp_display_text_heading)
        state.verificationDescription = Strings.screen_verify_phone_number_display_text_sub_title
        //state.mobileNumber[0] = "jhv"
        state.validResend = false
    }

    override fun handlePressOnSendButton(id: Int) {
        verifyOtp(id)
    }

    override fun handlePressOnResendOTP(context: Context) {
        launch {
            state.loading = true
            when (val response = repository.createForgotPasscodeOTP(
                CreateForgotPasscodeOtpRequest(
                    destination.toString(),
                    emailOtp ?: false
                )
            )) {
                is RetroApiResponse.Success -> {
                    state.toast =
                        getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10, context)
                    state.validResend = false
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                    otpUiBlocked(response.error.actualCode)
                }
            }
        }
    }

    override fun setPasscode(passcode: String) {
    }

    private fun verifyOtp(id: Int) {
        launch {
            state.loading = true
            when (val response =
                repository.verifyForgotPasscodeOtp(
                    VerifyForgotPasscodeOtpRequest(
                        destination.toString(),
                        state.otp,
                        emailOtp ?: false
                    )
                )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.setValue(id)
                    token = response.data.token
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.otp = ""
                    otpUiBlocked(response.error.actualCode)
                }
            }
            state.loading = false
        }
    }

    private fun otpUiBlocked(errorCode: String) {
        when (errorCode) {
            "1095" -> {
                state.validResend = false
//                state.valid = false
                state.color = context.getColors(R.color.disabled)
                state.isOtpBlocked.set(false)
            }
        }
    }
}