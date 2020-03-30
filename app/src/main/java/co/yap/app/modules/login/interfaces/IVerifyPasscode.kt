package co.yap.app.modules.login.interfaces

import android.text.TextWatcher
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IVerifyPasscode {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnPressView(id: Int)
        fun login()
        fun createOtp()
        fun validateDevice()
        fun getAccountInfo()
        fun verifyPasscode()
        fun showAccountBlockedError()
        val onClickEvent: MutableLiveData<Int>
        val forgotPasscodeButtonPressEvent: SingleClickEvent
        val loginSuccess: SingleLiveEvent<Boolean>
        val accountInfo: MutableLiveData<AccountInfo>
        val validateDeviceResult: SingleLiveEvent<Boolean>
        val createOtpResult: SingleLiveEvent<Boolean>
        var isFingerprintLogin: Boolean
        var mobileNumber: String
        var EVENT_LOGOUT_SUCCESS: Int
    }

    interface State : IBase.State {
        var deviceId: String
        var username: String
        var dialerError: String
        var btnVerifyPassCodeText: String?
        var passcode: String
        var valid: Boolean
        fun getTextWatcher(): TextWatcher
        fun validationPasscode(passcodeText: String)
        var sequence: Boolean
        var similar: Boolean
        var verifyPassCodeEnum: String
        var isScreenLocked: ObservableField<Boolean>
        var isAccountLocked: ObservableField<Boolean>
    }
}