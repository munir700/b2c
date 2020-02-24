package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IBeneficiaryAccountDetails {

    interface State : IBase.State {
        var accountIban: String
        //var accountConfirmIban: String
        var countryBankRequirementFieldCode: String
        var beneficiaryAccountNumber: String
        var swiftCode: String
        var valid: Boolean

        var bankName: String
        var idCode: String
        var bankAddress: String
        var bankPhoneNumber: String

        var showlyIban: ObservableField<Boolean>
        var isIbanMandatory:ObservableField<Boolean>
        //var showlyConfirmIban: ObservableField<Boolean>

    }

    interface ViewModel : IBase.ViewModel<State> {
        var beneficiary: Beneficiary?
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        val success: MutableLiveData<Boolean>
        val isBeneficiaryValid: MutableLiveData<Boolean>
        var clickEvent: SingleClickEvent
        val otpCreateObserver: MutableLiveData<Boolean>
        fun createBeneficiaryRequest()
        fun validateBeneficiaryDetails(beneficiary: Beneficiary)
        fun handlePressOnAddBank(id: Int)
        fun retry()
//        fun createOtp(action: String)
    }

    interface View : IBase.View<ViewModel>

}