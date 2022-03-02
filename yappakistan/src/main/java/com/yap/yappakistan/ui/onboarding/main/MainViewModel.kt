package com.yap.yappakistan.ui.onboarding.main

import androidx.hilt.lifecycle.ViewModelInject
import com.yap.core.base.BaseViewModel
import com.yap.core.base.SingleClickEvent
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.SignUpRequest

class MainViewModel @ViewModelInject constructor() : BaseViewModel<IMain.State>(), IMain.ViewModel {
    override val viewState: IMain.State = MainState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val signupData: SignUpRequest = SignUpRequest()

    override fun setProgress(percent: Int) {
        viewState.currentProgress.set(percent)
    }

    override fun setProgressVisibility(visible: Boolean) {
        viewState.toolsBarVisibility.set(visible)
    }


}