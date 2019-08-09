package co.yap.app.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.app.modules.forgotpasscode.states.ForgotPasscodeOtpState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.VerifyForgotPasscodeOtpRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class ForgotPasscodeOtpViewModel(application: Application) : BaseViewModel<IForgotPasscodeOtp.State>(application),
    IForgotPasscodeOtp.ViewModel, IRepositoryHolder<MessagesRepository> {

    override val state: ForgotPasscodeOtpState = ForgotPasscodeOtpState(application)
    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val repository: MessagesRepository = MessagesRepository
    //override var mobileNumber: String = "scsd"
    override var destination: String = ""
    override var emailOtp: Boolean = false


    override fun onCreate() {
        super.onCreate()
        state.verificationTitle =getString(Strings.screen_forgot_passcode_otp_display_text_heading)
        state.verificationDescription = Strings.screen_forgot_passcode_otp_display_text_sub_heading
        //state.mobileNumber[0] = "jhv"

        state.reverseTimer(10)
        state.validResend = false
    }

    override fun handlePressOnSendButton(id: Int) {
        verifyOtp(id)
    }

    override fun handlePressOnResendOTP(id: Int) {
        launch {
            state.loading = true
            when (val response=repository.createForgotPasscodeOTP(CreateForgotPasscodeOtpRequest(destination,emailOtp))) {
                is RetroApiResponse.Success ->{
                    state.toast=getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                    state.reverseTimer(10)
                    state.validResend = false
                    state.loading = false
                }
                is RetroApiResponse.Error->{
                    state.toast = response.error.message
                    state.loading = false
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
                repository.verifyForgotPasscodeOtp(VerifyForgotPasscodeOtpRequest(destination, state.otp, emailOtp))) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.setValue(id)
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}