package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult

interface IEidInfoReview {

    interface State : IBase.State {
        var firstName: String
        var middleName: String
        var lastName: String
        var nationality: String
        var dateOfBirth: String
        var gender: String
        var expiryDate: String
        var citizenNumber: String
        var caption: String

        var fullNameValid: Boolean
        var nationalityValid: Boolean
        var dateOfBirthValid: Boolean
        var genderValid: Boolean
        var expiryDateValid: Boolean
        var valid: Boolean
        var isShowMiddleName: ObservableBoolean
        var isShowLastName: ObservableBoolean
    }

    interface View : IBase.View<ViewModel> {
        fun showUnderAgeAlert()
        fun showExpiredEidAlert()
        fun showInvalidEidAlert()
        fun showUSACitizenAlert()
        fun openCardScanner()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_RESCAN: Int
            get() = 1
        val EVENT_ERROR_UNDER_AGE: Int
            get() = 2
        val EVENT_ERROR_EXPIRED_EID: Int
            get() = 3
        val EVENT_ERROR_FROM_USA: Int
            get() = 4
        val EVENT_NEXT_WITH_ERROR: Int
            get() = 5
        val EVENT_NEXT: Int
            get() = 6
        val EVENT_FINISH: Int
            get() = 7
        val EVENT_ERROR_INVALID_EID: Int
            get() = 8
        val EVENT_ALREADY_USED_EID: Int
            get() = 1041

        val EVENT_EID_UPDATE: Int
            get() = 9

        val clickEvent: SingleClickEvent
        fun handlePressOnRescanBtn()
        fun handlePressOnConfirmBtn()
        fun handleUserRejection(reason: Int)
        fun handleUserAcceptance(reason: Int)
        fun handlePressOnEdit(id: Int)
        fun onEIDScanningComplete(result: IdentityScannerResult)
        var sanctionedCountry: String
        var sanctionedNationality: String
        var errorTitle: String
        var errorBody: String
    }
}