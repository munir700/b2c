package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.text.TextUtils
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.trackEvent
import co.yap.yapcore.leanplum.TrackEvents
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.enums.DocumentType
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


    override fun onCreate() {
        super.onCreate()
        getSectionedCountriesList()
        parentViewModel?.let { populateState(it.identity) }
    }

    override fun handlePressOnRescanBtn() {
        parentViewModel?.let { populateState(it.identity) }
        clickEvent.setValue(EVENT_RESCAN)
    }

    override fun handlePressOnConfirmBtn() {
        sectionedCountries?.data?.get(1)?.isoCountryCode2Digit = "us"

        parentViewModel?.identity?.identity?.let {
            when {
                TextUtils.isEmpty(it.givenName) || TextUtils.isEmpty(it.nationality) ->
                    clickEvent.setValue(EVENT_ERROR_INVALID_EID)
                !it.isExpiryDateValid -> {
                    clickEvent.setValue(EVENT_ERROR_EXPIRED_EID)
                }
                !it.isDateOfBirthValid -> {
                    clickEvent.setValue(EVENT_ERROR_UNDER_AGE)
                    trackEvent(TrackEvents.EIDA_CALLBACK_UNDER_18)
                }
                it.nationality.equals("USA", true) || it.isoCountryCode2Digit.equals(
                    "US",
                    true
                ) -> {
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(EVENT_ERROR_FROM_USA)
                    trackEvent(TrackEvents.EIDA_CALLBACK_US_CITIZEN)
                }
                it.isoCountryCode2Digit.equals(
                    sectionedCountries?.data?.find { country -> country.isoCountryCode2Digit == it.isoCountryCode2Digit }?.isoCountryCode2Digit,
                    true
                ) -> {
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(
                        EVENT_ERROR_FROM_USA
                    )
                    trackEvent(TrackEvents.EIDA_CALLBACK_PROHIBITED_CITIZENS)
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
        val file = File(result.document.files[1].croppedFile)
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
                            if (data.sex.equals("M")) Gender.Male else Gender.Female
                        identity.sirName = data.surname
                        identity.givenName = data.names
                        identity.expirationDate =
                            DateUtils.stringToDate(data.expiration_date, "yyMMdd")
                        identity.dateOfBirth =
                            DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                        identity.citizenNumber = data.optional1
                        identity.isoCountryCode2Digit = "us"//data.isoCountryCode2Digit
                        result.identity = identity

                        parentViewModel?.identity = result

                        populateState(result)
                    } else {
                        result.identity = Identity()
                        parentViewModel?.identity = result
                        populateState(result)
                        clickEvent.setValue(EVENT_FINISH)
                        state.toast = response.data.errors?.message!!
                        //clearData()
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    clickEvent.setValue(EVENT_FINISH)
                }
            }
            state.loading = false
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

    private fun performUploadDocumentsRequest() {
        parentViewModel?.identity?.let {
            launch {
                val request = UploadDocumentsRequest(
                    documentType = if (it.document.type == DocumentType.EID) "EMIRATES_ID" else "PASSPORT",
                    firstName = it.identity.givenName,
                    lastName = it.identity.sirName,
                    dateExpiry = it.identity.expirationDate,
                    dob = it.identity.dateOfBirth,
                    fullName = it.identity.givenName + " " + it.identity.sirName,
                    gender = it.identity.gender.mrz.toString(),
                    nationality = it.identity.nationality,
                    identityNo = it.identity.citizenNumber,
                    filePaths = it.document.files.run {
                        val files: ArrayList<String> = arrayListOf()
                        forEach { files.add(it.originalFile) }
                        files
                    }
                )

                state.loading = true
                val response = repository.uploadDocuments(request)
                state.loading = false

                when (response) {
                    is RetroApiResponse.Success -> {
                        if (parentViewModel?.allowSkip?.value == true) {
                            clickEvent.setValue(EVENT_FINISH)
                            MoreActivity.showExpiredIcon = false
                        } else clickEvent.setValue(EVENT_NEXT)
                    }
                    is RetroApiResponse.Error -> {
                        if (response.error.actualCode == EVENT_ALREADY_USED_EID) {
                            clickEvent.setValue(EVENT_ALREADY_USED_EID)
                            state.toast = response.error.message
                        } else {
                            clickEvent.setValue(EVENT_NEXT)
                            state.toast = response.error.message
                        }
                    }
                }
            }
        }
    }

    private fun populateState(identity: IdentityScannerResult?) {
        identity?.let {
            state.fullName = it.identity.givenName + " " + it.identity.sirName
            state.fullNameValid = state.fullName.isNotBlank()
            state.nationality = it.identity.nationality
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.nationality.equals("USA", false)
            state.dateOfBirth = DateUtils.dateToString(it.identity.dateOfBirth)
            state.dateOfBirthValid = it.identity.isDateOfBirthValid
            state.expiryDate = DateUtils.dateToString(it.identity.expirationDate)
            state.expiryDateValid = it.identity.isExpiryDateValid
            state.genderValid = true
            state.gender = it.identity.gender.run {
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