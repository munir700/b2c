package co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces

import android.graphics.drawable.Drawable
import co.yap.modules.yapit.sendmoney.addbeneficiary.models.AddBeneficiaryData
import co.yap.networking.customers.responsedtos.sendmoney.AddBeneficiaryRequestDTO
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IAddBeneficiary {

    interface State : IBase.State {
        var country: String
        var transferType: String
        var currency: String
        var nickName: String
        var firstName: String
        var lastName: String
        var phoneNumber: String
        var flagDrawableResId: Int
        var mobile: String
        var iban: String
        var confirmIban: String
        var drawbleRight: Drawable?
        var mobileNoLength: Int
        var valid: Boolean
        var validateCashflowButton: Boolean
        var validateDomesticButton: Boolean

        var id: Int
        var beneficiaryId: String?
        var accountUuid: String?
        var beneficiaryType: String?
        var title: String?
        var accountNo: String?
        var lastUsedDate: String?
        var swiftCode: String?
        var bankName: String?
        var branchName: String?
        var branchAddress: String?
        var identifierCode1: String?
        var identifierCode2: String?


    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent

        var addBeneficiaryData: AddBeneficiaryData

        fun handlePressOnAddNow(id: Int)
        fun handlePressOnAddDomestic(id: Int)

//      fun generateRequestDTO(beneficiaryData: AddBeneficiaryData): AddBeneficiaryRequestDTO
        fun generateCashPayoutBeneficiaryRequestDTO(beneficiary: Beneficiary)

    }

    interface View : IBase.View<ViewModel>
}