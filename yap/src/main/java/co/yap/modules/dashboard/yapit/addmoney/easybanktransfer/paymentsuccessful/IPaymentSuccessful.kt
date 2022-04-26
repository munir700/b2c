package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.paymentsuccessful

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IPaymentSuccessful {
    interface View : IBase.View<ViewModel> {

    }

    interface ViewModel : IBase.ViewModel<State> {
        fun setNewBalanceData()
    }

    interface State : IBase.State {
        var newBalanceText:MutableLiveData<CharSequence>
    }
}