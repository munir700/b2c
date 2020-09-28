package co.yap.modules.dashboard.yapit.y2y.main.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IY2Y {

    interface State : IBase.State {
        var tootlBarVisibility: Int
        var rightButtonVisibility: Boolean
        var leftButtonVisibility: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {

        val yapContactLiveData: MutableLiveData<List<Contact>>
        val isSearching: MutableLiveData<Boolean>
        val searchQuery: MutableLiveData<String>
        var errorEvent: MutableLiveData<String>
    }

    interface View : IBase.View<ViewModel>
}