package com.yap.yappakistan.ui.onboarding.signupcompleted

import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseState
import javax.inject.Inject

class SignupCompletedState @Inject constructor() : BaseState(), ISignupCompleted.State {
    override var name: MutableLiveData<String> = MutableLiveData()
    override var description: MutableLiveData<CharSequence> = MutableLiveData()
    override val isWaiting: MutableLiveData<Boolean> = MutableLiveData()
    override val ibanNumber: MutableLiveData<String> = MutableLiveData()
}