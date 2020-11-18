package co.yap.sendmoney.home.main

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface ISMBeneficiaryParent {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State {
        var sendMoneyType: MutableLiveData<String>?
        var isSearching: MutableLiveData<Boolean>?
    }
}
