package co.yap.sendmoney.y2y.home.yapcontacts

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapContact {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var contactsAdapter: YapContactsAdaptor
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var stateLiveData: MutableLiveData<co.yap.widgets.State>
        var isNoYapContacts: ObservableBoolean
        var isNoSearchResult: ObservableBoolean
        var isShowContactsCounter: ObservableBoolean
        var contactsCounts: ObservableInt
    }
}