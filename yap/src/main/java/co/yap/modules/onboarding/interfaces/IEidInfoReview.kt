package co.yap.modules.onboarding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

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
        var uqudoToken: MutableLiveData<String>
        var showMiddleName: MutableLiveData<Boolean>
    }

    interface View : IBase.View<ViewModel> {
        fun showUnderAgeScreen()
        fun showExpiredEidScreen()
        fun showInvalidEidScreen()
        fun showUSACitizenScreen()
    }

    interface ViewModel : IBase.ViewModel<State> {

        var eidStateLiveData: MutableLiveData<co.yap.widgets.State>
        var configureEIDResponse: MutableLiveData<ConfigureEIDResponse>
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun updateLabels(title: String, body: String)
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
        fun populateUqudoState(identity: EidData?)

    }
}