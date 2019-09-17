package co.yap.modules.store.interfaces

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

interface IYapStore {

    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val storesLiveData: LiveData<PagedList<Store>>
        fun handlePressOnView(id: Int)
        fun getState(): LiveData<PagingState>
        fun listIsEmpty(): Boolean
        fun retry()
    }

    interface View : IBase.View<ViewModel>
}