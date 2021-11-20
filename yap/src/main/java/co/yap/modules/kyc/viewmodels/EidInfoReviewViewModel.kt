package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.DrawablePosition
import co.yap.widgets.edittext.OnDrawableClickListener
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.getFormattedDate
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import kotlinx.coroutines.delay
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class EidInfoReviewViewModel(application: Application) :
    KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel, IRepositoryHolder<CustomersRepository>, IValidator,
    Validator.ValidationListener {
    override var validator: Validator? = Validator(null)
    override val repository: CustomersRepository
        get() = CustomersRepository

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewState = EidInfoReviewState()
    private var sectionedCountries: SectionedCountriesResponseDTO? = null
    override var sanctionedCountry: String = ""
    override var sanctionedNationality: String = ""
    override var errorTitle: String = ""
    override var errorBody: String = ""
    private val eidLength = 15
    override var eidStateLiveData: MutableLiveData<State> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        getSectionedCountriesList()
        // TODO Remove mocking
        mockDataForScreen()
        mockServerDataForKYC()
        if (!parentViewModel?.amendmentMap.isNullOrEmpty()) {
            getKYCDataFromServer()
        }
        validator?.setValidationListener(this)
        parentViewModel?.identity?.let { populateState(it) }
    }

    private fun mockDataForScreen() {
        val identity = Identity()
        identity.citizenNumber = "784198653158182"
        identity.expirationDate =
            DateUtils.stringToDate("230628", "yyMMdd")
        identity.dateOfBirth =
            DateUtils.stringToDate("860319", "yyMMdd")
        identity.gender = Gender.Male
        identity.nationality = "Indian"
        identity.givenName = "Hiral Joshi"
        parentViewModel?.identity = identity
    }

    private fun mockServerDataForKYC() {
        launch {
            delay(3000)
            state.previousCitizenNumber = "784198653158182"
            state.previousFirstName = "Hiral"
            state.previousMiddleName = ""
            state.previousLastName = "Joshi"
            state.previousNationality = "Indian"
            state.previousDateOfBirth = DateUtils.dateToString(
                DateUtils.stringToDate("1986-03-19", "yyyy-MM-dd"), "dd/MM/yyyy",
                DateUtils.TIME_ZONE_Default
            )
            val gender = "M"
            state.previousGender = if (gender.equals(
                    "M",
                    true
                )
            ) getString(Strings.screen_b2c_eid_info_review_display_text_gender_male) else getString(
                Strings.screen_b2c_eid_info_review_display_text_gender_female
            )
            state.previousExpiryDate = DateUtils.dateToString(
                DateUtils.stringToDate("2023-06-28", "yyyy-MM-dd"), "dd/MM/yyyy",
                DateUtils.TIME_ZONE_Default
            )
            delay(500)
            validator?.toValidate()
        }
    }

    override fun handlePressOnView(id: Int) {
        if (id == R.id.btnTouchId)
            handlePressOnConfirmBtn()
        else {
            clickEvent.setValue(id)
        }
    }

    private fun handlePressOnConfirmBtn() {
        parentViewModel?.identity?.let {
            when {
                TextUtils.isEmpty(it.givenName) || TextUtils.isEmpty(it.nationality) -> {
                    clickEvent.setValue(eventErrorInvalidEid)
                }
                !it.isExpiryDateValid -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_expired_card),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_expired_card)
                    )
                    clickEvent.setValue(eventErrorExpiredEid)
                }
                !it.isDateOfBirthValid -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_under_age),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_under_age)
                    )
                    clickEvent.setValue(eventErrorUnderAge)
                    trackEvent(KYCEvents.EID_UNDER_AGE_18.type)
                }
                it.nationality.equals("USA", true) || it.isoCountryCode2Digit.equals(
                    "US",
                    true
                ) -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_from_us),
                        body = getString(Strings.screen_kyc_information_error_text_description_from_us)
                    )
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(eventErrorFromUsa)
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
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_sanctioned_country),
                        body = getString(Strings.screen_kyc_information_error_text_description_sanctioned_country)
                    )
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(eventErrorFromUsa)
                    trackEvent(KYCEvents.KYC_PROHIBITED_CITIIZEN.type)
                }
                parentViewModel?.document != null && it.citizenNumber != parentViewModel?.document?.identityNo -> {
                    state.toast =
                        "Your EID doesn't match with the current EID.^${AlertType.DIALOG.name}"
                }
                else -> {
                    performUploadDocumentsRequest(false) {
                    }
                }
            }
        }
    }

    override fun updateLabels(title: String, body: String) {
        errorTitle = title
        errorBody = body
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        uploadDocuments(result)
    }

    private fun uploadDocuments(result: IdentityScannerResult) {
        if (!result.document.files.isNullOrEmpty() && result.document.files.size < 3) {
            val fileFront = File(result.document.files[0].croppedFile)
            val fileBack = File(result.document.files[1].croppedFile)

            parentViewModel?.paths?.clear()
            parentViewModel?.paths?.add(result.document.files[0].croppedFile)
            parentViewModel?.paths?.add(result.document.files[1].croppedFile)

            val fileFrontReqBody = RequestBody.create(MediaType.parse("image/*"), fileFront)
            val partFront =
                MultipartBody.Part.createFormData("files_f", fileFront.name, fileFrontReqBody)

            val fileBackReqBody = RequestBody.create(MediaType.parse("image/*"), fileBack)
            val partBack =
                MultipartBody.Part.createFormData("files_b", fileBack.name, fileBackReqBody)
            launch {
                state.loading = true
                when (val response = repository.detectCardData(partFront, partBack)) {

                    is RetroApiResponse.Success -> {
                        val data = response.data.data
                        if (data != null) {
                            val identity = Identity()
                            identity.nationality = data.nationality
                            identity.gender =
                                if (data.sex.equals("M", true)) Gender.Male else Gender.Female
                            identity.givenName = data.names
                            trackEventWithAttributes(
                                SessionManager.user,
                                eidExpireDate = getFormattedDate(data.expiration_date)
                            )
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
                        }
                    }
                    is RetroApiResponse.Error -> {
                        if (null == parentViewModel?.identity)
                            state.toast =
                                "${response.error.message}^${AlertType.DIALOG_WITH_FINISH.name}"
                        else {
                            eidStateLiveData.postValue(State.error(response.error.message))
//                            state.toast =
//                                "${response.error.message}^${AlertType.DIALOG_WITH_FINISH.name}"
                        }
                        parentViewModel?.paths?.forEach { filePath ->
                            File(filePath).deleteRecursively()
                        }
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

    private fun getKYCDataFromServer() {
        launch {
            state.loading = true
            when (val response = repository.getCustomerKYCData(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    /*state.previousFirstName = response.data.data?.firstName
                    state.previousMiddleName = response.data.data?.lastName
                    state.previousLastName = response.data.data?.lastName*/
                    splitLastNamesForPrepopulateData(response.data.data?.fullName ?: "")
                    state.previousNationality = response.data.data?.nationality
                    response.data.data?.dob?.let {
                        state.previousDateOfBirth = DateUtils.dateToString(
                            DateUtils.stringToDate(it, "yyyy-MM-dd"), "dd/MM/yyyy",
                            DateUtils.TIME_ZONE_Default
                        )
                    }
                    state.previousGender = if (response.data.data?.gender.equals(
                            "M",
                            true
                        )
                    ) getString(Strings.screen_b2c_eid_info_review_display_text_gender_male) else getString(
                        Strings.screen_b2c_eid_info_review_display_text_gender_female
                    )
                    response.data.data?.dateExpiry?.let {
                        state.previousExpiryDate = DateUtils.dateToString(
                            DateUtils.stringToDate(it, "yyyy-MM-dd"), "dd/MM/yyyy",
                            DateUtils.TIME_ZONE_Default
                        )
                    }
                    state.previousCitizenNumber = response.data.data?.identityNo
                    delay(500)
                    validator?.toValidate()
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    fun performUploadDocumentsRequest(
        fromInformationErrorFragment: Boolean,
        success: (message: String) -> Unit
    ) {
        parentViewModel?.identity?.let {
            if (it.expirationDate == null) {
                clickEvent.setValue(eventEidExpiryDateIssue)
            } else {
                launch {
                    val request = UploadDocumentsRequest(
                        documentType = "EMIRATES_ID",
                        firstName = state.firstName,
                        middleName = if (state.middleName.isNotBlank()) state.middleName else null,
                        lastName = if (state.lastName.isNotBlank()) state.lastName else null,
                        dateExpiry = it.expirationDate,
                        dob = it.dateOfBirth,
                        fullName = getFullName(),
                        gender = it.gender.mrz.toString(),
                        nationality = it.isoCountryCode3Digit.toUpperCase(),
                        identityNo = it.citizenNumber,
                        filePaths = parentViewModel?.paths ?: arrayListOf(),
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
                                        clickEvent.setValue(eventEidUpdate)
                                        trackEvent(KYCEvents.KYC_ID_CONFIRMED.type)
                                    }
                                }
                                EIDStatus.NOT_SET -> {
                                    if (fromInformationErrorFragment) {
                                        success.invoke("success")
                                    } else {
                                        SessionManager.eidStatus = EIDStatus.VALID
                                        clickEvent.setValue(eventNext)
                                        trackEvent(KYCEvents.KYC_ID_CONFIRMED.type)
                                    }
                                }
                                else -> {
                                    if (fromInformationErrorFragment) {
                                        success.invoke("success")
                                    } else {
                                        SessionManager.eidStatus = EIDStatus.VALID
                                        clickEvent.setValue(eventNext)
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
                                        eventAlreadyUsedEid.toString(),
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

    private fun splitLastNamesForPrepopulateData(lastNames: String) {
        val parts = lastNames.trim().split(" ")
        state.previousFirstName = parts[0]
        when {
            parts.size == 2 -> {
                state.previousLastName = parts[1]
            }
            parts.size > 2 -> {
                state.previousLastName = ""
                state.previousMiddleName = parts[1]
                var x = 2
                while (x < parts.size) {
                    if (state.previousLastName?.isEmpty() == true) {
                        state.previousLastName = parts[x]
                    } else {
                        state.previousLastName = state.previousLastName + " " + parts[x]
                    }
                    x++
                }
            }
        }
    }

    private fun splitLastNames(lastNames: String) {
        val parts = lastNames.trim().split(" ")
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

    private fun populateState(identity: Identity?) {
        identity?.let {
            splitLastNames(it.givenName + " " + it.sirName)
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality = it.nationality
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.nationality.equals("USA", true)
            state.dateOfBirth =
                DateUtils.reformatToLocalString(it.dateOfBirth, DateUtils.DEFAULT_DATE_FORMAT)
            state.dateOfBirthValid = it.isDateOfBirthValid
            state.expiryDate =
                DateUtils.reformatToLocalString(it.expirationDate, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDateValid = it.isExpiryDateValid
            state.genderValid = true
            if (parentViewModel?.identity?.citizenNumber?.length != eidLength && !Utils.isValidEID(
                    parentViewModel?.identity?.citizenNumber
                )
            ) {
                clickEvent.setValue(eventCitizenNumberIssue)
            } else {
                state.citizenNumber = getFormattedCitizenNumber(it.citizenNumber)
            }
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
            state.dobCalendar = Calendar.getInstance().apply {
                time = it.dateOfBirth
            }
            state.expiryCalendar = Calendar.getInstance().apply {
                time = it.expirationDate
            }
            validator?.toValidate()
        }
    }

    private fun getFormattedCitizenNumber(citizenNo: String?): String {
        return citizenNo?.let {
            state.caption =
                getString(Strings.screen_b2c_eid_info_review_display_text_edit_sub_title).format(
                    parentViewModel?.name?.value
                )

            val builder = StringBuilder()
            if (hasValidPart(it, 0, 2)) {
                builder.append(it.subSequence(0..2))
                builder.append("-")
            }
            if (hasValidPart(it, 3, 6)) {
                builder.append(it.subSequence(3..6))
                builder.append("-")
            }
            if (hasValidPart(it, 7, 13)) {
                builder.append(it.subSequence(7..13))
                builder.append("-")
            }
            if (hasValidPart(it, 14, 14))
                builder.append(it.subSequence(14..14))
            return@let builder.toString()
        } ?: ""
    }

    fun invalidateFields() {
        state.firstName = ""
        state.middleName = ""
        state.lastName = ""
        state.nationality = ""
        state.dateOfBirth = ""
        state.gender = ""
        state.citizenNumber = ""
        state.caption = ""
        state.valid = false
        state.citizenNumberValid = false
        state.fullNameValid = false
        state.nationalityValid = false
        state.dateOfBirthValid = false
        state.genderValid = false
        state.expiryDateValid = true
        state.expiryDate = ""
        //state.isShowMiddleName = false
        //state.isShowLastName = false
    }

    private fun hasValidPart(value: String?, start: Int, end: Int): Boolean {
        return value?.let {
            return (end in start..it.length)
        } ?: false
    }

    override val drawableClickListener = object : OnDrawableClickListener {
        override fun onClick(view: View, target: DrawablePosition) {
            clickEvent.setValue(view.id)
        }
    }

    override fun getGenderOptions(): ArrayList<BottomSheetItem> {
        val list = arrayListOf<BottomSheetItem>()
        list.add(
            BottomSheetItem(
                icon = if (state.gender == getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)) R.drawable.ic_tick else -1,
                title = getString(Strings.screen_b2c_eid_info_review_display_text_gender_male),
                tag = getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)
            )
        )
        list.add(
            BottomSheetItem(
                icon = if (state.gender == getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)) R.drawable.ic_tick else -1,
                title = getString(Strings.screen_b2c_eid_info_review_display_text_gender_female),
                tag = getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)
            )
        )
        return list
    }

    override fun onValidationSuccess(validator: Validator) {
        super.onValidationSuccess(validator)
        state.valid = validator.isValidate.value == true
    }

    override fun onValidationError(validator: Validator) {
        super.onValidationError(validator)
        state.valid = validator.isValidate.value == false
    }
}