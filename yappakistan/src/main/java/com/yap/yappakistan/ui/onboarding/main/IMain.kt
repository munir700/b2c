package com.yap.yappakistan.ui.onboarding.main

import androidx.databinding.ObservableInt
import com.yap.core.base.interfaces.IBase
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.SignUpRequest
import java.util.*

interface IMain {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val signupData: SignUpRequest
        fun setProgress(percent: Int)
        fun setProgressVisibility(visible: Boolean)
    }

    interface State : IBase.State {
        var startTime: Date?
        var elapsedOnboardingTime: Long
        var totalProgress: ObservableInt
        var currentProgress: ObservableInt
    }
}