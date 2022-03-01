package com.yap.yappakistan.ui.onboarding.email

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState
import javax.inject.Inject

class EmailVerificationState @Inject constructor() : BaseState(), IEmailVerification.State {
    override var email: MutableLiveData<String> = MutableLiveData()
    override var isValid: MutableLiveData<Boolean> = MutableLiveData()
}