package com.yap.yappakistan.ui.auth.otpverification

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState
import javax.inject.Inject

class LoginOTPVerificationState @Inject constructor() : BaseState(), ILoginOTPVerification.State {
    override var isValid: MutableLiveData<Boolean> = MutableLiveData(false)
    override var timer: MutableLiveData<String> = MutableLiveData()
    override var validResend: MutableLiveData<Boolean> = MutableLiveData()
    override var formattedNumber: MutableLiveData<String> = MutableLiveData()
    override var color: MutableLiveData<Int> = MutableLiveData()
}