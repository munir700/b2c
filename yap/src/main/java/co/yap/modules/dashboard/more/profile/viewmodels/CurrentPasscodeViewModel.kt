package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.app.login.EncryptionUtils
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ChangeCardPinViewModel
import co.yap.networking.admin.AdminRepository.verifyUsername
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateForgotPasscodeOtpRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import java.util.regex.Pattern

class CurrentPasscodeViewModel(application: Application): ChangeCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val forgotPasscodeclickEvent: SingleClickEvent= SingleClickEvent()
    private val messagesRepository: MessagesRepository = MessagesRepository
    override var emailOtp: Boolean = false
    override var mobileNumber:String=""

    override fun onCreate() {
        super.onCreate()
        state.titleSetPin="Enter your current 4-digit \n passcode"
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        state.forgotTextVisibility=true
    }
    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            clickEvent.setValue(id)
        }
    }
    override fun handlePressOnForgotPasscodeButton(id: Int) {
        val sharedPreferenceManager = SharedPreferenceManager(context)
        var username = ""
            username = EncryptionUtils.decrypt(
                context,
                sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
            )!!
        launch {
            state.loading = true
            when (val response=messagesRepository.createForgotPasscodeOTP(CreateForgotPasscodeOtpRequest(
                verifyUsername(username),emailOtp)
            )) {
                is RetroApiResponse.Success ->{
                    mobileNumber=response.data.data
                    state.loading = false
                    forgotPasscodeclickEvent.postValue(id)
                }
                is RetroApiResponse.Error->{
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }
    private fun verifyUsername(enteredUsername: String): String {
        var username = enteredUsername
        if (isUsernameNumeric(username)) {
            emailOtp=false
            if (username.startsWith("+")) {
                username = username.replace("+", "00")
                return username
            } else if (username.startsWith("00")) {
                return username
            } else if (username.startsWith("0")) {
                username = username.substring(1, username.length)
                return username
            } else {
                return username
            }
        } else {
            emailOtp=true
            return username
        }
    }

    private fun isUsernameNumeric(username: String): Boolean {
        val inputStr: CharSequence
        var isValid = false
        val expression = "^[0-9+]*\$"

        inputStr = username
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }
}