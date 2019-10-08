package co.yap.modules.generic_otp

import android.app.Application
import co.yap.modules.forgotpasscode.viewmodels.ForgotPasscodeOtpViewModel
import co.yap.yapcore.SingleClickEvent

class GenericOtpViewModel(application: Application) : ForgotPasscodeOtpViewModel(application) {

    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()


    override fun handlePressOnSendButton(id: Int) {
        nextButtonPressEvent.setValue(id)
//        verifyOtp(id)
    }

}