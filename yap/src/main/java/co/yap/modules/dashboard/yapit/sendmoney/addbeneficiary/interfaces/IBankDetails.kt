package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.OtherBankQuery
import co.yap.networking.customers.responsedtos.beneficiary.BankParams
import co.yap.networking.customers.responsedtos.sendmoney.Bank
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBankDetails {

    interface State : IBase.State {
        var bankName: String
        var bankBranch: String
        var bankCity: String
        var swiftCode: String
        var valid: Boolean
        var hideSwiftSection: Boolean
        var isRmt: ObservableField<Boolean>
        var buttonText: String
        var txtCount: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var bankParams: MutableLiveData<List<BankParams>>
        var bankList: MutableLiveData<ArrayList<Bank>>
        fun handlePressOnView(id: Int)
        fun searchRMTBanks(otherBankQuery: OtherBankQuery)
        fun retry()
    }

    interface View : IBase.View<ViewModel>
}