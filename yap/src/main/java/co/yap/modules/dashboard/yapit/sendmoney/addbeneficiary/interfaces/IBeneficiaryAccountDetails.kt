package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IBeneficiaryAccountDetails {

    interface State : IBase.State {
        var accountIban: String
        var accountConfirmIban: String
        var countryBankRequirementFieldCode: String
        var beneficiaryAccountNumber: String
        var swiftCode: String
        var valid: Boolean

        var bankName: String
        var idCode: String
        var bankAddress: String
        var bankPhoneNumber: String

        var showlyIban: ObservableField<Boolean>
        var showlyConfirmIban: ObservableField<Boolean>

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        val success: MutableLiveData<Boolean>
        var clickEvent: SingleClickEvent
        fun createBeneficiaryRequest()
        fun handlePressOnAddBank(id: Int)
        fun retry()
    }

    interface View : IBase.View<ViewModel>

}