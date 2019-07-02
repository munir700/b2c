package co.yap.app.modules.login.interfaces

import android.graphics.drawable.Drawable
import co.yap.app.modules.login.models.SystemPermissionsContent
import co.yap.yapcore.IBase

interface IBiometricPermission {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        var screenType: String
        fun checkFingerPrint()
        fun setViews():SystemPermissionsContent

    }

    interface State : IBase.State {
        var icon: Drawable?
        var title: String
        var termsAndConditionsVisibility: Boolean
        var buttonTitle: String
    }
}