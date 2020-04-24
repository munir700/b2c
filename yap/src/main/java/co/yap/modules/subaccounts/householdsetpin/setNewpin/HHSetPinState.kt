package co.yap.modules.subaccounts.householdsetpin.setNewpin

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class HHSetPinState : BaseState(), IHHSetPin.State {
    override var setPinDataModel: MutableLiveData<SetPinDataModel> = MutableLiveData()
}