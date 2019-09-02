package co.yap.modules.store.interfaces

import co.yap.modules.store.models.YapStoreData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapStore {

    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var yapStoreData : MutableList<YapStoreData>
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
        var testName: String
    }
}