package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase

interface ISendMoneyMain {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
        var rightButtonText: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var RecentTransfers: MutableLiveData<Beneficiary>
    }

    interface View : IBase.View<ViewModel>
}