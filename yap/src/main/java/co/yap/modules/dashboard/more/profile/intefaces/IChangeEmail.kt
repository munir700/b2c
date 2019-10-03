package co.yap.modules.dashboard.more.profile.intefaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase

interface IChangeEmail {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun onHandlePressOnNextButton()
    }

    interface State : IBase.State {
        var newEmail: String
        var newConfirmEMail: String
        var background: Drawable?
        var drawableRight:Drawable?


        //error
        var errorMessage: String
        var valid: Boolean
    }
}