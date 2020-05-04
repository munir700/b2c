package co.yap.modules.subaccounts.householdsetpin.setNewpin

import android.content.Context
import androidx.databinding.ObservableField
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
        val EVENT_SET_CARD_PIN_FAILURE: Int
            get() = 0

        var mobileNumber: String
        var clickEvent: SingleClickEvent
        var errorEvent: SingleClickEvent
        fun setPinRequest()
        fun setCardPin()
        fun handleButtonPress(id: Int, context: Context)
    }

    interface State : IBase.State {
        var pinCode: String
        var dialerError: ObservableField<String>
        var setPinDataModel: MutableLiveData<SetPinDataModel>
    }
}