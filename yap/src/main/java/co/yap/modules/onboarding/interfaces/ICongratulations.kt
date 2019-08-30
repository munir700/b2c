package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICongratulations {

    interface View : IBase.View<ViewModel>{
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var elapsedOnboardingTime: Long
        fun handlePressOnCompleteVerification(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        val nameList: Array<String?>
        // var name: String
        var ibanNumber: String
        var onboardingTime: String
    }
}