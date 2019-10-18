package co.yap.modules.yapit.y2ytransfer.home.interfaces

import co.yap.modules.store.interfaces.IYapStoreDetail
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapToYap {
    interface View : IBase.View<IYapStoreDetail.ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State
}