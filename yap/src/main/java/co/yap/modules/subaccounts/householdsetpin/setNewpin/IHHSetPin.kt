package co.yap.modules.subaccounts.householdsetpin.setNewpin

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPin {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun loadData()
    }
    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_SET_CARD_PIN_SUCCESS: Int
            get() = 1

        var pincode: String
        var mobileNumber: String
        var clickEvent: SingleClickEvent
        var errorEvent: SingleClickEvent
        fun setPinRequest()
        fun handleButtonPress(id: Int)
    }

    interface State : IBase.State {
        var setPinDataModel: MutableLiveData<SetPinDataModel>
    }
}