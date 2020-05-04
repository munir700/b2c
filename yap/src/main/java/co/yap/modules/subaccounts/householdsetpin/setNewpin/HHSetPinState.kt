package co.yap.modules.subaccounts.householdsetpin.setNewpin

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BR
import co.yap.yapcore.BaseState

class HHSetPinState : BaseState(), IHHSetPin.State {
    @get:Bindable
    override var pinCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.pincode)

        }
    @get:Bindable
    override var dialerError: ObservableField<String> = ObservableField("")
        set(value) {
            field = value
            notifyPropertyChanged(BR.dialerError)
        }

    override var setPinDataModel: MutableLiveData<SetPinDataModel> = MutableLiveData()
}