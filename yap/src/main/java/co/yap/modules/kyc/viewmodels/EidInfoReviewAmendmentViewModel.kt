package co.yap.modules.kyc.viewmodels

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.modules.kyc.enums.Gender
import co.yap.modules.onboarding.enums.EidInfoEvents
import co.yap.modules.onboarding.interfaces.IEidInfoReviewAmendment
import co.yap.modules.onboarding.states.EidInfoReviewAmendmentState
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
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import kotlinx.coroutines.delay
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
        validator?.setValidationListener(this)
    }

    override fun handlePressOnView(id: Int) {
        if (id == R.id.btnTouchId)
            handlePressOnConfirmBtn()
        else {
            clickEvent.setValue(id)
        }
    }

    private fun handlePressOnConfirmBtn() {
        parentViewModel?.uqudoManager?.getUqudoIdentity()?.let { identity ->
            when {
                TextUtils.isEmpty(getFullName()) || TextUtils.isEmpty(state.nationality.value?.getName()) -> {
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_INVALID_EID.eventId)
                }
                state.isDateOfBirthValid.get().not() -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_under_age).format(
                            state.ageLimit ?: 18
                        ),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_under_age).format(
                            state.ageLimit ?: 18
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
                    sanctionedCountry = state.nationality.value?.getName() ?: ""
                    sanctionedNationality = state.nationality.value?.getName() ?: ""
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_FROM_USA.eventId)
                    trackEvent(KYCEvents.KYC_US_CITIIZEN.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_US)
                }
                sectionedCountries?.let { sc ->
                    state.nationality.value?.isoCountryCode3Digit?.let { digit3Code ->
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
                    sanctionedCountry = state.nationality.value?.getName() ?: ""
                    sanctionedNationality = state.nationality.value?.getName() ?: ""
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_FROM_USA.eventId)
                    trackEvent(KYCEvents.KYC_PROHIBITED_CITIIZEN.type)
                    trackEventWithScreenName(FirebaseEvent.KYC_SANCTIONED)
                }
                state.expiryDateValid.value?.not() == true -> {
                    updateLabels(
                        title = getString(Strings.screen_kyc_information_error_display_text_title_expired_card),
                        body = getString(Strings.screen_kyc_information_error_display_text_explanation_expired_card)
                    )
                    clickEvent.setValue(EidInfoEvents.EVENT_ERROR_EXPIRED_EID.eventId)
                }
                identity.filePaths.isNullOrEmpty() -> state.eidImageDownloaded.value =
                    State.empty("Sorry, it seems that the data extracted is not correct. Please scan again")
                parentViewModel?.document != null && state.citizenNumber.value?.replace(
                    "-",
                    ""
                ) ?: "" != parentViewModel?.document?.identityNo -> {
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
                        parentViewModel?.uqudoIdentity?.value?.let {
                            state.nationality.value =
                                countries.firstOrNull { country -> country.isoCountryCode3Digit == it.digit3CountryCode }
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
                    state.previousCitizenNumber =
                        getFormattedCitizenNumber(response.data.data?.identityNo)
                    delay(500)
                    eidStateLiveData.postValue(State.success(""))
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

    private fun hasValidPart(value: String?, start: Int, end: Int): Boolean {
        return value?.let {
            return (end in start..it.length)
        } ?: false
    }

    var configureEIDResponse: MutableLiveData<ConfigureEIDResponse> = MutableLiveData()
    override fun requestAllAPIs(callAll: Boolean) {
        requestAllEIDConfigurations(callAll) { senctionedCountryResponse, configurationEIDResponse, uqudoTokenResponse ->
            launch(Dispatcher.Main) {
                if (senctionedCountryResponse is RetroApiResponse.Success) {
                    sectionedCountries = senctionedCountryResponse.data
                }
                if (configurationEIDResponse is RetroApiResponse.Success) {

                    configureEIDResponse.value = configurationEIDResponse.data.data
                    state.ageLimit = configureEIDResponse?.value?.ageLimit
                    handleAgeValidation()
                    state.countryName.set(configureEIDResponse?.value?.country2DigitIsoCode?.let { str ->
                        str.split(",").map { it -> it.trim() }.find {
                            it == "US"
                        }
                    })
                    handleIsUsValidation()
                }
                if (uqudoTokenResponse is RetroApiResponse.Success) {
                    uqudoTokenResponse.data.data?.let {
                        parentViewModel?.uqudoManager?.setUqudoToken(
                            it
                        )
                    }
                } else {
                    if (uqudoTokenResponse is RetroApiResponse.Error) {
                        eidStateLiveData.postValue(State.error("Sorry, that didn’t work. Please try again"))
                    } else {
                        if (senctionedCountryResponse is RetroApiResponse.Error) state.toast =
                            senctionedCountryResponse.error.message
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
    override fun populateUqudoState(identity: EidData?) {
        if (parentViewModel?.uqudoManager?.noImageDownloaded() == true) downloadImageInBackground()
        identity?.let {
            val documentBack = it.documents[0].scan?.back
            val documentFront = it.documents[0].scan?.front
            documentFront?.fullName?.let { it1 -> splitLastNames(it1) }
            state.fullNameValid = state.firstName.isNotBlank()
            state.nationality.value = Country(name = documentFront?.nationality ?: "")
            state.nationalityValid =
                state.nationality.value?.getName()?.isNotBlank() == true && !state.isCountryUS
            val EXD = parentViewModel?.uqudoManager?.getExpiryDate()
            val DOB = parentViewModel?.uqudoManager?.getDateOfBirth()
            if (EXD?.let { it1 -> needToShowExpiryDateDialogue(it1) } == true) {
                clickEvent.setValue(EidInfoEvents.EVENT_EID_ABOUT_TO_EXPIRY_DATE_ISSUE.eventId)
            }
            state.dateOfBirth.value =
                DateUtils.reformatToLocalString(DOB, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDate =
                DateUtils.reformatToLocalString(EXD, DateUtils.DEFAULT_DATE_FORMAT)
            state.expiryDateValid.value =
                EXD?.let { it1 -> parentViewModel?.uqudoManager?.isExpiryDateValid(it1) } ?: false
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
                    this == Gender.Male.mrz -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)
                    this == Gender.Female.mrz -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)
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
            state.dobCalendar = Calendar.getInstance().apply {
                time = DOB
            }
            state.expiryCalendar = Calendar.getInstance().apply {
                time = EXD
            }

            state.nationality.value =
                countries.firstOrNull { country -> country.isoCountryCode3Digit == parentViewModel?.uqudoManager?.fetchDocumentBackDate()?.nationality }
            handleAgeValidation()
            handleIsUsValidation()
            validator?.toValidate()

        }
    }


    fun getCountryCode(countryName: String): String =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName } ?: ""

    fun performUqudoUploadDocumentsRequest(
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
                        fullName = getFullName(),
                        gender = if (state.gender == Gender.Male.name) Gender.Male.mrz else Gender.Female.mrz,
                        dateExpiry = state.expiryCalendar.time,
                        dob = state.dobCalendar.time,
                        nationality = state.nationality.value?.isoCountryCode3Digit ?: "",
                        identityNo = state.citizenNumber.value?.replace("-".toRegex(), ""),
                        filePaths = it.filePaths,
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

    fun downloadImageInBackground() {
        launch {
            state.eidImageDownloaded.value = State.loading(null)
            parentViewModel?.uqudoManager?.downloadImage { downloaded, msg ->
                if (downloaded) {
                    state.eidImageDownloaded.value = State.success(null)
                    parentViewModel?.uqudoIdentity?.value =
                        parentViewModel?.uqudoManager?.getUqudoIdentity(isAmendment = true)
                } else state.eidImageDownloaded.value = State.error(msg)
            }
        }
    }
    private fun needToShowExpiryDateDialogue(expiryDate: Date): Boolean {
        var maxAllowExpiryDate = DateUtils.nextDay(
            DateUtils.stringToDate(
                DateUtils.getCurrentDateWithFormat(DateUtils.SERVER_DATE_FULL_FORMAT),
                DateUtils.SERVER_DATE_FULL_FORMAT
            ), state.eidExpireLimitDays.value ?: 0
        )
        if (maxAllowExpiryDate?.let {
                DateUtils.expectedExpiryDateValid(
                    it,
                    expiryDate
                )
            } == true) {
            return false
        }
        state.expiryDateValid.value = false
        return true
    }
}