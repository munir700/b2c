package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IMobile {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnNext()
        fun onEditorActionListener(): TextView.OnEditorActionListener
    }

    interface State : IBase.State {
        var mobile: String
        var drawbleRight: Drawable?
        var mobileError: String
        var valid: Boolean
        var cursorPlacement: Boolean
        var refreshField: Boolean
        var setSelection: Int
        var handleBackPress: Int
        var inputText: SpannableStringBuilder
        var errorVisibility: String
    }
}