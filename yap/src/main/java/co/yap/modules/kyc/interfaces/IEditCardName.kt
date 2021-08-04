package co.yap.modules.kyc.interfaces

import co.yap.yapcore.IBase

interface IEditCardName {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>
    interface View : IBase.View<ViewModel>
}