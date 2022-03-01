package com.yap.yappakistan.ui.onboarding.fullname

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState
import javax.inject.Inject

class NameVerificationState @Inject constructor() : BaseState(), INameVerification.State {
    override var firstName: MutableLiveData<String> = MutableLiveData("")
    override var lastName: MutableLiveData<String> = MutableLiveData("")
    override var isValid: MutableLiveData<Boolean> = MutableLiveData()
}