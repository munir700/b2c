package com.yap.yappakistan.ui.auth.forgotpasscode.verifyforgototp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yap.core.base.interfaces.IBase
import com.yap.core.utils.SingleEvent
import com.yap.yappakistan.networking.microservices.messages.requestdtos.ForgotPasscodeOtpRequest

interface IVerifyForgotOTP {
    interface View : IBase.View<ViewModel> {
        fun getFragmentArguments()
        fun onResendOTPClick()
        fun viewModelObservers()
        fun onOTPVerifyClick()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var mobileNumber: String
        var otp: String
        var optToken: String?
        val isForgotOtpCreated: LiveData<Boolean>
        val isForgotOtpVerified: LiveData<Boolean>
        val openCreateNewPasscode: LiveData<SingleEvent<Int>>
        fun reverseTimer(totalSeconds: Long)
        fun resendOtp(forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest)
        fun verifyOtp(forgotPasscodeOtpRequest: ForgotPasscodeOtpRequest)
        fun openCreateNewPasscodeScreen()
    }

    interface State : IBase.State {
        var isValid: MutableLiveData<Boolean>
        var timer: MutableLiveData<String>
        var validResend: MutableLiveData<Boolean>
        var formattedNumber: MutableLiveData<String>
        var color: MutableLiveData<Int>
    }
}