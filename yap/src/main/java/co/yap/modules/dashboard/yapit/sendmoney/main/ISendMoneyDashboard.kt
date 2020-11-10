package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.ActivitySendMoneyDashboardBinding
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyDashboardAdapter
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.permissions.PermissionHelper

interface ISendMoneyDashboard {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
        var rightButtonTextVisibility: ObservableBoolean
        var rightButtonText: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var recentTransfers: MutableLiveData<Beneficiary>
        var dashboardAdapter: SendMoneyDashboardAdapter
        fun geSendMoneyOptions(): MutableList<SendMoneyOptions>
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface View : IBase.View<ViewModel> {
        val sendMoneyToYAPContacts get() = 0
        val sendMoneyToLocalBank get() = 1
        val sendMoneyToInternational get() = 2
        val sendMoneyToHomeCountry get() = 3
        val sendMoneyQRCode get() = 4
        var permissionHelper: PermissionHelper?
        fun getBinding(): ActivitySendMoneyDashboardBinding
        fun setObservers()
        fun removeObservers()
    }
}