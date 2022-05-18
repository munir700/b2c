package co.yap.modules.dashboard.store.young.pincode

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.BaseState
import javax.inject.Inject

class YoungCreatePinCodeState @Inject constructor() : BaseState(), IYoungPinCode.State {
    override var childName: MutableLiveData<String> = MutableLiveData("Lina")
    override var passCode: MutableLiveData<String> = MutableLiveData("")
    override var dialerError: MutableLiveData<String>? = MutableLiveData("")
    override var address: MutableLiveData<Address>? = MutableLiveData()
}