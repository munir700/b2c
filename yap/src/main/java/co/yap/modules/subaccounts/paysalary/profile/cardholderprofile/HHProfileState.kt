package co.yap.modules.subaccounts.paysalary.profile.cardholderprofile

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.Customer
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.yapcore.BaseState

class HHProfileState : BaseState(), IHHProfile.State {
    override var customer: MutableLiveData<Customer> = MutableLiveData()
    override var subAccount: MutableLiveData<SubAccount> = MutableLiveData()

}