package co.yap.app.modules.login.viewmodels

import android.app.Application
import co.yap.app.modules.login.interfaces.IBiometricPermission
import co.yap.yapcore.BaseViewModel

class BiometricPermissionViewModel(application: Application) :BaseViewModel<IBiometricPermission.State>(application),IBiometricPermission.ViewModel {
    override val state: IBiometricPermission.State
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun checkFingerPrint() {

    }
}