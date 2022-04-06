package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import co.yap.countryutils.country.Country
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.OnDrawableClickListener
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import java.util.*

interface IEidInfoReviewAmendment {
    interface State : IBase.State {
        var firstName: String
        var middleName: String
        var lastName: String
        var nationality: MutableLiveData<Country?>
        var dateOfBirth: MutableLiveData<String>
        var gender: String
        var expiryDate: String
        var citizenNumber: MutableLiveData<String>
        var caption: String
        var fullNameValid: Boolean
        var citizenNumberValid: Boolean
        var nationalityValid: Boolean

        //        var dateOfBirthValid: Boolean
        var genderValid: Boolean
        var expiryDateValid: MutableLiveData<Boolean>
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
        var isDateOfBirthValid: ObservableBoolean
        var ageLimit: Int?
        var isCountryUS: Boolean
        var countryName: ObservableField<String>
        var errorScreenVisited: Boolean
    }

    interface View : IBase.View<ViewModel> {
        fun showErrorScreen(actionId: NavDirections)
    }

    interface ViewModel : IBase.ViewModel<State> {
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
        fun handleAgeValidation()
        fun handleIsUsValidation()
        fun requestAllAPIs(callAll: Boolean)
        fun requestAllEIDConfigurations(
            callAll: Boolean,
            responses: (
                RetroApiResponse<SectionedCountriesResponseDTO>?,
                RetroApiResponse<BaseResponse<ConfigureEIDResponse>>?,
                RetroApiResponse<BaseResponse<UqudoTokenResponse>>?
            ) -> Unit
        )

        var uqudoResponse: MutableLiveData<UqudoTokenResponse>
        fun populateUqudoState(identity: EidData?)

    }
}