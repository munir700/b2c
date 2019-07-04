package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import co.yap.yapcore.IBase

interface IMobile {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnNext()
    }

    interface State : IBase.State {
        var mobile: String
        var drawbleRight: Drawable?
        var mobileError: String
        var valid: Boolean
        //textwatcher
        var cursorPlacement: Boolean
        var refreshField: Boolean
        var setSelection: Int
        var handleBackPress: Int
        var inputText: SpannableStringBuilder
    }
}