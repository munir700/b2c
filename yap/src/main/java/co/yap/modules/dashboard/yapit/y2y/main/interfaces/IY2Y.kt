package co.yap.modules.dashboard.yapit.y2y.main.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase

interface IY2Y {

    interface State : IBase.State {
        var tootlBarVisibility: Int
        var rightButtonVisibility: Boolean
        var leftButtonVisibility: Boolean
        var rightIcon: Int
        var fromQR: ObservableBoolean?
    }

    interface ViewModel : IBase.ViewModel<State> {

        val yapContactLiveData: MutableLiveData<List<Contact>>
        val isSearching: MutableLiveData<Boolean>
        val searchQuery: MutableLiveData<String>
        var errorEvent: MutableLiveData<String>
        var beneficiary: Beneficiary?
        var position: Int
    }

    interface View : IBase.View<ViewModel>
}