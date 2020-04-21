package co.yap.modules.otp

import android.app.Application
import android.content.Context
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getColors
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

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
            OTPActions.CHANGE_EMAIL.name -> {
                state.verificationTitle =
                    getString(Strings.screen_email_verification_display_text_heading)
                state.verificationDescription =
                    Strings.screen_verify_phone_number_display_text_sub_title
            }
            OTPActions.FORGOT_CARD_PIN.name -> {
                state.verificationTitle =
                    getString(Strings.screen_forgot_pin_display_text_heading)
                state.verificationDescription =
                    Strings.screen_verify_phone_number_display_text_sub_title
            }
            OTPActions.DOMESTIC_TRANSFER.name, OTPActions.UAEFTS.name, OTPActions.SWIFT.name, OTPActions.RMT.name, OTPActions.CASHPAYOUT.name,OTPActions.Y2Y.name -> {
                state.verificationTitle =
                    state.otpDataModel?.username ?: ""
                state.verificationDescription =
                    Strings.screen_verify_phone_number_display_text_sub_title
                val descriptionString =
                    getString(Strings.screen_cash_pickup_funds_display_otp_text_description).format(
                        state.currencyType,
                        state.otpDataModel?.amount?.toFormattedCurrency(),
                        state.otpDataModel?.username
                    )
                state.verificationDescriptionForLogo =
                    Utils.getSppnableStringForAmount(
                        context,
                        descriptionString,
                        state.currencyType ?: "",
                        Utils.getFormattedCurrencyWithoutComma(state.otpDataModel?.amount)
                    )
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
        verifyOtp(id)
    }

    override fun handlePressOnResendClick(context: Context) {
        if (state.otpDataModel?.otpAction == Constants.CHANGE_MOBILE_NO) {
            createOtpForPhoneNumber(true, context)
        } else {
            createOtp(true, context)
        }
    }

    private fun verifyOtp(id: Int) {
        if (state.otpDataModel?.otpAction == Constants.CHANGE_MOBILE_NO) {
            launch {
                state.loading = true
                when (val response =
                    repository.verifyOtpGenericWithPhone(
                        state.mobileNumber[0]?.replace(" ", "")?.replace("+", "00") ?: "",
                        VerifyOtpGenericRequest(state.otpDataModel?.otpAction ?: "", state.otp)
                    )
                    ) {
                    is RetroApiResponse.Success -> {
                        clickEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        state.otp=""
                        otpUiBlocked(response.error.actualCode)
                        //errorEvent.call()
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
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        state.otp=""
                        otpUiBlocked(response.error.actualCode)
                       // errorEvent.call()
                        state.loading = false
                    }
                }
                state.loading = false
            }
        }
    }

    override fun createOtp(resend: Boolean, context: Context) {
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
                    state.reverseTimer(10, context)
                    state.validResend = false
                }
                is RetroApiResponse.Error -> {
                    otpUiBlocked(response.error.actualCode)
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.loading = false
                }
            }
            state.loading = false
        }
    }

    override fun initializeData(context: Context) {
        if (state.otpDataModel?.otpAction == OTPActions.CHANGE_MOBILE_NO.name) {
            createOtpForPhoneNumber(false, context)
        } else {
            createOtp(context = context)
        }

        state.otpDataModel?.mobileNumber?.let {
            when {
                it.startsWith("00") -> state.mobileNumber[0] =
                    it.replaceRange(
                        0,
                        2,
                        "+"
                    )
                it.startsWith("+") -> state.mobileNumber[0] =
                    Utils.getFormattedPhone(it)
                else -> state.mobileNumber[0] =
                    Utils.formatePhoneWithPlus(it)
            }
        }
    }

    private fun createOtpForPhoneNumber(resend: Boolean, context: Context) {
        launch {
            state.loading = true
            when (val response =
                repository.createOtpGenericWithPhone(
                    phone = state.mobileNumber[0]?.replace(
                        " ",
                        ""
                    )?.replace("+", "00") ?: "",
                    createOtpGenericRequest = CreateOtpGenericRequest(Constants.CHANGE_MOBILE_NO)
                )) {
                is RetroApiResponse.Success -> {
                    if (resend)
                        state.toast =
                            getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)

                    state.reverseTimer(10, context)
                    state.validResend = false

                }

                is RetroApiResponse.Error -> {
                    state.errorMessage = response.error.message
                    errorEvent.call()
                    state.loading = false
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
//                state.valid = false // to disable confirm button
                state.color = context.getColors(R.color.disabled)
                state.isOtpBlocked.set(false)
            }
        }
    }
}
