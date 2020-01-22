package co.yap.app.modules.login.interfaces

import android.text.TextWatcher
import co.yap.app.modules.login.fragments.VerifyPassCodeEnum
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IVerifyPasscode {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnSignInButton()
        fun handlePressOnForgotPasscodeButton(id: Int)
        fun login()
        fun createOtp()
        fun validateDevice()
        val signInButtonPressEvent: SingleLiveEvent<Boolean>
        val forgotPasscodeButtonPressEvent: SingleClickEvent
        val loginSuccess: SingleLiveEvent<Boolean>
        val validateDeviceResult: SingleLiveEvent<Boolean>
        val createOtpResult: SingleLiveEvent<Boolean>
        var isFingerprintLogin: Boolean
        var mobileNumber: String
        var emailOtp: Boolean
    }

    interface State : IBase.State {
        var deviceId: String
        var username: String
        var dialerError: String
        var passcode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        fun validationPasscode(passcodeText: String)
        var sequence: Boolean
        var similar: Boolean
        var verifyPassCodeEnum: String
    }
}