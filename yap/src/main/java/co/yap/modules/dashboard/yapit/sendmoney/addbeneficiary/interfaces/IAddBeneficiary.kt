package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddBeneficiary {

    interface State : IBase.State {
        var country: String
        var countryCode: String
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
        var country2DigitIsoCode: String

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
        var selectedBeneficiaryType: String?
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnAddNow(id: Int)
        fun handlePressOnAddDomestic(id: Int)
        fun addCashPickupBeneficiary()
        fun addDomesticBeneficiary(objBeneficiary: Beneficiary?)
        var addBeneficiarySuccess: MutableLiveData<Boolean>
        var beneficiary: Beneficiary?
        fun createOtp(action: String)
        val otpCreateObserver:MutableLiveData<Boolean>

//      fun generateRequestDTO(beneficiaryData: AddBeneficiaryData): AddBeneficiaryRequestDTO
        //fun generateCashPayoutBeneficiaryRequestDTO()
//      var onSuccess: MutableLiveData<Int>

    }

    interface View : IBase.View<ViewModel>
}