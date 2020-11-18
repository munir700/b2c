package co.yap.sendmoney.home.main

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.yapcore.IBase

interface ISMBeneficiaryParent {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var beneficiariesList: List<IBeneficiary>
    }

    interface State : IBase.State {
        var sendMoneyType: MutableLiveData<String>?
    }
}
