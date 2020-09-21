package co.yap.modules.dashboard.store.young.sendmoney

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungSendMoney {
    interface State : IBase.State{
        var amount: MutableLiveData<String>
    }
    interface ViewModel : IBase.ViewModel<State>{
        fun onAmountChange(amount: CharSequence, start: Int, before: Int, count: Int)
    }
    interface View : IBase.View<ViewModel>
}