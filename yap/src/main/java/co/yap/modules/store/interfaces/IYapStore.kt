package co.yap.modules.store.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapStore {

    interface State : IBase.State {
        var storesList: MutableList<Store>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var yapStoreData: MutableLiveData<MutableList<Store>>
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
        var testName: String
    }
}