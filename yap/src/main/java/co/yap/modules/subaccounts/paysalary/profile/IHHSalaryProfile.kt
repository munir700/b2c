package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHHSalaryProfile {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnClick(id: Int)
    }

    interface State : IBase.State {
        var filterCount: ObservableField<Int>
        var isTransEmpty: ObservableField<Boolean>
        var subAccount: MutableLiveData<SubAccount>
    }
}