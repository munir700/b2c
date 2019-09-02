package co.yap.modules.store.interfaces

import co.yap.modules.store.models.YapStoreData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapStoreDetail {

    interface View : IBase.View<ViewModel> {
        var testValue: String
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var yapStoreData: MutableList<YapStoreData>
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var title: String
        var subTitle: String
        var image: Int
        var storeIcon: Int

        var storeHeading: String
        var storeDetail: String
    }
}