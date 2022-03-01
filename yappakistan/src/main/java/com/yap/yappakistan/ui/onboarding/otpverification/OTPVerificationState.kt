package com.yap.yappakistan.ui.onboarding.otpverification

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState

class OTPVerificationState : BaseState(), IOTPVerification.State {
    override var isValid: MutableLiveData<Boolean> = MutableLiveData(false)
    override var timer: MutableLiveData<String> = MutableLiveData()
    override var validResend: MutableLiveData<Boolean> = MutableLiveData()
    override var formattedNumber: MutableLiveData<String> = MutableLiveData()
    override var color: MutableLiveData<Int> = MutableLiveData()
}