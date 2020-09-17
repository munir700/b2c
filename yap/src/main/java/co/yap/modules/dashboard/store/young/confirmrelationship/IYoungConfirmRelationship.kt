package co.yap.modules.dashboard.store.young.confirmrelationship

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungConfirmRelationship {
    interface State : IBase.State{
        var realtion: MutableLiveData<String>
        var childName :  MutableLiveData<String>
    }
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface View : IBase.View<ViewModel>
}
