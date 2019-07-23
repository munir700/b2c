package co.yap.modules.kyc.interfaces

import co.yap.yapcore.IBase

interface IKYCHome {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>
    interface View : IBase.View<ViewModel>
}