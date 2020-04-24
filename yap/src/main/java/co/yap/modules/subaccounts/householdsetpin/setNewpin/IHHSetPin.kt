package co.yap.modules.subaccounts.householdsetpin.setNewpin

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPin {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handleButtonPress(id: Int)
        var clickEvent: SingleClickEvent
        fun setPinRequest()
    }

    interface State : IBase.State {
        var setPinDataModel: MutableLiveData<SetPinDataModel>
    }
}