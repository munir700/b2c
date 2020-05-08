package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.text.TextUtils
import co.yap.app.YAPApplication
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.MyUserManager
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class EidInfoReviewViewModel(application: Application) :
    KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewState = EidInfoReviewState()
    private var sectionedCountries: SectionedCountriesResponseDTO? = null

    override var sanctionedCountry: String = "" // for runtime hanlding
    override var sanctionedNationality: String = "" // for runtime hanlding
    override var errorTitle: String = ""
    override var errorBody: String = ""
    var pandemicValidation: Boolean = false

    override fun onCreate() {
        super.onCreate()
        getSectionedCountriesList()
        parentViewModel?.identity?.let { populateState(it) }
    }

    override fun handlePressOnRescanBtn() {
        parentViewModel?.identity?.let { populateState(it) }
        clickEvent.setValue(EVENT_RESCAN)
    }

    override fun handlePressOnConfirmBtn() {
        parentViewModel?.identity?.let {
            when {
                TextUtils.isEmpty(it.givenName) || TextUtils.isEmpty(it.nationality) ->
                    clickEvent.setValue(EVENT_ERROR_INVALID_EID)
                !it.isExpiryDateValid -> {
                    errorTitle =
                        getString(Strings.screen_kyc_information_error_display_text_title_expired_card)
                    errorBody =
                        getString(Strings.screen_kyc_information_error_display_text_explanation_expired_card)

                    clickEvent.setValue(EVENT_ERROR_EXPIRED_EID)
                }
                !it.isDateOfBirthValid -> {
                    errorTitle =
                        getString(Strings.screen_kyc_information_error_display_text_title_under_age)
                    errorBody =
                        getString(Strings.screen_kyc_information_error_display_text_explanation_under_age)
                    clickEvent.setValue(EVENT_ERROR_UNDER_AGE)
                    trackEvent(KYCEvents.EID_UNDER_AGE_18.type)
                }
                it.nationality.equals("USA", true) || it.isoCountryCode2Digit.equals(
                    "US",
                    true
                ) -> {
                    errorTitle = "It's not you, its US"
                    errorBody =
                        "We're sorry, we aren't able to create bank accounts for US Citizens at this point. Stay tuned though, we'll announce it everywhere if this changes!"
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(EVENT_ERROR_FROM_USA)
                    trackEvent(KYCEvents.KYC_US_CITIIZEN.type)
                }
                it.isoCountryCode2Digit.equals(
                    sectionedCountries?.data?.find { country ->
                        country.isoCountryCode2Digit.equals(
                            it.isoCountryCode2Digit,
                            true
                        )
                    }?.isoCountryCode2Digit,
                    true
                ) -> {
                    errorTitle = "We're so sorry"//countryName
                    errorBody =
                        "Unfortunately, we cannot go ahead with creating your account at this time. Thank you for your interest in YAP."
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    handleUserAcceptance(EVENT_ERROR_FROM_USA)
                    trackEvent(KYCEvents.KYC_PROHIBITED_CITIIZEN.type)
                }
                parentViewModel?.document != null && it.citizenNumber != parentViewModel?.document?.identityNo -> {
                    state.toast = "Your EID doesn't match with the current EID."
                }
                else -> {
                    performUploadDocumentsRequest()
                }
            }
        }
    }

    override fun handleUserRejection(reason: Int) {
        handlePressOnRescanBtn()
    }

    override fun handleUserAcceptance(reason: Int) {
        clickEvent.setValue(EVENT_NEXT_WITH_ERROR)
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        uploadDocuments(result)
    }


    private fun uploadDocuments(result: IdentityScannerResult) {

        if (!result.document.files.isNullOrEmpty() && result.document.files.size < 3) {
            val file = File(result.document.files[1].croppedFile)
            parentViewModel?.paths?.clear()
            parentViewModel?.paths?.add(result.document.files[0].croppedFile)
            parentViewModel?.paths?.add(result.document.files[1].croppedFile)


            val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
            val part =
                MultipartBody.Part.createFormData("image", file.name, fileReqBody)
            launch {
                state.loading = true
                when (val response = repository.detectCardData(part)) {

                    is RetroApiResponse.Success -> {

                        val data = response.data.data
                        if (data != null) {
                            val identity = Identity()
                            identity.nationality = data.nationality
                            identity.gender =
                                if (data.sex.equals("M", true)) Gender.Male else Gender.Female
                            identity.sirName = data.surname
                            identity.givenName = data.names
                            identity.expirationDate =
                                DateUtils.stringToDate(data.expiration_date, "yyMMdd")
                            identity.dateOfBirth =
                                DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                            identity.citizenNumber = data.optional1
                            identity.isoCountryCode2Digit = data.isoCountryCode2Digit
                            identity.isoCountryCode3Digit = data.isoCountryCode3Digit
                            result.identity = identity

                            parentViewModel?.identity = identity

                            populateState(parentViewModel?.identity)
                        } else {
                            result.identity = Identity()
                            parentViewModel?.identity = Identity()
                            populateState(Identity())
                            clickEvent.setValue(EVENT_FINISH)
                            state.toast = "${response.data.errors?.message
                                ?: " Error occurred"}^${AlertType.DIALOG.name}"
                        }
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        state.toast = response.error.message
                        clickEvent.setValue(EVENT_FINISH)
                    }
                }
                state.loading = false
            }
        }
    }

    private fun getSectionedCountriesList() {
        launch {
            when (val response = repository.getSectionedCountries()) {
                is RetroApiResponse.Success -> {
                    sectionedCountries = response.data
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }


    fun checkPandemic(expirationDate: Any): Any {
//        var verifiedDate: Any = expirationDate as Date
        var verifiedDate: Any
        var pandemicExpiryDateToString = "Dec 31, 2020"
        var pandemicExpiryDateFromString = "Mar 1, 2020"
        val pandemicExpiryToDate =
            DateUtils.stringToDate(pandemicExpiryDateToString, DateUtils.FORMAT_DATE_MON_YEAR)
        val pandemicExpiryFromDate =
            DateUtils.stringToDate(pandemicExpiryDateFromString, DateUtils.FORMAT_DATE_MON_YEAR)

//        If EID is expiring between  Mar 1, 2020, to Dec 31, 2020 Mark expiry date for EID as Dec 31, 2020,
//        which means any user whose EID is expiring between  Mar 1, 2020, to Dec 31, 2020 will be able to onboard in our system.

        if (expirationDate is Date) {
            verifiedDate = expirationDate as Date

            if ((expirationDate?.after(pandemicExpiryFromDate) && expirationDate?.equals(
                    pandemicExpiryToDate
                )) || expirationDate?.equals(
                    pandemicExpiryFromDate
                )
            ) {
                pandemicValidation = true
                verifiedDate = pandemicExpiryToDate!!
            }

        } else {
            verifiedDate = expirationDate as String

            var convertedDate: Date = DateUtils.stringToDate(verifiedDate, "yyMMdd")!!

            if ((convertedDate?.after(pandemicExpiryFromDate) && convertedDate?.equals(
                    pandemicExpiryToDate
                )) || convertedDate?.equals(
                    pandemicExpiryFromDate
                )
            ) {
                pandemicValidation = true
                verifiedDate = DateUtils.dateToString(pandemicExpiryToDate) as String


            }
        }


        return verifiedDate
    }

    private fun performUploadDocumentsRequest() {
        parentViewModel?.identity?.let {
            launch {
                val request = UploadDocumentsRequest(
                    documentType = "EMIRATES_ID",
                    firstName = it.givenName,
                    lastName = it.sirName,
                    dateExpiry = checkPandemic(it.expirationDate) as Date,
                    dob = it.dateOfBirth,
                    fullName = it.givenName + " " + it.sirName,
                    gender = it.gender.mrz.toString(),
                    nationality = it.isoCountryCode3Digit.toUpperCase(),
                    identityNo = if (YAPApplication.appInfo?.build_type == "debug") (700000000000000..800000000000000).random()
                        .toString() else it.citizenNumber,
                    filePaths = parentViewModel?.paths ?: arrayListOf()
                )

                state.loading = true
                val response = repository.uploadDocuments(request)
                state.loading = false

                when (response) {
                    is RetroApiResponse.Success -> {
                        when (MyUserManager.eidStatus) {
                            EIDStatus.EXPIRED, EIDStatus.VALID -> {
                                MyUserManager.eidStatus = EIDStatus.VALID
                                clickEvent.setValue(EVENT_EID_UPDATE)
                            }
                            EIDStatus.NOT_SET -> {
                                MyUserManager.eidStatus = EIDStatus.VALID
                                clickEvent.setValue(EVENT_NEXT)
                            }
                            else -> {
                                MyUserManager.eidStatus = EIDStatus.VALID
                                clickEvent.setValue(EVENT_NEXT)
                            }
                        }
                    }
                    is RetroApiResponse.Error -> {
                        if (response.error.actualCode.equals(
                                EVENT_ALREADY_USED_EID.toString(),
                                true
                            )
                        ) {
                            clickEvent.setValue(EVENT_ALREADY_USED_EID)
                        }
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    }
                }
            }
        }
    }

    fun splitLastNames(lastNames: String) {
        val parts = lastNames.split(" ")
        state.firstName =parts.get(0)

        if (parts.size == 2) {
            state.lastName = parts.get(1)
        } else if (parts.size > 2) {
            state.middleName = parts.get(1)
//            state.lastName = parts.get(2)
            var x=2
            while (x < parts.size) {
                if (state.lastName.isEmpty()) {
                    state.lastName = parts.get(x)
                 } else {
                    state.lastName = state.lastName + " " + parts.get(x)
                }
                x++ // Same as x += 1
            }
//            parts.forEach { name ->
//                if (state.lastName.isEmpty()) {
//                    state.lastName = name
//                } else {
//                    state.lastName = state.lastName + " " + name
//                }
//            }
        }
    }

    private fun populateState(identity: Identity?) {
        identity?.let {
            splitLastNames( it.givenName + " " + it.sirName)
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality = it.nationality
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.nationality.equals("USA", true)
            state.dateOfBirth = DateUtils.dateToString(it.dateOfBirth)
            state.dateOfBirthValid = it.isDateOfBirthValid
            state.expiryDate = DateUtils.dateToString(it.expirationDate)
            state.expiryDateValid = if (pandemicValidation) true else it.isExpiryDateValid
            state.genderValid = true
            state.gender = it.gender.run {
                when {
                    this == Gender.Male -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)
                    this == Gender.Female -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)
                    else -> {
                        state.genderValid = false
                        ""
                    }
                }
            }
        }
    }
}