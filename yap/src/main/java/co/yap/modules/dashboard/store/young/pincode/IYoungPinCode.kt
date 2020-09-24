package co.yap.modules.dashboard.store.young.pincode

import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.IBase

interface IYoungPinCode {
    interface State : IBase.State{
        var childName : MutableLiveData<String>
        var passCode: MutableLiveData<String>
        var dialerError: MutableLiveData<String>?
        var address: MutableLiveData<Address>?
    }
    interface ViewModel : IBase.ViewModel<State>{
        fun requestGetAddressForPhysicalCard(apiResponse: ((Boolean) -> Unit?)?)
    }
    interface View : IBase.View<ViewModel>
}