package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

interface IPhoneContact {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun getState(): LiveData<PagingState>
        val phoneContactLiveData: LiveData<PagedList<Contact>>
        fun handlePressOnView(id: Int)
        fun listIsEmpty(): Boolean
        fun retry()
    }

    interface State : IBase.State {

    }
}