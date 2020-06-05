package co.yap.modules.subaccounts.paysalary.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class HHSalaryProfileState : BaseState(), IHHSalaryProfile.State {

    override var filterCount: ObservableField<Int> = ObservableField()
    override var isTransEmpty: ObservableField<Boolean> = ObservableField(true)
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var lastSalaryTransferAmount: MutableLiveData<String>? = MutableLiveData()
    override var nextSalaryTransfer: MutableLiveData<String>? = MutableLiveData()
    override var expenseAmount: MutableLiveData<String>? = MutableLiveData()
}