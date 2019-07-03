package co.yap.app.modules.login.states

import android.graphics.drawable.Drawable
import co.yap.BR
import co.yap.app.modules.login.interfaces.IBiometricPermission
import co.yap.yapcore.BaseState

class BiometricPermissionState(
    override var title: String,
    override var termsAndConditionsVisibility: Boolean,
    override var buttonTitle: String
) :BaseState(),IBiometricPermission.State {


    override var icon : Drawable? = null
    set(value)  {
        field=value
        notifyPropertyChanged(BR.emailError)
    }

}
