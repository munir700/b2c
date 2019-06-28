package co.yap.app.modules.login.interfaces

import co.yap.yapcore.IBase

interface IBiometricPermission {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun checkFingerPrint()
    }

    interface State : IBase.State
}