package co.yap.modules.dashboard.store.household.userinfo

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IHHAddUserName {
    interface State : IBase.State{
        var firstName:MutableLiveData<String>
        var lastName:MutableLiveData<String>
    }
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface View : IBase.View<ViewModel>
}
