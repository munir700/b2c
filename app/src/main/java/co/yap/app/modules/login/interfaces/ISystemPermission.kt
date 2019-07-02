package co.yap.app.modules.login.interfaces

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import co.yap.app.modules.login.models.SystemPermissionsContent
import co.yap.yapcore.IBase

interface ISystemPermission {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        var screenType: String
        fun checkFingerPrint()
        fun setViews()

    }

    interface State : IBase.State {
        var icon: Bitmap?
        var title: String
        var termsAndConditionsVisibility: Boolean
        var buttonTitle: String
    }
}