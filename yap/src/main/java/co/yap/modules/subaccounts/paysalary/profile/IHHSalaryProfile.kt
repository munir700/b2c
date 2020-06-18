package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.CustomerHHApi
import co.yap.networking.customers.household.responsedtos.HouseHoldLastNextSalary
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSalaryProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var customersHHRepository: CustomerHHApi
        var clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
        fun getLastNextTransaction(uuid: String?)
    }

    interface State : IBase.State {
        var filterCount: ObservableField<Int>
        var isTransEmpty: ObservableField<Boolean>
        var subAccount: MutableLiveData<SubAccount>
        var lastSalaryTransfer: MutableLiveData<HouseHoldLastNextSalary>?
        var nextSalaryTransfer: MutableLiveData<HouseHoldLastNextSalary>?
        var expense: MutableLiveData<HouseHoldLastNextSalary>?
    }
}