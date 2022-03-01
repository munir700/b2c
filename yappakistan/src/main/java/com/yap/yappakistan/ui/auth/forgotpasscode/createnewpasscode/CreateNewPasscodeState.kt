package com.yap.yappakistan.ui.auth.forgotpasscode.createnewpasscode

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState
import com.yap.yappakistan.ui.auth.forgotpasscode.createnewpasscode.ICreateNewPasscode
import javax.inject.Inject

class CreateNewPasscodeState @Inject constructor() : BaseState(), ICreateNewPasscode.State {
    override var passcode: MutableLiveData<String> = MutableLiveData()
    override var valid: MutableLiveData<Boolean> = MutableLiveData()
    override var passcodeError: MutableLiveData<String> = MutableLiveData()
}
