package com.yap.yappakistan.ui.onboarding.signupcompleted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseViewModel
import com.yap.core.utils.SingleEvent
import com.yap.yappakistan.ui.onboarding.signupcompleted.ISignupCompleted
import com.yap.yappakistan.ui.onboarding.signupcompleted.SignupCompletedState
import com.yap.yappakistan.R
import com.yap.yappakistan.SessionManager
import com.yap.yappakistan.di.ResourcesProviders
import com.yap.yappakistan.utils.maskIbanNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupCompletedVM @Inject constructor(
    override val viewState: SignupCompletedState,
    var resourcesProviders: ResourcesProviders,
    override val sessionManager: SessionManager
) : BaseViewModel<ISignupCompleted.State>(), ISignupCompleted.ViewModel {
    override var elapsedOnboardingTime: Long = 0
    override var startTime: Long = 0

    private val _openKycDashboard: MutableLiveData<SingleEvent<Any>> = MutableLiveData()
    override val openKycDashboard: LiveData<SingleEvent<Any>> = _openKycDashboard

    private val _openWaitingList: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    override val openWaitingList: LiveData<SingleEvent<Int>> = _openWaitingList

    override fun onCreate() {
        super.onCreate()
        if (!sessionManager.userAccount.value?.iban.isNullOrBlank())
            viewState.ibanNumber.value = sessionManager.userAccount.value?.iban.maskIbanNumber()
    }

    override fun navigate() {
        if (viewState.isWaiting.value == true)
            openWaitingList()
        else
            openDashboard()
    }

    override fun openDashboard() {
        _openKycDashboard.value = SingleEvent(-1)
    }

    override fun openWaitingList() {
        _openWaitingList.value =
            SingleEvent(R.id.action_signupCompletedFragment_to_waitingListFragment)
    }
}