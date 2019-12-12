package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.graphics.drawable.Drawable
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
        var flagDrawableResId: Int
        var mobileNo: String
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
        var clickEvent: SingleClickEvent
        fun handlePressOnAddNow(id: Int)
        fun handlePressOnAddDomestic(id: Int)

//      fun generateRequestDTO(beneficiaryData: AddBeneficiaryData): AddBeneficiaryRequestDTO
        //fun generateCashPayoutBeneficiaryRequestDTO()
//      var onSuccess: MutableLiveData<Int>

    }

    interface View : IBase.View<ViewModel>
}