package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IBeneficiaryAccountDetails {

    interface State : IBase.State {
        var accountIban: String
        var countryBankRequirementFieldCode: String
        var beneficiaryAccountNumber: String
        var swiftCode: String
        var valid: Boolean

        var bankName: String
        var idCode: String
        var bankAddress: String
        var bankPhoneNumber: String

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent

        fun handlePressOnAddBank(id: Int)
    }

    interface View : IBase.View<ViewModel>

}