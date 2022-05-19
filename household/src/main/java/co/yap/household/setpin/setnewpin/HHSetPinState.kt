package co.yap.household.setpin.setnewpin

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState
import javax.inject.Inject

class HHSetPinState @Inject constructor(): BaseState(), IHHSetPin.State {
    override var pinCode: MutableLiveData<String> = MutableLiveData()
    override var dialerError: MutableLiveData<String> = MutableLiveData()
    override var setPinDataModel: MutableLiveData<SetPinDataModel> = MutableLiveData()
}