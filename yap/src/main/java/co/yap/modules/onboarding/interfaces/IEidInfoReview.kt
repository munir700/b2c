package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult

interface IEidInfoReview {

    interface State : IBase.State {
        var firstName: String
        var middleName: String
        var lastName: String
        var nationality: String
        var dateOfBirth: String
        var gender: String
        var expiryDate: MutableLiveData<String>
        var citizenNumber: MutableLiveData<String>
        var caption: String
        var fullNameValid: Boolean
        var nationalityValid: Boolean
        var genderValid: Boolean
        var expiryDateValid: MutableLiveData<Boolean>
        var valid: Boolean
        var isShowMiddleName: ObservableBoolean
        var isShowLastName: ObservableBoolean
        var isDateOfBirthValid: ObservableBoolean
        var AgeLimit: Int?
        var isCountryUS: Boolean
        var isTokenValid: ObservableBoolean
        var uqudoToken: MutableLiveData<String>
        var payLoadObj: MutableLiveData<UqudoPayLoad>
        var uqudoHeaderObj: MutableLiveData<UqudoHeader>
        var isExpired: MutableLiveData<Boolean>
        var frontImage: MutableLiveData<String>
        var BackImage: MutableLiveData<String>
        var showMiddleName: MutableLiveData<Boolean>
    }

    interface View : IBase.View<ViewModel> {
        fun showUnderAgeScreen()
        fun showExpiredEidScreen()
        fun showInvalidEidScreen()
        fun showUSACitizenScreen()
        //   fun openCardScanner()
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
        var configureEIDResponse: MutableLiveData<ConfigureEIDResponse>
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun updateLabels(title: String, body: String)
        fun onEIDScanningComplete(result: IdentityScannerResult)
        var sanctionedCountry: String
        var sanctionedNationality: String
        var errorTitle: String
        var errorBody: String
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
        fun populateUqudoState(identity: UqudoPayLoad?)

    }
}