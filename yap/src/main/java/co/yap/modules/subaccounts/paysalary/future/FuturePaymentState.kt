package co.yap.modules.subaccounts.paysalary.future

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class FuturePaymentState : BaseState(), IFuturePayment.State {
    override var date: ObservableField<String> = ObservableField("")
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()
    override var amount: MutableLiveData<String> = MutableLiveData()
    override var isValid: MutableLiveData<Boolean> = MutableLiveData(false)
}
