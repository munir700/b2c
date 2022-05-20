package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IEmail {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleClickEvent
        val animationStartEvent: SingleLiveEvent<Boolean>
        fun handlePressOnNext()
        fun onEditorActionListener(): TextView.OnEditorActionListener
        fun postDemographicData()
        fun sendVerificationEmail()
        fun stopTimer()
        fun setFeatureFlagCall(email: String?, customerId: String?)
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
        var emailBtnTitle: String
        var deactivateField: Boolean
        var verificationCompleted: Boolean
        var isWaiting: Boolean
    }
}