package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.kyc.enums.Gender.Female
import co.yap.modules.kyc.enums.Gender.Male
import co.yap.modules.onboarding.enums.EidInfoEvents
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.EidData
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.getAge
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import java.util.*

class EidInfoReviewViewModel(application: Application) :
    KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewState = EidInfoReviewState()
    private var sectionedCountries: SectionedCountriesResponseDTO? = null
    override var sanctionedCountry: String = ""
    override var sanctionedNationality: String = ""
    override var errorTitle: String = ""
    override var errorBody: String = ""
    override var configureEIDResponse: MutableLiveData<ConfigureEIDResponse> = MutableLiveData()

    private val eidLength = 15
    override var eidStateLiveData: MutableLiveData<State> = MutableLiveData()

    override fun handlePressOnView(id: Int) {
        if (id == R.id.btnTouchId)
            handlePressOnConfirmBtn()
        else {
            clickEvent.setValue(id)
        }
    }

    private fun handlePressOnConfirmBtn() {
        parentViewModel?.uqudoManager?.getUqudoIdentity()?.let {
            when {
                TextUtils.isEmpty(it.fullName) || TextUtils.isEmpty(it.nationality) -> {
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_INVALID_EID.eventId)
                }
                state.expiryDateValid.value?.not() == true -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_expired_card),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_expired_card)
                    )
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_EXPIRED_EID.eventId)
                }
                !state.isDateOfBirthValid.get() -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_under_age).format(
                            state.AgeLimit?.value ?: 18
                        ),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_under_age).format(
                            state.AgeLimit?.value ?: 18
                        )
                    )
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROE_UNDERAGE.eventId)
                    trackEvent(KYCEvents.EID_UNDER_AGE_18.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_UNDERAGED)

                }
                state.isCountryUS -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_from_us),
                        body = getString(Strings.screen_kyc_information_error_text_description_from_us)
                    )
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_FROM_USA.eventId)
                    trackEvent(KYCEvents.KYC_US_CITIIZEN.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_US)
                }
                sectionedCountries?.let { sc ->
                    it.digit3CountryCode?.let { digit3Code ->
                        digit3Code.equals(sc.data.find { country ->
                            country.isoCountryCode3Digit.equals(
                                digit3Code,
                                true
                            )
                        }?.isoCountryCode3Digit, true)
                    } ?: true
                } ?: true -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_sanctioned_country),
                        body = getString(Strings.screen_kyc_information_error_text_description_sanctioned_country)
                    )
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_FROM_USA.eventId)
                    trackEvent(KYCEvents.KYC_PROHIBITED_CITIIZEN.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_SANCTIONED)
                }
                parentViewModel?.document != null && it.identityNo != parentViewModel?.document?.identityNo -> {
                    state.toast =
                        "Your EID doesn't match with the current EID.^${AlertType.DIALOG.name}"
                }
                else -> {
                    performUqudoUploadDocumentsRequest(false) {
                    }
                }
            }
        }
    }

    override fun updateLabels(title: String, body: String) {
        errorTitle = title
        errorBody = body
    }

    override fun performUqudoUploadDocumentsRequest(
        fromInformationErrorFragment: Boolean,
        success: (message: String) -> Unit
    ) {
        parentViewModel?.uqudoManager?.getUqudoIdentity()?.let {
            if (it.dateExpiry.toString().isBlank()) {
                clickEvent.setValue(EidInfoEvents.EVENT_EID_EXPIRY_DATE_ISSUE.eventId)
            } else {
                launch {
                    val request = UploadDocumentsRequest(
                        documentType = "EMIRATES_ID",
                        firstName = state.firstName,
                        middleName = if (state.middleName.isNotBlank()) state.middleName else null,
                        lastName = if (state.lastName.isNotBlank()) state.lastName else null,
                        dateExpiry = it.dateExpiry,
                        dob = it.dob,
                        fullName = getFullName(),
                        gender = it.gender,
                        nationality = it.digit3CountryCode ?: "",
                        identityNo = it.identityNo?.replace("-".toRegex(), ""),
                        filePaths = it.filePaths,
                        countryIsSanctioned = if (fromInformationErrorFragment) fromInformationErrorFragment else null
                    )

                    state.loading = true
                    val response = repository.uploadDocuments(request)
                    state.loading = false

                    when (response) {
                        is RetroApiResponse.Success -> {
                            when (SessionManager.eidStatus) {
                                EIDStatus.EXPIRED, EIDStatus.VALID -> {
                                    if (fromInformationErrorFragment) {
                                        success.invoke("success")
                                    } else {
                                        SessionManager.eidStatus = EIDStatus.VALID
                                        clickEvent.setValue(EidInfoEvents.EVENT_EID_UPDATE.eventId)
                                        trackEvent(KYCEvents.KYC_ID_CONFIRMED.type)
                                    }
                                }
                                EIDStatus.NOT_SET -> {
                                    if (fromInformationErrorFragment) {
                                        success.invoke("success")
                                    } else {
                                        SessionManager.eidStatus = EIDStatus.VALID
                                        clickEvent.setValue(EidInfoEvents.EVENT_NEXT.eventId)
                                        trackEvent(KYCEvents.KYC_ID_CONFIRMED.type)
                                    }
                                }
                                else -> {
                                    if (fromInformationErrorFragment) {
                                        success.invoke("success")
                                    } else {
                                        SessionManager.eidStatus = EIDStatus.VALID
                                        clickEvent.setValue(EidInfoEvents.EVENT_NEXT.eventId)
                                        trackEvent(KYCEvents.KYC_ID_CONFIRMED.type)
                                    }
                                }
                            }
                        }
                        is RetroApiResponse.Error -> {
                            if (fromInformationErrorFragment) {
                                success.invoke(response.error.message)
                            } else {
                                if (response.error.actualCode.equals(
                                        EidInfoEvents.EVENT_ALREADY_USED_EID.eventId.toString(),
                                        true
                                    )
                                ) {
                                    //clickEvent.setValue(EVENT_ALREADY_USED_EID)
                                }
                                state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                            }
                        }
                    }
                }

            }
        }
    }

    private fun getFullName(): String {
        return (when {
            state.middleName.isNotBlank() && state.lastName.isNotBlank() -> {
                "${state.firstName} ${state.middleName} ${state.lastName}"
            }
            state.middleName.isNotBlank() -> {
                "${state.firstName} ${state.middleName}"
            }
            state.lastName.isNotBlank() -> {
                "${state.firstName} ${state.lastName}"
            }
            else -> {
                state.firstName
            }
        })
    }


    private fun splitLastNames(lastNames: String) {
        val parts = lastNames.trim().split(" ".toRegex())
        state.firstName = parts[0]

        when {
            parts.size == 2 -> {
                state.lastName = parts[1]
                state.isShowLastName.set(true)
                state.isShowMiddleName.set(true)
            }
            parts.size > 2 -> {
                state.lastName = ""
                state.isShowLastName.set(true)
                state.isShowMiddleName.set(true)
                state.middleName = parts[1]
                var x = 2
                while (x < parts.size) {
                    if (state.lastName.isEmpty()) {
                        state.lastName = parts[x]
                    } else {
                        state.lastName = state.lastName + " " + parts[x]
                    }
                    x++
                }
            }
            else -> {
                state.isShowLastName.set(true)
                state.isShowMiddleName.set(true)
            }
        }
    }

    override fun requestAllAPIs(callAll: Boolean) {
        requestAllEIDConfigurations(callAll) { senctionedCountryResponse, configurationEIDResponse, uqudoTokenResponse ->
            launch(Dispatcher.Main) {
                state.viewState.postValue(false)
                when {
                    senctionedCountryResponse is RetroApiResponse.Success && configurationEIDResponse is RetroApiResponse.Success && uqudoTokenResponse is RetroApiResponse.Success -> {
                        sectionedCountries = senctionedCountryResponse.data
                        configureEIDResponse.value = configurationEIDResponse.data.data
//                        state.isDateOfBirthValid.set(getAge(identity.dateOfBirth) >= configureEIDResponse.value?.ageLimit ?: 18)
                        state.AgeLimit?.value = configureEIDResponse.value?.ageLimit
                        val countryName =
                            configureEIDResponse.value?.country2DigitIsoCode?.let { str ->
                                str.split(",").map { it -> it.trim() }.find {
                                    it.equals("US")
                                }
                            }
//                        state.isCountryUS =
//                            identity.isoCountryCode2Digit.contains(
//                                countryName ?: "US"
//                            )
                        uqudoTokenResponse.data.data?.let {
                            parentViewModel?.uqudoManager?.setUqudoToken(
                                it
                            )
                        }
                    }
                    uqudoTokenResponse is RetroApiResponse.Success -> {
                        uqudoTokenResponse.data.data?.let {
                            parentViewModel?.uqudoManager?.setUqudoToken(
                                it
                            )
                        }
                    }
                    else -> {
                        if (senctionedCountryResponse is RetroApiResponse.Error)
                            state.toast = senctionedCountryResponse.error.message
                    }
                }
            }
        }

    }

    private fun requestAllEIDConfigurations(
        callAll: Boolean,
        responses: (
            RetroApiResponse<SectionedCountriesResponseDTO>?,
            RetroApiResponse<BaseResponse<ConfigureEIDResponse>>?,
            RetroApiResponse<BaseResponse<UqudoTokenResponse>>?
        ) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(false)
            when (callAll) {
                true -> {
                    val senctionedCountriesList = launchAsync {
                        repository.getSectionedCountries()
                    }

                    val eidConfigurationResponse = launchAsync {
                        repository.getEIDConfigurations()
                    }
                    val uqudoTokenResponse = launchAsync {
                        repository.getUqudoAuthToken()
                    }
                    responses(
                        senctionedCountriesList.await(),
                        eidConfigurationResponse.await(),
                        uqudoTokenResponse.await()
                    )
                }
                else -> {
                    val uqudoTokenResponse = launchAsync {
                        repository.getUqudoAuthToken()
                    }
                    responses(
                        null,
                        null,
                        uqudoTokenResponse.await()
                    )
                }
            }

        }
    }


    override fun populateUqudoState(identity: EidData?) {
        identity?.let {
            val documentBack = it.documents[0].scan?.back
            val documentFront = it.documents[0].scan?.front
            documentFront?.fullName?.let { it1 ->
                splitLastNames(it1)
            }
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality = documentFront?.nationality ?: ""
            state.nationalityValid = state.nationality.isNotBlank() && !state.isCountryUS
            val expiryDate = parentViewModel?.uqudoManager?.getExpiryDate()
            val birthDate = parentViewModel?.uqudoManager?.getDateOfBirth()
            state.dateOfBirth =
                DateUtils.reformatToLocalString(birthDate, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDate.value =
                DateUtils.reformatToLocalString(expiryDate, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDateValid.value =
                expiryDate?.let { it1 -> parentViewModel?.uqudoManager?.isExpiryDateValid(it1) }
                    ?: false
            state.genderValid = true
            if (documentFront?.identityNumber?.length != eidLength && !Utils.isValidEID(
                    documentFront?.identityNumber
                )
            ) {
                clickEvent.setValue(EidInfoEvents.EVENT_CITIZEN_NUMBER_ISSUE.eventId)
            } else {
                state.citizenNumber.value = documentFront?.identityNumber
                parentViewModel?.state?.identityNo?.set(documentFront?.identityNumber)
            }
            state.gender = documentBack?.sex.run {
                when {
                    this == Male.mrz -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)
                    this == Female.mrz -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)
                    else -> {
                        state.genderValid = false
                        ""
                    }
                }
            }
            // If Age Limit available in case of Re-Scan, set Age validity again.
            state.AgeLimit?.value?.let { limit ->
                state.isDateOfBirthValid.set(
                    birthDate?.let { it1 -> getAge(it1) } ?: 18 >= limit
                )
            }
            val countryName = configureEIDResponse.value?.country2DigitIsoCode?.let { str ->
                str.split(",").map { it -> it.trim() }.find { code ->
                    code == "US"
                }
            }
            state.isCountryUS =
                getCountryCode(documentFront?.nationality ?: "").contains(countryName ?: "US")
            if (parentViewModel?.uqudoManager?.getFrontImagePath()
                    .isNullOrBlank() && parentViewModel?.uqudoManager?.getBackImagePath()
                    .isNullOrBlank()
            ) parentViewModel?.uqudoManager?.downloadImage { downloaded ->
                state.viewState.postValue(false)
                if (downloaded)
                    parentViewModel?.uqudoIdentity?.value =
                        parentViewModel?.uqudoManager?.getUqudoIdentity()
                else state.eidImageDownloaded.value = false
            }
        }
    }

    private fun getCountryCode(countryName: String): String =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName } ?: ""

    override fun navigateToConfirmNameFragment(navigate: () -> Unit) {
        parentViewModel?.state?.let { parentState ->
            parentState.middleName.set(state.middleName)
            parentState.firstName.set(state.firstName)
            parentState.lastName.set(state.lastName)
            parentState.nationality.set(state.nationality)
            SharedPreferenceManager.getInstance(context)
                .save(Constants.KYC_FIRST_NAME, parentState.firstName.get() ?: "")
            SharedPreferenceManager.getInstance(context)
                .save(Constants.KYC_LAST_NAME, parentState.lastName.get() ?: "")
            SharedPreferenceManager.getInstance(context)
                .save(Constants.KYC_MIDDLE_NAME, parentState.middleName.get() ?: "")
            navigate.invoke()
        }
    }
}