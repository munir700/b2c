package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.kyc.enums.Gender.Female
import co.yap.modules.kyc.enums.Gender.Male
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.getAge
import co.yap.yapcore.helpers.DateUtils.isDatePassed
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
                !state.isDateOfBirthValid.get() -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_under_age).format(
                            state.AgeLimit ?: 18
                        ),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_under_age).format(
                            state.AgeLimit ?: 18
                        )
                    )
                    clickEvent.setValue(eventErrorUnderAge)
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
                    clickEvent.setValue(eventErrorFromUsa)
                    trackEvent(KYCEvents.KYC_US_CITIIZEN.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_US)
                }
                sectionedCountries?.let { sc ->
                    it.isoCountryCode2Digit.equals(
                        sc.data.find { country ->
                            country.isoCountryCode2Digit.equals(
                                it.isoCountryCode2Digit,
                                true
                            )
                        }?.isoCountryCode2Digit,
                        true
                    )
                } ?: true -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_sanctioned_country),
                        body = getString(Strings.screen_kyc_information_error_text_description_sanctioned_country)
                    )
                    sanctionedCountry = it.nationality
                    sanctionedNationality = it.nationality
                    clickEvent.setValue(eventErrorFromUsa)
                    trackEvent(KYCEvents.KYC_PROHIBITED_CITIIZEN.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_SANCTIONED)
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
        //  uploadDocuments(result)

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

    override fun populateState(identity: Identity?) {
        identity?.let {
            splitLastNames(it.givenName + " " + it.sirName)
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality = it.nationality
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.isCountryUS
            state.dateOfBirth =
                DateUtils.reformatToLocalString(it.dateOfBirth, DateUtils.DEFAULT_DATE_FORMAT)
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
                parentViewModel?.state?.identityNo?.set(it.citizenNumber)
            }
            state.gender = it.gender.run {
                when {
                    this == Male.name as com.digitify.identityscanner.core.arch.Gender -> getString(
                        Strings.screen_b2c_eid_info_review_display_text_gender_male
                    )
                    this == Female.name as com.digitify.identityscanner.core.arch.Gender -> getString(
                        Strings.screen_b2c_eid_info_review_display_text_gender_female
                    )
                    else -> {
                        state.genderValid = false
                        ""
                    }
                }
            }
            // If Age Limit available in case of Re-Scan, set Age validity again.
            state.AgeLimit?.let { limit ->
                state.isDateOfBirthValid.set(
                    getAge(identity.dateOfBirth) >= limit
                )
            }
//            state.isDateOfBirthValid.set(getAge(identity.dateOfBirth) >= configureEIDResponse.value?.ageLimit ?: 18)
            val countryName = configureEIDResponse.value?.country2DigitIsoCode?.let { str ->
                str.split(",").map { it -> it.trim() }.find {
                    it.equals("US")
                }
            }
            state.isCountryUS =
                identity.isoCountryCode2Digit.contains(
                    countryName ?: "US"
                )
        }
    }

    override var uqudoResponse: MutableLiveData<UqudoTokenResponse> = MutableLiveData()

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
        state.fullNameValid = false
        state.nationalityValid = false
        state.isDateOfBirthValid.set(false)
        state.genderValid = false
        state.expiryDateValid = true
        state.expiryDate = ""
        state.isCountryUS = false
        //state.isShowMiddleName = false
        //state.isShowLastName = false
    }

    private fun hasValidPart(value: String?, start: Int, end: Int): Boolean {
        return value?.let {
            return (end in start..it.length)
        } ?: false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun requestAllAPIs(callAll: Boolean) {
        requestAllEIDConfigurations(callAll) { senctionedCountryResponse, configurationEIDResponse, uqudoTokenResponse ->
            launch(Dispatcher.Main) {
                state.viewState.postValue(false)
                when {
                    senctionedCountryResponse is RetroApiResponse.Success && configurationEIDResponse is RetroApiResponse.Success && uqudoTokenResponse is RetroApiResponse.Success -> {
                        sectionedCountries = senctionedCountryResponse.data
                        configureEIDResponse.value = configurationEIDResponse.data.data
//                        state.isDateOfBirthValid.set(getAge(identity.dateOfBirth) >= configureEIDResponse.value?.ageLimit ?: 18)
                        state.AgeLimit = configureEIDResponse.value?.ageLimit
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
                        uqudoResponse.value = uqudoTokenResponse.data.data
                        isAccessTokenExpired()
                    }
                    uqudoTokenResponse is RetroApiResponse.Success ->{
                        uqudoResponse.value = uqudoTokenResponse.data.data
                        isAccessTokenExpired()
                    }
                    else -> {
                        if (senctionedCountryResponse is RetroApiResponse.Error)
                            state.toast = senctionedCountryResponse.error.message
                    }
                }
            }
        }

    }

    override fun requestAllEIDConfigurations(
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

    fun extractJwt(token: String?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) showToast("Requires SDK 26") else {
            val parts = token?.split("\\.".toRegex())
            parts?.let { parts ->
                try {
                    val decoder = Base64.getUrlDecoder()
                    val charset = charset("UTF-8")
                    val base64EncodedHeader: String = parts[0]
                    val base64EncodedBody: String = parts[1]
                    val header =
                        String(decoder.decode(base64EncodedHeader.toByteArray(charset)), charset)
                    val payload =
                        String(decoder.decode(base64EncodedBody.toByteArray(charset)), charset)
                    val gson = Gson()
                    val headerObj: UqudoHeader = gson.fromJson(header, UqudoHeader::class.java)
                    val payLoadObj: UqudoPayLoad = gson.fromJson(payload, UqudoPayLoad::class.java)
                    state.uqudoHeaderObj.postValue(headerObj)
                    state.payLoadObj.postValue(payLoadObj)
                    eidStateLiveData.postValue(State.success(""))
                } catch (e: Exception) {
                    "Error parsing JWT: $e"
                }
            }
        }
    }

    override fun populateUqudoState(identity: UqudoPayLoad?) {
        identity?.let {
            val documentBack = it.data?.documents?.get(0)?.scan?.back
            val documentFront = it.data?.documents?.get(0)?.scan?.front
            splitLastNames(documentBack?.primaryId + " " + documentBack?.secondaryId)
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality = documentFront?.nationality ?: ""
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.isCountryUS
            var DOB = getDateFormatyyMMddToyyyyMMdd(documentBack?.dateOfBirth)
            var EXD = getDateFormatyyMMddToyyyyMMdd(documentBack?.dateOfExpiry)
            state.dateOfBirth =
                DateUtils.reformatToLocalString(DOB, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDate =
                DateUtils.reformatToLocalString(EXD, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDateValid = EXD?.let { it1 -> isExpiryDateValid(it1) } ?: false
            state.genderValid = true
            if (documentFront?.identityNumber?.length != eidLength && !Utils.isValidEID(
                    documentFront?.identityNumber
                )
            ) {
                clickEvent.setValue(eventCitizenNumberIssue)
            } else {
                state.citizenNumber = getFormattedCitizenNumber(documentFront?.identityNumber)
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
            state.AgeLimit?.let { limit ->
                state.isDateOfBirthValid.set(
                    DOB?.let { it1 -> getAge(it1) } ?: 18 >= limit
                )
            }
//            state.isDateOfBirthValid.set(getAge(identity.dateOfBirth) >= configureEIDResponse.value?.ageLimit ?: 18)
            val countryName = configureEIDResponse.value?.country2DigitIsoCode?.let { str ->
                str.split(",").map { it -> it.trim() }.find {
                    it.equals("US")
                }
            }
            state.isCountryUS =
                getCountryCode(documentFront?.nationality ?: "").contains(countryName ?: "US")
        }
    }

    fun isExpiryDateValid(expirationDate: Date): Boolean {
        return if (expirationDate == null) {
            false.also { it }
        } else !isDatePassed(expirationDate).also {
            it
        }
    }

    fun getCountryCode(countryName: String): String =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName } ?: ""

    fun getDateFormatyyMMddToyyyyMMdd(string: String?): Date? {
        var inputSDF = SimpleDateFormat("yyMMdd")
        val outputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var date: Date? = null
        try {
//            val dateInput = string?.replace("..".toRegex(), "$0 ")?.trim()?.replace(" ".toRegex(),"-")
            date = inputSDF.parse(string)
            inputSDF.applyPattern("yyyy-mm-dd")
            val newDate = outputSDF.format(date)
            //after changing date format again you can change to string with changed format
            val dateRes: Date = outputSDF.parse(newDate)
            return dateRes
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isAccessTokenExpired() {
        state.isExpired.value = false
        val timeInSec = uqudoResponse.value?.expiresIn?.toInt()?:0
        object : CountDownTimer((timeInSec * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                seconds %= 60
                state.isExpired.value = false
            }

            override fun onFinish() {
                state.isExpired.value = true
            }
        }.start()
    }


}