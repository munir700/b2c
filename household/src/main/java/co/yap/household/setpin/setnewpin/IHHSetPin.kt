package co.yap.household.setpin.setnewpin

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSetPin {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun loadData()
    }
    interface ViewModel : IBase.ViewModel<State> {
        val eventSuccess: Int
            get() = 1
        val eventFailure: Int
            get() = 0

        var mobileNumber: String
        var errorEvent: SingleClickEvent
        fun setPinRequest()
        fun setCardPin()
    }

    interface State : IBase.State {
        var pinCode: MutableLiveData<String>
        var dialerError: MutableLiveData<String>
        var setPinDataModel: MutableLiveData<SetPinDataModel>
    }
}