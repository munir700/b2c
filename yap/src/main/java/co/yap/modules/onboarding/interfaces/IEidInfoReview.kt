package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.OnDrawableClickListener
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import java.util.*

interface IEidInfoReview {

    interface State : IBase.State {
        var firstName: String
        var middleName: String
        var lastName: String
        var nationality: ObservableField<Country?>
        var dateOfBirth: String
        var gender: String
        var expiryDate: String
        var citizenNumber: String
        var caption: String
        var fullNameValid: Boolean
        var citizenNumberValid: Boolean
        var nationalityValid: Boolean
        var dateOfBirthValid: Boolean
        var genderValid: Boolean
        var expiryDateValid: Boolean
        var valid: Boolean
        var isShowMiddleName: ObservableBoolean
        var isShowLastName: ObservableBoolean
        var dobCalendar: Calendar
        var expiryCalendar: Calendar

        // Previous Data
        var previousFirstName: String?
        var previousMiddleName: String?
        var previousLastName: String?
        var previousNationality: String?
        var previousDateOfBirth: String?
        var previousGender: String?
        var previousExpiryDate: String?
        var previousCitizenNumber: String?
    }

    interface View : IBase.View<ViewModel> {
        fun showUnderAgeScreen()
        fun showExpiredEidScreen()
        fun showInvalidEidScreen()
        fun showUSACitizenScreen()
        fun openCardScanner()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val eventRescan: Int get() = 1
        val eventErrorUnderAge: Int get() = 2
        val eventErrorExpiredEid: Int get() = 3
        val eventErrorFromUsa: Int get() = 4
        val eventNextWithError: Int get() = 5
        val eventNext: Int get() = 6
        val eventFinish: Int get() = 7
        val eventErrorInvalidEid: Int get() = 8
        val eventAlreadyUsedEid: Int get() = 1041
        val eventEidUpdate: Int get() = 9
        val eventCitizenNumberIssue: Int get() = 10
        val eventEidExpiryDateIssue: Int get() = 11
        var eidStateLiveData: MutableLiveData<co.yap.widgets.State>
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun updateLabels(title: String, body: String)
        fun onEIDScanningComplete(result: IdentityScannerResult)
        var sanctionedCountry: String
        var sanctionedNationality: String
        var errorTitle: String
        var errorBody: String
        val drawableClickListener: OnDrawableClickListener
        fun getGenderOptions(): ArrayList<BottomSheetItem>
        var countries: ArrayList<Country>
        fun getAllCountries()
        var populateNationalitySpinnerData: MutableLiveData<ArrayList<Country>>
        fun getKYCDataFromServer()
        fun isFromAmendment(): Boolean
//        var validator: Validator?
    }
}