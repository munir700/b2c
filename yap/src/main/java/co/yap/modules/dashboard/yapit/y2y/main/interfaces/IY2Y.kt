package co.yap.modules.dashboard.yapit.y2y.main.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IY2Y {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var rightButtonVisibility: Int
        var leftButtonVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton(id: Int)
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        val yapContactLiveData: MutableLiveData<List<Contact>>

    }

    interface View : IBase.View<ViewModel>
}