package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupsuccessscreen

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPaymentSuccessful {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun setNewBalanceData(balance: String)
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var newBalanceText:MutableLiveData<CharSequence>
    }
}