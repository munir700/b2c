package co.yap.modules.otp

import android.app.Application
import android.content.Context
import android.text.SpannableStringBuilder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.messages.requestdtos.VerifyForgotPasscodeOtpRequest
import co.yap.networking.messages.requestdtos.VerifyOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getColors
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.managers.MyUserManager

class GenericOtpViewModel(application: Application) :
    BaseViewModel<IGenericOtp.State>(application = application), IGenericOtp.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val errorEvent: SingleClickEvent = SingleClickEvent()
    private val repository: MessagesRepository = MessagesRepository
    override var token: String? = ""
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
            OTPActions.FORGOT_PASS_CODE.name -> {
                state.verificationTitle =
                    getString(Strings.screen_forgot_passcode_otp_display_text_heading)
                state.verificationDescription =
                    Strings.screen_forgot_passcode_otp_display_text_sub_heading


            }
            OTPActions.DOMESTIC_TRANSFER.name, OTPActions.UAEFTS.name, OTPActions.SWIFT.name, OTPActions.RMT.name, OTPActions.CASHPAYOUT.name, OTPActions.Y2Y.name -> {
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
        when (state.otpDataModel?.otpAction) {
            OTPActions.CHANGE_MOBILE_NO.name -> createOtpForPhoneNumber(true, context)
            OTPActions.FORGOT_PASS_CODE.name -> createForgotPassCodeOtpRequest(true, context)
            else -> createOtp(true, context)
        }
    }

    private fun verifyOtp(id: Int) {
        if (state.otpDataModel?.otpAction == OTPActions.CHANGE_MOBILE_NO.name) {
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
                        state.otp = ""
                        otpUiBlocked(response.error.actualCode)
                        //errorEvent.call()
                        state.loading = false
                    }
                }
                state.loading = false
            }
        } else if (state.otpDataModel?.otpAction == OTPActions.FORGOT_PASS_CODE.name) {
            verifyForgotPassCodeOtp(id)
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
                        token = response.data.token.toString()
                        clickEvent.setValue(id)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        state.otp = ""
                        otpUiBlocked(response.error.actualCode)
                        // errorEvent.call()
                        state.loading = false
                    }
                }
                state.loading = false
            }
        }
    }

    private fun verifyForgotPassCodeOtp(id: Int) {
        launch {
            state.loading = true
            when (val response =
                repository.verifyForgotPasscodeOtp(
                    VerifyForgotPasscodeOtpRequest(
                        state.otpDataModel?.username.toString(),
                        state.otp,
                        state.otpDataModel?.emailOtp ?: false
                    )
                )) {
                is RetroApiResponse.Success -> {
                    token = response.data.token
                    clickEvent.setValue(id)
                }
                is RetroApiResponse.Error -> {
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    state.otp = ""
                    otpUiBlocked(response.error.actualCode)
                }
            }
            state.loading = false
        }
    }

    private fun getUserName(): String? {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        return if (!SharedPreferenceManager(context).getValueBoolien(
                Constants.KEY_IS_USER_LOGGED_IN,
                false
            )
        ) {
            state.otpDataModel?.username
        } else {
            sharedPreferenceManager.getDecryptedUserName()
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

    private fun createForgotPassCodeOtpRequest(
        resend: Boolean,
        context: Context
    ) {
        val username = getUserName()
        username?.let {
            launch {
                state.loading = true
                when (val response = repository.createForgotPasscodeOTP(
                    CreateForgotPasscodeOtpRequest(
                        Utils.verifyUsername(username),
                        !Utils.isUsernameNumeric(username)
                    )
                )) {
                    is RetroApiResponse.Success -> {
                        response.data.data?.let {
                            state.otpDataModel?.mobileNumber = it
                            state.mobileNumber[0] = getFormattedPhoneNo(it)
                            state.verificationDescriptionForLogo = SpannableStringBuilder(
                                Translator.getString(
                                    context,
                                    Strings.screen_forgot_passcode_otp_display_text_sub_heading
                                ).format(state.mobileNumber[0])
                            )

                        }
                        if (resend) {
                            state.toast =
                                getString(Strings.screen_verify_phone_number_display_text_resend_otp_success)
                        }
                        state.reverseTimer(10, context)
                        state.validResend = false

                    }
                    is RetroApiResponse.Error -> {
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        state.loading = false
                    }
                }
                state.loading = false
            }
        } ?: toast(context, "Invalid user name")
    }

    override fun initializeData(context: Context) {
        when (state.otpDataModel?.otpAction) {
            OTPActions.CHANGE_MOBILE_NO.name -> createOtpForPhoneNumber(false, context)
            OTPActions.FORGOT_PASS_CODE.name -> createForgotPassCodeOtpRequest(false, context)
            else -> createOtp(false, context)
        }
        state.otpDataModel?.mobileNumber?.let {
            state.mobileNumber[0] = getFormattedPhoneNo(it)
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
                MyUserManager.getAccountInfo()
            }
        }
    }

    private fun getFormattedPhoneNo(mobileNumber: String): String {
        return when {
            mobileNumber.startsWith("00") ->
                mobileNumber.replaceRange(
                    0,
                    2,
                    "+"
                )
            mobileNumber.startsWith("+") -> Utils.getFormattedPhone(mobileNumber)
            else -> Utils.formatePhoneWithPlus(mobileNumber)
        }
    }

}
