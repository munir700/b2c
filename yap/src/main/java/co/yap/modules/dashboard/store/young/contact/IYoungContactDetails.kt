package co.yap.modules.dashboard.store.young.contact

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungContactDetails {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface State : IBase.State {
        var email: MutableLiveData<String>
        var contactName :  MutableLiveData<String>
        var phone: MutableLiveData<String>
        var countryCode: MutableLiveData<String>
    }
}