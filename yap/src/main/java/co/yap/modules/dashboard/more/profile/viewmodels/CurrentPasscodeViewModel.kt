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

class CurrentPasscodeViewModel(application: Application): ChangeCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val forgotPasscodeclickEvent: SingleClickEvent= SingleClickEvent()
    private val messagesRepository: MessagesRepository = MessagesRepository

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
        forgotPasscodeclickEvent.postValue(id)
        /*var sharedPreferenceManager: SharedPreferenceManager = SharedPreferenceManager(context)
        var username : String = ""
        if (!sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, false)) {
            username = "userName"
//            username = state.username
        } else {
            username = EncryptionUtils.decrypt(
                context,
                sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
            )!!
        }


        launch {
            state.loading = true
            when (val response=messagesRepository.createForgotPasscodeOTP(CreateForgotPasscodeOtpRequest(
                verifyUsername(username).toString(),false)
            )) {
                is RetroApiResponse.Success ->{

                   // mobileNumber=response.data.data
                    state.loading = false
                   // forgotPasscodeButtonPressEvent.setValue(id)
                }
                is RetroApiResponse.Error->{
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }*/

    }

}