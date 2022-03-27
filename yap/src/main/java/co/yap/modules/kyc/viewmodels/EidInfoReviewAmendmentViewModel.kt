package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.modules.onboarding.interfaces.IEidInfoReviewAmendment
import co.yap.modules.onboarding.states.EidInfoReviewAmendmentState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.SectionedCountriesResponseDTO
import co.yap.networking.customers.responsedtos.UqudoHeader
import co.yap.networking.customers.responsedtos.UqudoPayLoad
import co.yap.networking.customers.responsedtos.V2DocumentDTO
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.widgets.edittext.DrawablePosition
import co.yap.widgets.edittext.OnDrawableClickListener
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.getAge
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.saveEidTemp
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class EidInfoReviewAmendmentViewModel(application: Application) :
    KYCChildViewModel<IEidInfoReviewAmendment.State>(application),
    IEidInfoReviewAmendment.ViewModel, IRepositoryHolder<CustomersRepository>, IValidator,
    Validator.ValidationListener {
    override var validator: Validator? = Validator(null)
    override val repository: CustomersRepository
        get() = CustomersRepository

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewAmendmentState = EidInfoReviewAmendmentState()
    private var sectionedCountries: SectionedCountriesResponseDTO? = null
    override var sanctionedCountry: String = ""
    override var sanctionedNationality: String = ""
    override var errorTitle: String = ""
    override var errorBody: String = ""

    private val eidLength = 15
    override var eidStateLiveData: MutableLiveData<State> = MutableLiveData()
    override var countries: ArrayList<Country> = ArrayList()
    override var populateNationalitySpinnerData: MutableLiveData<ArrayList<Country>> =
        MutableLiveData()

    override var uqudoResponse: MutableLiveData<UqudoTokenResponse> = MutableLiveData()


    override fun onCreate() {
        super.onCreate()
        getAllCountries()
        if (isFromAmendment()) {
            getKYCDataFromServer()
        }
        validator?.setValidationListener(this)
        requestAllAPIs(true)
    }

    override fun handlePressOnView(id: Int) {
        if (id == R.id.btnTouchId)
            handlePressOnConfirmBtn()
        else {
            clickEvent.setValue(id)
        }
    }

    private fun handlePressOnConfirmBtn() {
        parentViewModel?.uqudoIdentity?.value?.let {
            when {
                TextUtils.isEmpty(it.fullName) || TextUtils.isEmpty(it.nationality) -> {
                    clickEvent.setValue(eventErrorInvalidEid)
                }
                state.expiryDateValid.value?.not() == true -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_expired_card),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_expired_card)
                    )
                    clickEvent.setValue(eventErrorExpiredEid)
                }
                !state.isDateOfBirthValid.get() -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_under_age).format(
                            state.ageLimit ?: 18
                        ),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_under_age).format(
                            state.ageLimit ?: 18
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
                    it.digit3CountryCode.equals(
                        sc.data.find { country ->
                            country.isoCountryCode3Digit.equals(
                                it.digit3CountryCode,
                                true
                            )
                        }?.isoCountryCode3Digit,
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

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        //uploadDocuments(result)
    }


    override fun getAllCountries() {
        launch(Dispatcher.Background) {
            val response = repository.getAllCountries()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        populateNationalitySpinnerData.value =
                            Utils.parseCountryList(response.data.data, addOIndex = false)
                        countries =
                            populateNationalitySpinnerData.value as ArrayList<Country>
                        parentViewModel?.identity?.let {
                            state.nationality.value =
                                countries.firstOrNull { country -> country.isoCountryCode2Digit == it.isoCountryCode2Digit }
                        }
                    }

                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                    }
                }
            }
        }
    }

    override fun getKYCDataFromServer() {
        launch {
            state.loading = true
            when (val response = repository.getCustomerKYCData(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.previousFirstName = response.data.data?.firstName ?: ""
                    state.previousMiddleName = response.data.data?.middleName ?: ""
                    state.previousLastName = response.data.data?.lastName ?: ""
                    state.previousNationality = response.data.data?.nationality?.let {
                        countries.firstOrNull { country -> country.isoCountryCode3Digit == it }
                            ?.getName()
                    }
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

    private fun getFormattedCitizenNumber(citizenNo: String?): String {
        return citizenNo?.let {
            state.caption =
                getString(Strings.screen_b2c_eid_info_review_display_text_edit_sub_title)

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
        state.nationality.value = null
        state.dateOfBirth.value = ""
        state.gender = ""
        state.citizenNumber.value = ""
        state.caption = ""
        state.valid = false
        state.citizenNumberValid = false
        state.fullNameValid = false
        state.nationalityValid = false
        state.isDateOfBirthValid.set(false)
        state.genderValid = false
        state.expiryDateValid.value = true
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

    var configureEIDResponse: MutableLiveData<ConfigureEIDResponse> = MutableLiveData()
    override fun requestAllAPIs(callAll: Boolean) {
        requestAllEIDConfigurations(callAll) { senctionedCountryResponse, configurationEIDResponse, uqudoTokenResponse ->
            launch(Dispatcher.Main) {
                state.viewState.postValue(false)
                when {
                    senctionedCountryResponse is RetroApiResponse.Success && configurationEIDResponse is RetroApiResponse.Success && uqudoTokenResponse is RetroApiResponse.Success -> {
                        sectionedCountries = senctionedCountryResponse.data
                        configureEIDResponse.value = configurationEIDResponse.data.data
                        state.ageLimit = configureEIDResponse?.value?.ageLimit
                        handleAgeValidation()
                        state.countryName.set(configureEIDResponse?.value?.country2DigitIsoCode?.let { str ->
                            str.split(",").map { it -> it.trim() }.find {
                                it == "US"
                            }
                        })
                        handleIsUsValidation()
                        state.uqudoToken.value = uqudoTokenResponse.data.data?.accessToken ?: ""

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

    override fun handleIsUsValidation() {
        state.isCountryUS = state.nationality.value?.let {
            it.isoCountryCode2Digit?.contains(
                state.countryName.get() ?: "US"
            )
        } == true
    }

    override fun handleAgeValidation() {
        state.isDateOfBirthValid.set(
            getAge(state.dobCalendar.time) >= state.ageLimit ?: 18
        )
    }

    override fun onValidationSuccess(validator: Validator) {
        super.onValidationSuccess(validator)
        state.valid = validator.isValidate.value == true
    }

    override fun onValidationError(validator: Validator) {
        super.onValidationError(validator)
        state.valid = validator.isValidate.value == false
    }

    override fun isFromAmendment() = parentViewModel?.amendmentMap?.isNullOrEmpty() == false
    override fun populateUqudoState(identity: UqudoPayLoad?) {
        identity?.let {
            val documentBack = it.data?.documents?.get(0)?.scan?.back
            val documentFront = it.data?.documents?.get(0)?.scan?.front
            documentFront?.fullName?.let { it1 -> splitLastNames(it1) }
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality.value = Country(name = documentFront?.nationality ?: "")
            state.nationalityValid =
                state.nationality.value?.getName()?.isNotBlank() == true && !state.isCountryUS
            var DOB: Date = getDateFormatyyMMddToyyyyMMdd(documentBack?.dateOfBirth) ?: Date()
            var EXD: Date = getDateFormatyyMMddToyyyyMMdd(documentBack?.dateOfExpiry) ?: Date()
            state.dateOfBirth.value =
                DateUtils.reformatToLocalString(DOB, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDate =
                DateUtils.reformatToLocalString(EXD, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDateValid.value = EXD?.let { it1 -> isExpiryDateValid(it1) } ?: false
            state.genderValid = true
            if (documentFront?.identityNumber?.length != eidLength && !Utils.isValidEID(
                    documentFront?.identityNumber
                )
            ) {
                clickEvent.setValue(eventCitizenNumberIssue)
            } else {
                state.citizenNumber.value = documentFront?.identityNumber
                parentViewModel?.state?.identityNo?.set(documentFront?.identityNumber)
            }
            state.gender = documentBack?.sex.run {
                when {
                    this == co.yap.modules.kyc.enums.Gender.Male.mrz -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)
                    this == co.yap.modules.kyc.enums.Gender.Female.mrz -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)
                    else -> {
                        state.genderValid = false
                        ""
                    }
                }
            }
            // If Age Limit available in case of Re-Scan, set Age validity again.
            state.ageLimit?.let { limit ->
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
            downloadImage {
                state.viewState.postValue(
                    it.not()
                )
                if (it) {
                    parentViewModel?.uqudoIdentity?.value =
                        V2DocumentDTO(
                            filePaths = arrayListOf(
                                state.frontImage.value ?: "",
                                state.BackImage.value ?: ""
                            ),
                            documentType = "EMIRATES_ID",
                            firstName = state.firstName,
                            middleName = if (state.middleName.isNotBlank()) state.middleName else null,
                            lastName = if (state.lastName.isNotBlank()) state.lastName else null,
                            dateExpiry = EXD,
                            dob = DOB,
                            fullName = documentFront?.fullName ?: "",
                            gender = documentBack?.sex.toString(),
                            digit3CountryCode = documentBack?.nationality ?: "UAE",
                            nationality = documentFront?.nationality ?: "",
                            identityNo = documentFront?.identityNumber
                        )
                } else showToast("unable to download EIDs")
            }

        }
    }

    fun isExpiryDateValid(expirationDate: Date): Boolean {
        return if (expirationDate == null) {
            false.also { it }
        } else !DateUtils.isDatePassed(expirationDate).also {
            it
        }
    }

    fun getCountryCode(countryName: String): String =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName } ?: ""

    fun getDateFormatyyMMddToyyyyMMdd(string: String?): Date {
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
        return Date()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isAccessTokenExpired() {
        state.isExpired.value = false
        val timeInSec = uqudoResponse.value?.expiresIn?.toInt() ?: 0
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

    fun performUqudoUploadDocumentsRequest(
        fromInformationErrorFragment: Boolean,
        success: (message: String) -> Unit
    ) {
        parentViewModel?.uqudoIdentity?.value?.let {
            if (it.dateExpiry == null) {
                clickEvent.setValue(eventEidExpiryDateIssue)
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
                        identityNo = it.identityNo,
                        filePaths = it.filePaths ?: arrayListOf(),
                        countryIsSanctioned = if (fromInformationErrorFragment) fromInformationErrorFragment else null,
                        isAmendment = !parentViewModel?.amendmentMap.isNullOrEmpty()
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

    fun downloadImage(
        success: (success: Boolean) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            downloadImagewithGlide(
                state.payLoadObj.value?.data?.documents?.get(0)?.scan?.frontImageId ?: ""
            ) { successs, resource ->
                if (successs) {
                    state.frontImage.value = resource?.let { context.saveEidTemp(it) }
                    downloadImagewithGlide(
                        state.payLoadObj.value?.data?.documents?.get(0)?.scan?.backImageId ?: ""
                    ) { succsess, ressource ->
                        if (succsess) {
                            state.BackImage.value = ressource?.let {
                                context.saveEidTemp(
                                    it
                                )
                            }
                            success.invoke(true)

                        } else {
                            showToast(
                                "Re-scan your EID"
                            )
                            state.viewState.postValue(false)
                        }
                    }
                } else {
                    showToast(
                        "Re-scan your EID"
                    )
                    state.viewState.postValue(false)
                }
            }
        }
    }

    private fun downloadImagewithGlide(
        imageId: String,
        downloaded: (sucess: Boolean, bitmap: Bitmap?) -> Unit
    ) {
        Glide.with(context)
            .asBitmap()
            .load(
                GlideUrl(
                    "https://id.dev.uqudo.io/api/v1/info/img/$imageId", LazyHeaders.Builder()
                        .addHeader(
                            "Authorization",
                            "Bearer " + uqudoResponse.value?.accessToken ?: ""
                        )
                        .build()
                )
            ).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    downloaded.invoke(true, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    downloaded.invoke(false, null)
                    showToast("Unable to download")

                }
            })
    }
}