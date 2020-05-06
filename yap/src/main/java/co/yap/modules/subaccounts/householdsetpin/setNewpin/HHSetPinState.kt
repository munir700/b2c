package co.yap.modules.subaccounts.householdsetpin.setNewpin

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHSetPinState : BaseState(), IHHSetPin.State {
    override var pinCode: MutableLiveData<String> = MutableLiveData()
    override var dialerError: MutableLiveData<String> = MutableLiveData()
    override var setPinDataModel: MutableLiveData<SetPinDataModel> = MutableLiveData()
}