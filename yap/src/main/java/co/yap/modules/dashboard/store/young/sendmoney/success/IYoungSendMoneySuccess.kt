package co.yap.modules.dashboard.store.young.sendmoney.success

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IYoungSendMoneySuccess {
    interface State : IBase.State{
    }
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface View : IBase.View<ViewModel>
}