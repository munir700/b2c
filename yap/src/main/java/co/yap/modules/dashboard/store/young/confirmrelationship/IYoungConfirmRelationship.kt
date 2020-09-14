package co.yap.modules.dashboard.store.young.confirmrelationship

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungConfirmRelationship {
    interface State : IBase.State{
        var email: MutableLiveData<String>
        var childName :  MutableLiveData<String>
    }
    interface ViewModel : IBase.ViewModel<State>{
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }
    interface View : IBase.View<ViewModel>
}
