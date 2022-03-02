package com.yap.yappakistan.ui.auth.loginpasscode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yap.core.base.interfaces.IBase
import com.yap.yappakistan.SharedPreferenceManager
import com.yap.core.utils.SingleEvent
import com.yap.yappakistan.SessionManager
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.LoginRequest
import com.yap.yappakistan.networking.microservices.customers.responsedtos.accountinfo.AccountInfo
import com.yap.yappakistan.networking.microservices.messages.requestdtos.ForgotPasscodeOtpRequest

interface ILoginPassCode {
    interface View : IBase.View<ViewModel> {
        fun onClickLogin()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun login(loginRequest: LoginRequest)
        fun logout(accountUUID: String, success: () -> Unit)
        fun getClientSecret(): String
        fun isBiometricLoginEnabled(isBiometricAvailable: Boolean): Boolean
        fun openWaitingListScreen()
        fun openDashboardScreen()
        fun openOTPVerificationScreen()
        fun openForgotOTPVerificationScreen()
        fun blockAccount(errorMessage: String)
        fun createForgotOtp(forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest)
        val sharedPreferenceManager: SharedPreferenceManager
        val sessionManager: SessionManager
        val isDeviceValidate: LiveData<Boolean>
        val openPermissionSettings: LiveData<SingleEvent<Int>>
        val openWaitingList: LiveData<SingleEvent<Int>>
        val openAllowedUser: LiveData<SingleEvent<Int>>
        val openDashboard: LiveData<SingleEvent<AccountInfo?>>
        val openOTPVerification: LiveData<SingleEvent<Int>>
        val openForgotOTPVerification: LiveData<SingleEvent<Int>>
        val isForgotOtpCreated: LiveData<Boolean>
    }

    interface State : IBase.State {
        var deviceId: String
        var valid: MutableLiveData<Boolean>
        var passcode: MutableLiveData<String>
        var passcodeError: MutableLiveData<String>
        var biometricEnable: MutableLiveData<Boolean>
        val isScreenLocked: MutableLiveData<Boolean>
        val isAccountLocked: MutableLiveData<Boolean>
    }
}