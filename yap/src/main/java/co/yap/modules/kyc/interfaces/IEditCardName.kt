package co.yap.modules.kyc.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEditCardName {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
        fun handleOnPressView(id: Int)
        var clickEvent: SingleClickEvent
    }
    interface View : IBase.View<ViewModel>
}