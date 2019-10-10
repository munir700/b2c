package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ConfirmNewCardPinViewModel
import co.yap.networking.admin.AdminRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class UpdateConfirmPasscodeViewModel(application: Application) :
    ConfirmNewCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val forgotPasscodeclickEvent: SingleClickEvent = SingleClickEvent()
    private val adminRepository: AdminRepository = AdminRepository
    override fun onCreate() {
        super.onCreate()
        state.titleSetPin = "Re-enter your new 4-digit \n passcode"
        state.buttonTitle = getString(Strings.screen_current_card_pin_display_button_next)
        state.forgotTextVisibility = true
    }

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
             if (state.newPin == state.pincode) {
                 //call service here
                 changePasscode(id)
             } else {
                 errorEvent.call()
             }
            //temporary
            //clickEvent.postValue(id)
        }
    }


    private fun changePasscode(id: Int){
        launch {
            state.loading = true
            when (val response=adminRepository.changePasscode(state.pincode)) {
                is RetroApiResponse.Success ->{
                    state.loading = false
                    clickEvent.postValue(id)
                }
                is RetroApiResponse.Error->{
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }
    override fun handlePressOnForgotPasscodeButton(id: Int) {
        forgotPasscodeclickEvent.postValue(id)
    }
}