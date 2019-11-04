package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITransferType {

    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ITransferType.State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnType(id: Int)
    }

    interface View : IBase.View<co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.ITransferType.ViewModel>
}