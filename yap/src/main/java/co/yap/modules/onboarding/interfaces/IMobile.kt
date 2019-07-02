package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase

interface IMobile {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnNext()
        fun validateMobileNumber(phoneNumber:String): Boolean?
    }

    interface State : IBase.State {
        var mobile: String
        var drawbleRight: Drawable?
        var mobileError: String
        var valid: Boolean
    }
}