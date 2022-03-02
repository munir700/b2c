package com.yap.yappakistan.ui.onboarding.passcode

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState

class PasscodeState : BaseState(), IPassCode.State {
    override var passcode: MutableLiveData<String> = MutableLiveData()
    override var valid: MutableLiveData<Boolean> = MutableLiveData()
    override var passcodeError: MutableLiveData<String> = MutableLiveData()
}