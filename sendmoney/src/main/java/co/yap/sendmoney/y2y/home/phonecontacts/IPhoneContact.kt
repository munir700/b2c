package co.yap.sendmoney.y2y.home.phonecontacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.Contact
import co.yap.sendmoney.y2y.home.yapcontacts.YapContactsAdaptor
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState

interface IPhoneContact {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var adaptor: YapContactsAdaptor
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var stateLiveData: MutableLiveData<co.yap.widgets.State>
        var isNoContacts: ObservableBoolean
        var isNoSearchResult: ObservableBoolean
        var isShowContactsCounter: ObservableBoolean
        var contactsCounts: ObservableInt
    }
}