package co.yap.sendmoney.editbeneficiary.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEditBeneficiary {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent?
        fun handlePressOnConfirm(id: Int)
        fun requestUpdateBeneficiary()
        fun requestCountryInfo()
        fun validateBeneficiaryDetails(beneficiary:Beneficiary)
        fun createBeneficiaryRequest()
        var onUpdateSuccess:MutableLiveData<Boolean>
        var isBeneficiaryValid:MutableLiveData<Boolean>
        var onBeneficiaryCreatedSuccess:MutableLiveData<Boolean>
    }
    interface State : IBase.State {
        var country: String?
        var countryCode: String?
        var transferType: String?
        var currency: String?
        var nickName: String?
        var firstName: String?
        var lastName: String?
        var phoneNumber: String?
        var accountNumber: String?
        var swiftCode: String?
        var countryBankRequirementFieldCode: String?
        var beneficiary: Beneficiary?
        var residenceCountry: Country?
        var needOverView:Boolean?
        var needIban:Boolean?
        var showIban:Boolean?
        var valid: Boolean?
    }
}