package co.yap.modules.setcardpin.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISetCardPin {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>{
        val clickEvent: SingleClickEvent
        fun handlePressOnNextButton(id: Int)
    }

    interface State : IBase.State
}