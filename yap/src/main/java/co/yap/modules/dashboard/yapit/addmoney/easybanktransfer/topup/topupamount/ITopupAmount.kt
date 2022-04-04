package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ITopupAmount {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        val denominationFirstAmount: String
        val denominationSecondAmount: String
        val denominationThirdAmount: String
        val valid: MutableLiveData<Boolean>
        val availableBalance: MutableLiveData<CharSequence>
    }
}