package co.yap.modules.dashboard.store.young.pincode

import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IYoungPinCode {
    interface State : IBase.State{
        var childName : MutableLiveData<String>
        var passCode: MutableLiveData<String>
        var dialerError: MutableLiveData<String>?
    }
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface View : IBase.View<ViewModel>
}