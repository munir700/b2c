package co.yap.modules.others.unverifiedemail

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IUnverifiedEmail {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>
}