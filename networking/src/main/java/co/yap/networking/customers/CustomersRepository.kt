package co.yap.networking.customers

import androidx.annotation.NonNull
import co.yap.networking.BaseRepository
import co.yap.networking.CookiesManager
import co.yap.networking.RetroNetwork
import co.yap.networking.customers.models.CityModel
import co.yap.networking.customers.models.dashboardwidget.UpdateWidgetResponse
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfoResponse
import co.yap.networking.customers.responsedtos.beneficiary.BankParamsResponse
import co.yap.networking.customers.responsedtos.billpayment.*
import co.yap.networking.customers.responsedtos.birthinfoamendment.BirthInfoAmendmentResponse
import co.yap.networking.customers.responsedtos.currency.CurrenciesByCodeResponse
import co.yap.networking.customers.responsedtos.currency.CurrenciesResponse
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.EIDDocumentsResponse
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.customers.responsedtos.documents.UqudoTokenResponse
import co.yap.networking.customers.responsedtos.employment_amendment.DocumentResponse
import co.yap.networking.customers.responsedtos.employment_amendment.EmploymentInfoAmendmentResponse
import co.yap.networking.customers.responsedtos.employmentinfo.IndustrySegmentsResponse
import co.yap.networking.customers.responsedtos.featureflag.FeatureFlagResponse
import co.yap.networking.customers.responsedtos.sendmoney.*
import co.yap.networking.customers.responsedtos.tax.TaxInfoResponse
import co.yap.networking.customers.responsedtos.taxinfoamendment.TaxInfoAmendmentResponse
import co.yap.networking.household.responsedtos.ValidateParentMobileResponse
import co.yap.networking.messages.responsedtos.OtpValidationResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.EditBillerRequest
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object CustomersRepository : BaseRepository(), CustomersApi {

    const val URL_SIGN_UP = "/customers/api/profile"
    const val URL_SYSTEM_CONFIGURATION = "/customers/api/system-configuration"
    const val URL_GET_SIGN_UP_COUNTRIES = "/customers/api/sign-up/countries"
    const val URL_SEND_VERIFICATION_EMAIL = "/customers/api/sign-up/email"
    const val URL_ACCOUNT_INFO = "/customers/api/accounts"
    const val URL_POST_DEMOGRAPHIC_DATA_SIGN_IN = "/customers/api/demographics/device-login"
    const val URL_POST_DEMOGRAPHIC_DATA = "/customers/api/demographics/"
    const val URL_VALIDATE_DEMOGRAPHIC_DATA =
        "customers/api/demographics/validate/user-device/{device_id}"
    const val URL_GET_DOCUMENTS = "customers/api/customer-documents"
    const val URL_UPLOAD_DOCUMENTS = "customers/api/v2/documents"
    const val URL_GET_MORE_DOCUMENTS = "customers/api/document-information"
    const val URL_UPLOAD_PROFILE_PICTURE = "customers/api/customers/profile-picture"
    const val URL_DELETE_PROFILE_PICTURE = "customers/api/customers/profile-picture"
    const val URL_VALIDATE_PHONE_NUMBER = "customers/api/validate-mobile-number"
    const val URL_VALIDATE_EMAIL = "customers/api/validate-email"
    const val URL_CHANGE_MOBILE_NUMBER =
        "customers/api/change-mobile-number/{country-code}/{mobile-number}"
    const val URL_CHANGE_VERIFIED_EMAIL = "customers/api/change-email/{email}"
    const val URL_CHANGE_UNVERIFIED_EMAIL = "customers/api/change-unverified-email"
    const val URL_Y2Y_BENEFICIARIES = "customers/api/y2y-beneficiaries"
    const val URL_Y2Y_RECENT_BENEFICIARIES = "customers/api/recent-beneficiaries/y2y"
    const val URL_DELETE_BENEFICIARIE = "customers/api/mastercard/beneficiaries/{cardId}"
    const val URL_TOPUP_BENEFICIARIES = "customers/api/mastercard/beneficiaries"
    const val URL_CREATE_BENEFICIARIY = "customers/api/mastercard/beneficiaries"
    const val URL_CARDS_LIMITS = "customers/api/mastercard/beneficiaries/limits"
    const val URL_GET_COUNTRY_DATA_WITH_ISO_DIGIT =
        "customers/api/countries/CountryCode2Digit/{country-code}"
    const val URL_GET_COUNTRY_TRANSACTION_LIMITS =
        "/customers/api/bank-transfer/transaction/limit"

    const val URL_DETECT = "digi-ocr/detect/"
    const val URL_SAVE_REFERAL_INVITATION = "/customers/api/save-referral-invitation"

    const val URL_GET_ALL_BENEFICIARIES = "/customers/api/beneficiaries/bank-transfer"
    const val URL_GET_RECENT_BENEFICIARIES = "/customers/api/beneficiaries/bank-transfer/recent"
    const val URL_GET_BENEFICIARY_BY_ID = "/customers/api/beneficiaries/{beneficiary-id}"
    const val URL_DELETE_BENEFICIARY_BY_ID =
        "/customers/api/beneficiaries/bank-transfer/{beneficiary-id}"
    const val URL_EDIT_BENEFICIARY_BY_ID = "/customers/api/beneficiaries/bank-transfer"
    const val URL_GET_COUNTRIES = "/customers/api/bank-transfer/countries"
    const val URL_ADD_BENEFICIARY = "/customers/api/beneficiaries/bank-transfer"
    const val URL_SEARCH_BANK_PARAMS = "/customers/api/other_bank/params"
    const val URL_SEARCH_BANKS = "/customers/api/other_bank/query"
    const val URL_VALIDATE_BENEFICIARY = "customers/api/validate/bank-transfer/beneficiary-details"
    const val URL_GET_ALL_COUNTRIES = "customers/api/countries"
    const val URL_GET_ALL_CitIES = "customers/api/countries/cities/{country-code}"
    const val URL_GET_ALL_DOCUMENT_FOR_EMPLOYMENT = "customers/api/employment-document-criteria"

    val URL_GET_TRANSFER_REASONS = "/transactions/api/product-codes/{product-code}/purpose-reasons"
    val URL_INTERNAL_TRANSFER = "/transactions/api/internal-transfer"
    val URL_SEND_MONEY_UAEFT = "/transactions/api/uaefts"
    val URL_GET_FEE = "/transactions/api/product-codes/{product-code}/fees"
    val URL_BENEFICIARY_CHECK_OTP_STATUS = "customers/api/beneficiaries/bank-transfer/otp-req"
    const val URL_HOME_COUNTRY_FX_RATE = "/transactions/api/fxRate"


    val URL_RMT = "transactions/api/rmt"
    val URL_SWIFT = "transactions/api/swift "
    val URL_CASH_PICKUP = "/transactions/api/cashpayout "
    val URL_CASH_PICKUP_PARTNER =
        "customers/api/pickup-partner/currency-code/{currency-code}/country-code/{country-code} "

    const val URL_CURRENCIES_BY_COUNTRY_CODE =
        "customers/api/country/{country}/currencies"

    const val URL_VERIFY_HOUSEHOLD_MOBILE = "customers/api/on-board/verify/household-mobile"
    const val URL_VERIFY_PARENT_HOUSEHOLD_MOBILE = "customers/api/verify/parent-mobile-no/household"
    const val URL_HOUSEHOLD_USER_ONBOARD = "customers/api/on-board/household"
    const val URL_ADD_HOUSEHOLD_EMAIL = "customers/api/on-board/household-email"
    const val URL_CREATE_HOUSEHOLD_PASSCODE = "customers/api/on-board/household-passcode"
    const val URL_SANCTIONED_COUNTRIES = "customers/api/countries/sanctioned"
    const val URL_SUB_ACCOUNT_INVITATION = "customers/api/accept-reject-subaccountinvitation/"
    const val URL_BIRTH_INFO = "customers/api/customer-birth-info"
    const val URL_TAX_INFO = "customers/api/tax-information"
    const val URL_AMENDMENTS_Birth_INFO = "customers/api/customer-birth-info"
    const val URL_AMENDMENTS_TAX_INFO = "customers/api/tax-information"
    const val URL_AMENDMENTS_Employment_INFO = "customers/api/employment-information"
    const val URL_CITIES = "customers/api/cities"
    const val URL_TAX_REASONS = "customers/api/tin-reasons"
    const val URL_GET_QR_CONTACT = "customers/api/customers-info"
    const val URL_KEY_FACTS_STATEMENT = "customers/api/customer-documents-kfs-statement-url"
    const val URL_GET_FAILED_SUBSCRIPTIONS_NOTIFICATIONS =
            "/transactions/api/household/get-subscriptions-notifications"
    //.................... End region of old projects apis................................................

    /*
   * Url's that comes from admin repo
   * */
    const val URL_VERIFY_USERNAME = "/customers/api/verify-user"
    const val URL_FORGOT_PASSCODE = "/customers/api/forgot-password"
    const val URL_VALIDATE_CURRENT_PASSCODE = "/customers/api/user/verify-passcode"
    const val URL_CHANGE_PASSCODE = "/customers/api/user/change-password"
    const val URL_APP_VERSION = "/customers/api/mobile-app-versions"
    const val URL_RESEND_EMAIL = "/customers/api/sign-up/resend/email"

    const val URL_GET_ALL_CURRENCIES = "/customers/api/currencies"
    const val URL_GET_BY_CURRENCY_CODE = "/customers/api/currencies/code/{currencyCode}"

    const val URL_GET_COOLING_PERIOD = "customers/api/cooling-period-duration"
    const val URL_UPDATE_HOME_COUNTRY = "customers/api/customers-info/update-home-country"
    const val URL_TOUR_GUIDES = "customers/api/tour-guides"

    const val URL_GET_ADDITIONAL_DOCUMENT = "customers/api/additional/documents/required"
    const val URL_ADDITIONAL_DOCUMENT_UPLOAD = "customers/api/additional/documents"
    const val URL_ADDITIONAL_QUESTION_ADD = "customers/api/additional/documents/question-answer"
    const val URL_SEND_INVITE_FRIEND = "customers/api/save-invite"
    const val URL_ADDITIONAL_SUBMIT = "customers/api/update-notification-status"
    const val URL_GET_RANKING = "customers/api/fetch-ranking"
    const val URL_COMPLETE_VERIFICATION = "customers/api/v2/profile"
    const val URL_GET_INDUSTRY_SEGMENTS = "customers/api/industry-sub-segments"
    const val URL_SAVE_EMPLOYMENT_INFO = "customers/api/employment-information"
    const val URL_SAVE_EMPLOYMENT_INFO_WITH_DOCUMENTS =
        "customers/api/profile/employment-information"
    const val URL_STOP_RANKING_MSG = "customers/api/stop-display"
    const val URL_DASHBOARD_WIDGETS = "customers/api/getWidgets"
    const val URL_DASHBOARD_WIDGETS_UPDATE = "customers/api/updateWidgets"
    const val URL_UPDATE_PROFILE_FSS = "customers/api/update-profile-on-fss"
    const val URL_VALIDATE_EID = "customers/api/sanction-countries-configuration"
    const val URL_BILL_PROVIDERS = "customers/api/billpayment/biller-categories"
    const val URL_BILLER_CATALOGS = "customers/api/billpayment/biller-catalogs/{category-id}"
    const val URL_BILLER_INPUTS_DETAILS = "customers/api/billpayment/biller-details/{biller-id}"
    const val URL_ADD_BILLER = "customers/api/billpayment/add-biller"
    const val URL_GET_ADDED_BILLS = "customers/api/billpayment/all-added-billers"
    const val URL_DELETE_BILL = "customers/api/billpayment/delete-biller/{id}"
    const val URL_EDIT_BILLER = "/customers/api/billpayment/edit-biller"
    const val URL_GET_AMENDMENT_FIELDS = "customers/api/amendment-fields"
    const val URL_GET_CUSTOMER_KYC_DOCUMENTS = "customers/api/v2/documents"
    const val URL_UPDATE_PASSPORT_AMENDMENT = "customers/api/kyc-amendments/passport"

    //Uqudo API
    const val URL_GET_UQUDO_AUTH_TOKEN = "customers/api/uqudo/get-token"

    const val URL_GET_CUSTOMER_DOCUMENTS =
        "customers/api/eida-data"
    const val URL_GET_EMPLOYMENT_INFORMATION =
        "customers/api/profile/employment-information"

    //Feature Flag
    const val URL_GET_FEATURE_FLAG =
        "yapsuper/feature/YAP-UAE-B2C/customer/{customer_id}/email/{email}"

    private val api: CustomersRetroService =
        RetroNetwork.createService(CustomersRetroService::class.java)

    override suspend fun signUp(signUpRequest: SignUpRequest): RetroApiResponse<SignUpResponse> {
        val response = executeSafely(call = { api.signUp(signUpRequest) })
        when (response) {
            is RetroApiResponse.Success -> {
                CookiesManager.jwtToken = response.data.data
                CookiesManager.isLoggedIn = true
            }
        }
        return response
    }

    override suspend fun getSystemConfigurations(): RetroApiResponse<BaseListResponse<SystemConfigurationInfo>> =
        executeSafely(call = { api.getSystemConfigurations() })

    override suspend fun sendVerificationEmail(verificationEmailRequest: SendVerificationEmailRequest): RetroApiResponse<OtpValidationResponse> =
        executeSafely(call = { api.sendVerificationEmail(verificationEmailRequest) })

    override suspend fun getAccountInfo(): RetroApiResponse<AccountInfoResponse> =
        executeSafely(call = { api.getAccountInfo() })

    override suspend fun generateOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.generateOTPForDeviceVerification(demographicDataRequest) })

    override suspend fun verifyOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): RetroApiResponse<OtpValidationResponse> =
        executeSafely(call = { api.verifyOTPForDeviceVerification(demographicDataRequest) })

    override suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.postDemographicData(demographicDataRequest) })

    override suspend fun validateDemographicData(deviceId: String): RetroApiResponse<ValidateDeviceResponse> =
        executeSafely(call = { api.validateDemographicData(deviceId) })

    override suspend fun getDocuments(): RetroApiResponse<GetDocumentsResponse> =
        executeSafely(call = { api.getDocuments() })

    override suspend fun saveEmploymentInfoWithDocument(
        employmentInfoRequest: EmploymentInfoRequest,
        files: ArrayList<MultipartBody.Part>, documentTypeList: ArrayList<String>
    ): RetroApiResponse<ApiResponse> =
        employmentInfoRequest.run {
            executeSafely(call = {
                api.submitEmploymentInfoWithDocument(
                    files = files,
                    documentTypes = createPartFromArray(
                        documentTypeList,
                        "documentTypes"
                    ),
                    businessCountries = createPartFromArray(
                        businessCountries ?: listOf(),
                        "businessCountries"
                    ),
                    companyName = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        companyName ?: ""
                    ),
                    employerName = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        employerName ?: ""
                    ),
                    employmentStatus = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        employmentStatus ?: ""
                    ),
                    employmentType = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        employmentType ?: ""
                    ),
                    expectedMonthlyCredit = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        expectedMonthlyCredit ?: ""
                    ),
                    industrySubSegmentCodes = createPartFromArray(
                        industrySegmentCodes ?: listOf(),
                        "industrySubSegmentCode"
                    ),
                    monthlySalary = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        monthlySalary ?: ""
                    ),
                    sponsorName = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        sponsorName ?: ""
                    ),
                    typeOfSelfEmployment = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        typeOfSelfEmployment ?: ""
                    )
                )
            })
        }

    @NonNull
    private fun createPartFromArray(
        values: List<String>,
        param_name: String?
    ): List<MultipartBody.Part> {
        val descriptionList: MutableList<MultipartBody.Part> = ArrayList()
        values.forEach { index ->
            descriptionList.add(MultipartBody.Part.createFormData(param_name, index))
        }
        return descriptionList
    }

    override suspend fun uploadDocuments(document: UploadDocumentsRequest): RetroApiResponse<ApiResponse> =
        document.run {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val files = ArrayList<MultipartBody.Part>()
            filePaths.forEach {
                val file = File(it)
                val reqFile: RequestBody =
                    RequestBody.create(MediaType.parse("image/" + file.extension), file)
                val body = MultipartBody.Part.createFormData("files", file.name, reqFile)
                files.add(body)
            }

            executeSafely(call = {
                api.uploadDocuments(
                    files,
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), documentType),
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), firstName),
                    middleName = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        middleName ?: ""
                    ),
                    lastName = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"), lastName ?: ""
                    ),
                    nationality = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        nationality
                    ),
                    dateExpiry = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        dateFormatter.format(dateExpiry)
                    ),
                    dob = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        dateFormatter.format(dob)
                    ),
                    fullName = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        fullName
                    ),
                    gender = RequestBody.create(MediaType.parse("multipart/form-dataList"), gender),
                    identityNo = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        identityNo
                    ),
                    isAmendment = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        if (isAmendment) "true" else "false"
                    )
                )
            })
        }

    override suspend fun getMoreDocumentsByType(documentType: String): RetroApiResponse<GetMoreDocumentsResponse> =
        executeSafely(call = { api.getMoreDocumentsByType(documentType) })

    override suspend fun uploadProfilePicture(profilePicture: MultipartBody.Part): RetroApiResponse<UploadProfilePictureResponse> =
        executeSafely(call = { api.uploadProfilePicture(profilePicture) })

    override suspend fun validatePhoneNumber(
        countryCode: String,
        mobileNumber: String
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.validatePhoneNumber(countryCode, mobileNumber) })

    override suspend fun changeMobileNumber(
        countryCode: String,
        mobileNumber: String
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.changeMobileNumber(countryCode, mobileNumber) })

    override suspend fun validateEmail(email: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.validateEmail(email) })

    override suspend fun changeVerifiedEmail(email: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.changeVerifiedEmail(email) })

    override suspend fun changeUnverifiedEmail(newEmail: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.changeUnverifiedEmail(newEmail) })

    override suspend fun detectCardData(
        fileFront: MultipartBody.Part,
        fileBack: MultipartBody.Part
    ) =
        executeSafely(call = { api.uploadIdCard(fileFront, fileBack) })

    override suspend fun getY2YBeneficiaries(contacts: List<Contact>) =
        executeSafely(call = { api.getY2YBeneficiaries(contacts) })

    override suspend fun getRecentY2YBeneficiaries() =
        executeSafely(call = { api.getRecentY2YBeneficiaries() })

    /*  send money */

    override suspend fun getRecentBeneficiaries() =
        executeSafely(call = { api.getRecentBeneficiaries() })

    override suspend fun getAllBeneficiaries() = executeSafely(call = { api.getAllBeneficiaries() })

    override suspend fun getCountries() = executeSafely(call = { api.getCountries() })

    override suspend fun getAllCountries() = executeSafely(call = { api.getAllCountries() })

    override suspend fun getAllCities(countryCode: String): RetroApiResponse<CityModel> = executeSafely(call = { api.getAllCities(countryCode) })

    override suspend fun addBeneficiary(beneficiary: Beneficiary): RetroApiResponse<AddBeneficiaryResponseDTO> =
        executeSafely(call = { api.addBeneficiary(beneficiary) })

    override suspend fun validateBeneficiary(beneficiary: Beneficiary): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.validateBeneficiary(beneficiary) })

    override suspend fun editBeneficiary(beneficiary: Beneficiary?): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.editBeneficiary(beneficiary) })

    override suspend fun deleteBeneficiaryFromList(beneficiaryId: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.deleteBeneficiaryById(beneficiaryId) })

    override suspend fun getTopUpBeneficiaries() =
        executeSafely(call = { api.getTopUpBeneficiaries() })

    override suspend fun deleteBeneficiary(cardId: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.deleteBeneficiary(cardId) })

    override suspend fun createBeneficiary(createBeneficiaryRequest: CreateBeneficiaryRequest): RetroApiResponse<CreateBeneficiaryResponse> =
        executeSafely(call = { api.createBeneficiary(createBeneficiaryRequest) })

    override suspend fun getCardsLimit(): RetroApiResponse<CardsLimitResponse> =
        executeSafely(call = { api.getCardsLimit() })

    override suspend fun getCurrenciesByCountryCode(country: String) =
        executeSafely(call = { api.getCurrenciesByCountryCode(country) })

    override suspend fun findOtherBank(otherBankQuery: OtherBankQuery): RetroApiResponse<RAKBankModel> =
        executeSafely(call = { api.findOtherBank(otherBankQuery) })

    override suspend fun getOtherBankParams(countryName: String): RetroApiResponse<BankParamsResponse> =
        executeSafely(call = { api.getOtherBankParams(countryName) })

    /* Household */
    override suspend fun verifyHouseholdMobile(verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.verifyHouseholdMobile(verifyHouseholdMobileRequest) })

    override suspend fun verifyHouseholdParentMobile(verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest
    ): RetroApiResponse<ValidateParentMobileResponse> =
            executeSafely(call = {
                api.verifyHouseholdParentMobile(
                    verifyHouseholdMobileRequest.mobileNo,
                        verifyHouseholdMobileRequest
                )
            })

    override suspend fun onboardHousehold(householdOnboardRequest: HouseholdOnboardRequest?): RetroApiResponse<HouseholdOnBoardingResponse> =
            executeSafely(call = { api.onboardHouseholdUser(householdOnboardRequest) })

    override suspend fun addHouseholdEmail(addHouseholdEmailRequest: AddHouseholdEmailRequest): RetroApiResponse<ValidateParentMobileResponse> =
            executeSafely(call = { api.addHouseholdEmail(addHouseholdEmailRequest) })

    override suspend fun createHouseholdPasscode(createPassCodeRequest: CreatePassCodeRequest): RetroApiResponse<ValidateParentMobileResponse> =
            executeSafely(call = { api.createHouseholdPasscode(createPassCodeRequest) })

    override suspend fun getCountryDataWithISODigit(countryCodeWith2Digit: String): RetroApiResponse<CountryDataWithISODigit> =
            executeSafely(call = { api.getCountryDataWithISODigit(countryCodeWith2Digit) })

    override suspend fun getCountryTransactionLimits(
        countryCode: String,
        currencyCode: String
    ): RetroApiResponse<CountryLimitsResponseDTO> =
        executeSafely(call = { api.getCountryTransactionLimits(countryCode, currencyCode) })

    override suspend fun getSectionedCountries(): RetroApiResponse<SectionedCountriesResponseDTO> =
        executeSafely(call = { api.getSectionedCountries() })

    override suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse> =
        executeSafely(call = { api.verifyUsername(username) })

    override suspend fun forgotPasscode(forgotPasscodeRequest: ForgotPasscodeRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.forgotPasscode(forgotPasscodeRequest) })

    override suspend fun validateCurrentPasscode(verifyPasscodeRequest: VerifyPasscodeRequest): RetroApiResponse<OtpValidationResponse> =
        executeSafely(call = { api.validateCurrentPasscode(verifyPasscodeRequest) })

    override suspend fun changePasscode(changePasscodeRequest: ChangePasscodeRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.changePasscode(changePasscodeRequest) })

    override suspend fun appUpdate(): RetroApiResponse<AppUpdateResponse> =
        executeSafely(call = { api.appUpdate() })

    override suspend fun getSubAccountInviteStatus(notificationStatus: String): RetroApiResponse<SubAccountInvitationResponse> =
            executeSafely(call = { api.subAccountInvitation(notificationStatus) })

    override suspend fun saveReferalInvitation(saveReferalRequest: SaveReferalRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.saveReferalInvitation(saveReferalRequest) })

    override suspend fun getCities(): RetroApiResponse<CitiesModel> =
        executeSafely(call = { api.getCities() })

    override suspend fun getTaxReasons(): RetroApiResponse<TaxReasonResponse> =
        executeSafely(call = { api.getTaxReasons() })

    override suspend fun saveBirthInfo(birthInfoRequest: BirthInfoRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.saveBirthInfo(birthInfoRequest) })

    override suspend fun saveTaxInfo(taxInfoRequest: TaxInfoRequest): RetroApiResponse<TaxInfoResponse> =
        executeSafely(call = { api.saveTaxInfo(taxInfoRequest) })

    override suspend fun getAmendmentsBirthInfo(accountUuid: String): RetroApiResponse<BaseResponse<BirthInfoAmendmentResponse>> =
        executeSafely(call = { api.getAmendmentsBirthInfo(accountUuid) })

    override suspend fun getAmendmentsTaxInfo(accountUuid: String): RetroApiResponse<BaseResponse<TaxInfoAmendmentResponse>> =
        executeSafely(call = { api.getAmendmentsTaxInfo(accountUuid) })

    override suspend fun getAmendmentsEmploymentInfo(accountUuid: String): RetroApiResponse<BaseResponse<EmploymentInfoAmendmentResponse>> {
        return executeSafely(call = { api.getAmendmentsEmploymentInfo(accountUuid) })
        /*val empRes = EmploymentInfoAmendmentResponse(
            businessCountries = listOf("AF", "AX"),
            companyName = "Apple",
            employmentStatus = "Self-Employed",
            expectedMonthlyCredit = "900.0",
            industrySubSegmentCode = "USED CARS",
            monthlySalary = "1000.0"
        )
        val empOtherRes = EmploymentInfoAmendmentResponse(
            employmentStatus = "OTHER",
            employmentType = "RETIRED",
            expectedMonthlyCredit = "300.0",
            monthlySalary = "1000.0",
            sponsorName = "Microsoft",
            documents = arrayListOf(Document(DocumentTypes.PROOF_OF_INCOME.name, "https://yap/customer_data/1000002709/documents/1638797811931_1638797788318.jpg","pdf", "Proof of Income", "Upload salary certificate, Pay slip, Increment Letter or Labor Contract"))
        )
        val response = BaseResponse<EmploymentInfoAmendmentResponse>()
        //response.data = empRes
        response.data = empOtherRes
        return RetroApiResponse.Success(200, response)*/
    }

    override suspend fun getAllCurrenciesConfigs(): RetroApiResponse<CurrenciesResponse> =
        executeSafely(call = { api.getAllCurrencies() })

    override suspend fun getCurrencyByCode(currencyCode: String?): RetroApiResponse<CurrenciesByCodeResponse> =
        executeSafely(call = { api.getCurrencyByCode(currencyCode ?: "") })

    override suspend fun resendVerificationEmail(): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.resendVerificationEmail() })

    override suspend fun removeProfilePicture(): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.removeProfilePicture() })

    override suspend fun getCoolingPeriod(smCoolingPeriodRequest: SMCoolingPeriodRequest): RetroApiResponse<SMCoolingPeriodResponseDTO> =
        executeSafely(call = {
            api.getCoolingPeriod(
                smCoolingPeriodRequest.beneficiaryId,
                smCoolingPeriodRequest.productCode
            )
        })

    override suspend fun getSubscriptionsNotifications() =
            TransactionsRepository.executeSafely(call = { api.getSubscriptionsNotifications() })

    override suspend fun getQRContact(qrContactRequest: QRContactRequest): RetroApiResponse<QRContactResponse> =
        executeSafely(call = { api.getQRContact(qrContactRequest) })

    override suspend fun updateHomeCountry(homeCountry: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.updateHomeCountry(UpdateHomeCountryRequest(homeCountry)) })

    override suspend fun updateFxRate(fxRate: FxRateRequest): RetroApiResponse<FxRateResponse> =
        executeSafely(call = { api.updateFxRate(fxRate) })

    override suspend fun updateTourGuideStatus(tourGuide: TourGuideRequest): RetroApiResponse<UpdateTourGuideResponse> =
        executeSafely(call = { api.updateTourGuideStatus(tourGuide) })

    override suspend fun getTourGuides(): RetroApiResponse<TourGuideResponse> =
        executeSafely(call = { api.getTourGuides() })

    override suspend fun getAdditionalInfoRequired(): RetroApiResponse<AdditionalInfoResponse> =
        executeSafely(call = { api.getAdditionalInfoRequired() })

    override suspend fun uploadAdditionalDocuments(uploadAdditionalInfo: UploadAdditionalInfo): RetroApiResponse<ApiResponse> =
        uploadAdditionalInfo.run {
            val reqFile: RequestBody =
                RequestBody.create(
                    MediaType.parse(
                        uploadAdditionalInfo.contentType
                            ?: "image/" + uploadAdditionalInfo.files?.extension
                    ),
                    uploadAdditionalInfo.files ?: File(uploadAdditionalInfo.files?.name ?: "")
                )
            val body =
                MultipartBody.Part.createFormData(
                    "files",
                    uploadAdditionalInfo.files?.name,
                    reqFile
                )
            executeSafely(call = {
                api.uploadAdditionalDocuments(
                    files = body,
                    documentType = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        documentType ?: ""
                    )
                )
            })
        }

    override suspend fun uploadAdditionalQuestion(uploadAdditionalInfo: UploadAdditionalInfo): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.uploadAdditionalQuestion(uploadAdditionalInfo)
        })

    override suspend fun sendInviteFriend(sendInviteFriendRequest: SendInviteFriendRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.sendInviteFriend(sendInviteFriendRequest)
        })

    override suspend fun submitAdditionalInfo(uploadAdditionalInfo: UploadAdditionalInfo): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.submitAdditionalInfo(uploadAdditionalInfo)
        })

    override suspend fun getWaitingRanking(): RetroApiResponse<WaitingRankingResponse> =
        executeSafely(call = {
            api.getWaitingRanking()
        })

    override suspend fun completeVerification(completeVerificationRequest: CompleteVerificationRequest): RetroApiResponse<SignUpResponse> =
        executeSafely(call = {
            api.completeVerification(completeVerificationRequest)
        })

    override suspend fun getIndustrySegments(): RetroApiResponse<IndustrySegmentsResponse> =
        executeSafely(call = {
            api.getIndustriesSegments()
        })

    override suspend fun saveEmploymentInfo(employmentInfoRequest: EmploymentInfoRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.submitEmploymentInfo(employmentInfoRequest)
        })

    override suspend fun stopRankingMsgRequest(): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.stopRankingMsgRequest()
        })

    override suspend fun getDashboardWidget(): RetroApiResponse<BaseListResponse<WidgetData>> =
        executeSafely(call = {
            api.getDashboardWidget()
        })

    override suspend fun updateDashboardWidget(list: List<WidgetData>): RetroApiResponse<UpdateWidgetResponse> =
        executeSafely(call = {
            api.updateDashboardWidget(list)
        })

    override suspend fun updateCardName(cardNameRequest: CardNameRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.updateCardName(cardNameRequest)
        })

    override suspend fun getBillProviders(): RetroApiResponse<BillProviderResponse> =
        executeSafely(call = {
            api.getBillProviders()
        })

    override suspend fun getBillerCatalogs(categoryId: String): RetroApiResponse<BillerCatalogResponse> =
        executeSafely(call = { api.getBillerCatalogs(categoryId) })

    override suspend fun getBillerInputDetails(billerId: String): RetroApiResponse<BillerDetailResponse> =
        executeSafely(call = { api.getBillerInputsDetails(billerId) })

    override suspend fun addBiller(billerInformation: AddBillerInformationRequest): RetroApiResponse<BillAddedResponse> =
        executeSafely(call = { api.addBiller(billerInformation) })

    override suspend fun getAddedBills(): RetroApiResponse<BillResponse> =
        executeSafely(call = { api.getAddedBills() })

    override suspend fun deleteBill(id: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.deleteBill(id) })

    override suspend fun editBiller(editBillerRequest: EditBillerRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = {
            api.editBiller(editBillerRequest)
        })

    override suspend fun getEIDConfigurations(): RetroApiResponse<BaseResponse<ConfigureEIDResponse>> =
        executeSafely(call = {
            api.getEIDConfigurations()
        })

    override suspend fun getMissingInfoList(accountUuid: String): RetroApiResponse<BaseListResponse<AmendmentFields>> {
        return executeSafely(call = { api.getMissingInfoList(accountUuid) })
    }

    override suspend fun getCustomerKYCData(accountUuid: String): RetroApiResponse<BaseResponse<EIDDocumentsResponse>> {
        return executeSafely(call = { api.getCustomerKYCData(accountUuid) })
    }

    override suspend fun uploadPassportAmendments(request: PassportRequest): RetroApiResponse<ApiResponse> =
        request.run {
            val reqFile: RequestBody =
                RequestBody.create(
                    MediaType.parse(
                        request.contentType
                            ?: "image/" + request.files?.extension
                    ),
                    request.files ?: File(request.files?.name ?: "")
                )
            val body =
                MultipartBody.Part.createFormData(
                    "files",
                    request.files?.name,
                    reqFile
                )
            executeSafely(call = {
                api.uploadPassportAmendments(
                    files = body,
                    passportNumber = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"), passportNumber ?: ""
                    ),
                    passportExpiryDate = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"), passportExpiryDate ?: ""
                    ),
                    passportIssueDate = RequestBody.create(
                        MediaType.parse("multipart/form-dataList"), passportIssueDate ?: ""
                    )
                )
            })
        }

    override suspend fun getCustomerDocuments(accountUuid: String?) =
        executeSafely(call = { api.getCustomerDocuments(accountUuid) })

    override suspend fun getEmploymentInfo() =
        executeSafely { api.getEmploymentInfo() }

    override suspend fun getAllDocumentsForEmploymentAmendment(): RetroApiResponse<BaseListResponse<DocumentResponse>> =
        executeSafely { api.getAllDocumentsForEmploymentAmendment() }

    override suspend fun getUqudoAuthToken(): RetroApiResponse<BaseResponse<UqudoTokenResponse>> =
        executeSafely(call = { api.getUqudoAuthToken() })

    override suspend fun getAppCountries(): RetroApiResponse<BaseListResponse<Country>> =
        executeSafely { api.getAppCountries() }

    override suspend fun getKeyFactStatement(): RetroApiResponse<TaxInfoResponse> =
        executeSafely(call = { api.getKeyFactStatement() })

    override suspend fun getFeatureFlag(
        customer_id: String,
        email: String
    ): RetroApiResponse<BaseResponse<FeatureFlagResponse>> =
        executeSafely { api.getFeatureFlag(customer_id, email) }
}