package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBankDetails {

    interface State : IBase.State {
        var bankName: String
        var bankBranch: String
        var bankCity: String
        var swiftCode: String
        var valid: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun createBeneficiaryRequest()
        fun searchRMTBanks()
    }

    interface View : IBase.View<ViewModel>
}