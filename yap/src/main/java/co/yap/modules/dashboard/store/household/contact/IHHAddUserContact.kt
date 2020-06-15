package co.yap.modules.dashboard.store.household.contact

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IHHAddUserContact {
    interface State : IBase.State{
        var phone: MutableLiveData<String>
        var confirmPhone: MutableLiveData<String>
    }
    interface ViewModel : IBase.ViewModel<State>
    interface View : IBase.View<ViewModel>
}
