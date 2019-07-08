package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IEmail {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnNext()
    }

    interface State : IBase.State {
        var email: String
        var drawbleRight: Drawable?
        var emailError: String
        var emailHint: String
        var valid: Boolean

        //textwatcher
        var cursorPlacement: Boolean
        var refreshField: Boolean
        var setSelection: Int
        var handleBackPress: Int
        var twoWayTextWatcher: String
        var emailTitle: String
        var emailVerificationTitle: String
    }
}