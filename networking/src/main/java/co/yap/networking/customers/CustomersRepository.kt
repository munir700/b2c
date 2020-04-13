package co.yap.networking.customers

import co.yap.networking.BaseRepository
import co.yap.networking.CookiesManager
import co.yap.networking.RetroNetwork
import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.beneficiary.BankParamsResponse
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.customers.responsedtos.household.HouseHoldGetSubscriptionResponseDTO
import co.yap.networking.customers.responsedtos.sendmoney.AddBeneficiaryResponseDTO
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.Country
import co.yap.networking.customers.responsedtos.sendmoney.RAKBankModel
import co.yap.networking.household.responsedtos.ValidateParentMobileResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object CustomersRepository : BaseRepository(), CustomersApi {

    const val URL_SIGN_UP = "/customers/api/profile"
    const val URL_SEND_VERIFICATION_EMAIL = "/customers/api/sign-up/email"
    const val URL_ACCOUNT_INFO = "/customers/api/accounts"
    const val URL_POST_DEMOGRAPHIC_DATA = "/customers/api/demographics/"
    const val URL_VALIDATE_DEMOGRAPHIC_DATA =
        "customers/api/demographics/validate/user-device/{device_id}"
    const val URL_GET_DOCUMENTS = "customers/api/customer-documents"
    const val URL_UPLOAD_DOCUMENTS = "customers/api/v2/documents"
    const val URL_GET_MORE_DOCUMENTS = "customers/api/document-information"
    const val URL_UPLOAD_PROFILE_PICTURE = "customers/api/customers/profile-picture"
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


    // Bank transfer information as per old project integration................................................

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

    val URL_GET_TRANSFER_REASONS = "/transactions/api/product-codes/{product-code}/purpose-reasons"
    val URL_INTERNAL_TRANSFER = "/transactions/api/internal-transfer"
    val URL_SEND_MONEY_UAEFT = "/transactions/api/uaefts"
    val URL_GET_FEE = "/transactions/api/product-codes/{product-code}/fees"
    val URL_BENEFICIARY_CHECK_OTP_STATUS = "customers/api/beneficiaries/bank-transfer/otp-req"


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


    //.................... End region of old projects apis................................................

    /*
   * Url's that comes from admin repo
   * */
    const val URL_VERIFY_USERNAME = "/customers/api/verify-user"
    const val URL_FORGOT_PASSCODE = "/customers/api/forgot-password"
    const val URL_VALIDATE_CURRENT_PASSCODE = "/customers/api/user/verify-passcode"
    const val URL_CHANGE_PASSCODE = "/customers/api/user/change-password"
    const val URL_APP_VERSION = "/customers/api/mobile-app-versions"
    //.................... End region of admin repo urls................................................

    /**
     * House Hold Employee interface APIS (Sub Accounts)
     **/
    const val URL_GET_SUB_ACCOUNTS = "/customers/api/account/get-sub-accounts"
    const val URL_REFUND_REMOVE_HOUSEHOLD =
        "/customers/api/household/refund-remove-household/{UUID}"
    const val URL_RESEND_HOUSEHOLD = "/customers/api/household/resend-household/{UUID}"
    const val URL_GET_PROFILE_HOUSEHOLD_USER = "/customers/api/household/household-user"
    const val URL_GET_HOUSE_HOLD_SUBSCRIPTION = "customers/api/household/get-subscription/{UUID}"
    const val URL_SETUP_HOUSE_HOLD_SUBSCRIPTION =
        "customers/api/household/setup-subscription/{UUID}"
    const val URL_CANCEL_HOUSE_HOLD_SUBSCRIPTION =
        "customers/api/household/cancel-subscription/{UUID}"


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

    override suspend fun sendVerificationEmail(verificationEmailRequest: SendVerificationEmailRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.sendVerificationEmail(verificationEmailRequest) })


    override suspend fun getAccountInfo(): RetroApiResponse<AccountInfoResponse> =
        executeSafely(call = { api.getAccountInfo() })

    override suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.postDemographicData(demographicDataRequest) })

    override suspend fun validateDemographicData(deviceId: String): RetroApiResponse<ValidateDeviceResponse> =
        executeSafely(call = { api.validateDemographicData(deviceId) })

    override suspend fun getDocuments(): RetroApiResponse<GetDocumentsResponse> =
        executeSafely(call = { api.getDocuments() })

    override suspend fun uploadDocuments(document: UploadDocumentsRequest): RetroApiResponse<ApiResponse> =
        document.run {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
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
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), lastName),
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), nationality),
                    RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        dateFormatter.format(dateExpiry)
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/form-dataList"),
                        dateFormatter.format(dob)
                    ),
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), fullName),
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), gender),
                    RequestBody.create(MediaType.parse("multipart/form-dataList"), identityNo)
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

    override suspend fun detectCardData(file: MultipartBody.Part) =
        executeSafely(call = { api.uploadIdCard(file) })

    override suspend fun getY2YBeneficiaries(contacts: List<Contact>) =
        executeSafely(call = { api.getY2YBeneficiaries(contacts) })

    override suspend fun getRecentY2YBeneficiaries() =
        executeSafely(call = { api.getRecentY2YBeneficiaries() })

/*  send money */

    override suspend fun getRecentBeneficiaries() =
        executeSafely(call = { api.getRecentBeneficiaries() })

    override suspend fun getAllBeneficiaries() = executeSafely(call = { api.getAllBeneficiaries() })

    override suspend fun getAllCountries() = executeSafely(call = { api.getAllCountries() })

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

    override suspend fun verifyHouseholdParentMobile(
        mobileNumber: String?, verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest
    ): RetroApiResponse<ValidateParentMobileResponse> =
        executeSafely(call = {
            api.verifyHouseholdParentMobile(
                mobileNumber,
                verifyHouseholdMobileRequest
            )
        })

    override suspend fun onboardHousehold(householdOnboardRequest: HouseholdOnboardRequest): RetroApiResponse<HouseholdOnBoardingResponse> =
        executeSafely(call = { api.onboardHouseholdUser(householdOnboardRequest) })

    override suspend fun addHouseholdEmail(addHouseholdEmailRequest: AddHouseholdEmailRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.addHouseholdEmail(addHouseholdEmailRequest) })

    override suspend fun createHouseholdPasscode(createPassCodeRequest: CreatePassCodeRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.createHouseholdPasscode(createPassCodeRequest) })

    override suspend fun getCountryDataWithISODigit(countryCodeWith2Digit: String): RetroApiResponse<Country> =
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

    override suspend fun validateCurrentPasscode(passcode: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.validateCurrentPasscode(passcode) })

    override suspend fun changePasscode(newPasscode: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.changePasscode(newPasscode) })

    override suspend fun appUpdate(): RetroApiResponse<AppUpdateResponse> =
        executeSafely(call = { api.appUpdate() })


    override suspend fun getSubAccountInviteStatus(notificationStatus: String): RetroApiResponse<SubAccountInvitationResponse> =
        executeSafely(call = { api.subAccountInvitation(notificationStatus) })

    override suspend fun saveReferalInvitation(saveReferalRequest: SaveReferalRequest): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.saveReferalInvitation(saveReferalRequest) })

    //    Get All subaccounts for a IBAN user:
    override suspend fun getSubAccounts(): RetroApiResponse<SubAccounts> =
        executeSafely(call = { api.getSubAccountAccount() })

    override suspend fun getHouseholdUser(uuid: String) =
        executeSafely(call = { api.getHouseholdUser(uuid) })

    // Resend request to  house hold user from IBAN user
    override suspend fun resendRequestToHouseHoldUser(uuid: String?) =
        executeSafely(call = { api.resendRequestToHouseHoldUser(uuid) })

    // Remove house hold user from IBAN Sub Account
    override suspend fun RemoveRefundHouseHoldUser(uuid: String?) =
        executeSafely(call = { api.RemoveRefundHouseHoldUser(uuid) })

    //     Get House Hold user subscription From Iban user
    override suspend fun getHouseHoldSubscription(uuid: String): RetroApiResponse<HouseHoldGetSubscriptionResponseDTO> =
        executeSafely(call = { api.getHouseHoldSubscription(uuid) })

    override suspend fun setUpHouseHoldSubscription(
        uuid: String,
        planType: String, isAutoRenew: Boolean
    ): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.setUpHouseHoldSubscription(uuid, planType, isAutoRenew) })

    override suspend fun cancelHouseHoldSubscription(uuid: String): RetroApiResponse<ApiResponse> =
        executeSafely(call = { api.cancelHouseHoldSubscription(uuid) })

}
