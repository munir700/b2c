package co.yap.networking.customers

import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.beneficiary.BankParamsResponse
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.beneficiary.TopUpBeneficiariesResponse
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.customers.responsedtos.sendmoney.*
import co.yap.networking.models.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CustomersRetroService {

    // User sign up request
    @POST(CustomersRepository.URL_SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    // In onboarding send verification email to verify uer
    @POST(CustomersRepository.URL_SEND_VERIFICATION_EMAIL)
    suspend fun sendVerificationEmail(@Body sendVerificationEmailRequest: SendVerificationEmailRequest): Response<ApiResponse>


    // Get user account(s) Info
    @GET(CustomersRepository.URL_ACCOUNT_INFO)
    suspend fun getAccountInfo(): Response<AccountInfoResponse>

    // Post demographic dataList
    @PUT(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA)
    suspend fun postDemographicData(@Body demographicDataRequest: DemographicDataRequest): Response<ApiResponse>

    // Validate demographic dataList
    @GET(CustomersRepository.URL_VALIDATE_DEMOGRAPHIC_DATA)
    suspend fun validateDemographicData(@Path("device_id") deviceId: String): Response<ValidateDeviceResponse>

    // Upload Documents Request
    @Multipart
    @POST(CustomersRepository.URL_UPLOAD_DOCUMENTS)
    suspend fun uploadDocuments(
        @Part files: List<MultipartBody.Part>,
        @Part("documentType") documentType: RequestBody,
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("nationality") nationality: RequestBody,
        @Part("dateExpiry") dateExpiry: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("fullName") fullName: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("identityNo") identityNo: RequestBody
    ): Response<ApiResponse>

    // Get Documents
    @GET(CustomersRepository.URL_GET_DOCUMENTS)
    suspend fun getDocuments(): Response<GetDocumentsResponse>

    // Get Documents
    @GET(CustomersRepository.URL_VALIDATE_EMAIL)
    suspend fun validateEmail(@Query("email") email: String): Response<ApiResponse>


    // Get More Documents on profile settings fragment
    @GET(CustomersRepository.URL_GET_MORE_DOCUMENTS)
    suspend fun getMoreDocumentsByType(@Query("documentType") EMIRATES_ID: String): Response<GetMoreDocumentsResponse>

    // upload profile picture
    @Multipart
    @POST(CustomersRepository.URL_UPLOAD_PROFILE_PICTURE)
    suspend fun uploadProfilePicture(@Part profilePicture: MultipartBody.Part): Response<UploadProfilePictureResponse>

    // Get More Documents on profile settings fragment
    @GET(CustomersRepository.URL_VALIDATE_PHONE_NUMBER)
    suspend fun validatePhoneNumber(@Query("country-code") countryCode: String, @Query("mobile-number") mobileNumber: String): Response<ApiResponse>

    @PUT(CustomersRepository.URL_CHANGE_MOBILE_NUMBER)
    suspend fun changeMobileNumber(@Path("country-code") countryCode: String, @Path("mobile-number") mobileNumber: String): Response<ApiResponse>

    @PUT(CustomersRepository.URL_CHANGE_VERIFIED_EMAIL)
    suspend fun changeVerifiedEmail(@Path("email") email: String): Response<ApiResponse>

    @PUT(CustomersRepository.URL_CHANGE_UNVERIFIED_EMAIL)
    suspend fun changeUnverifiedEmail(@Query("newEmail") newEmail: String): Response<ApiResponse>

    @Multipart
    @POST(CustomersRepository.URL_DETECT)
    suspend fun uploadIdCard(@Part file: MultipartBody.Part): Response<KycResponse>

    @POST(CustomersRepository.URL_Y2Y_BENEFICIARIES)
    suspend fun getY2YBeneficiaries(@Body contacts: List<Contact>): Response<Y2YBeneficiariesResponse>

    @GET(CustomersRepository.URL_Y2Y_RECENT_BENEFICIARIES)
    suspend fun getRecentY2YBeneficiaries(): Response<RecentBeneficiariesResponse>


    @GET(CustomersRepository.URL_TOPUP_BENEFICIARIES)
    suspend fun getTopUpBeneficiaries(): Response<TopUpBeneficiariesResponse>

    @DELETE(CustomersRepository.URL_DELETE_BENEFICIARIE)
    suspend fun deleteBeneficiary(@Path("cardId") cardId: String): Response<ApiResponse>

    @POST(CustomersRepository.URL_CREATE_BENEFICIARIY)
    suspend fun createBeneficiary(@Body createBeneficiaryRequest: CreateBeneficiaryRequest): Response<CreateBeneficiaryResponse>

    @GET(CustomersRepository.URL_CARDS_LIMITS)
    suspend fun getCardsLimit(): Response<CardsLimitResponse>

    @GET(CustomersRepository.URL_GET_COUNTRY_DATA_WITH_ISO_DIGIT)
    suspend fun getCountryDataWithISODigit(@Path("country-code") countryCodeWith2Digit: String): Response<Country>

    @GET(CustomersRepository.URL_GET_COUNTRY_TRANSACTION_LIMITS)
    suspend fun getCountryTransactionLimits(@Query("countryCode") countryCode: String, @Query("currencyCode") currencyCode: String): Response<CountryLimitsResponseDTO>

    /*  send money */
    @GET(CustomersRepository.URL_GET_RECENT_BENEFICIARIES)
    suspend fun getRecentBeneficiaries(): Response<GetAllBeneficiaryResponse>

    @GET(CustomersRepository.URL_GET_ALL_BENEFICIARIES)
    suspend fun getAllBeneficiaries(): Response<GetAllBeneficiaryResponse>

    @GET(CustomersRepository.URL_GET_COUNTRIES)
    suspend fun getAllCountries(): Response<CountryModel>

    @POST(CustomersRepository.URL_ADD_BENEFICIARY)
    suspend fun addBeneficiary(@Body beneficiary: Beneficiary): Response<AddBeneficiaryResponseDTO>

    @POST(CustomersRepository.URL_VALIDATE_BENEFICIARY)
    suspend fun validateBeneficiary(@Body beneficiary: Beneficiary): Response<AddBeneficiaryResponseDTO>

    @POST(CustomersRepository.URL_SEARCH_BANKS)
    suspend fun findOtherBank(@Body otherBankQuery: OtherBankQuery): Response<RAKBankModel>

    @GET(CustomersRepository.URL_SEARCH_BANK_PARAMS)
    suspend fun getOtherBankParams(@Query("other_bank_country") countryName: String): Response<BankParamsResponse>

    @PUT(CustomersRepository.URL_EDIT_BENEFICIARY_BY_ID)
    suspend fun editBeneficiary(@Body beneficiary: Beneficiary?): Response<ApiResponse>

    @DELETE(CustomersRepository.URL_DELETE_BENEFICIARY_BY_ID)
    suspend fun deleteBeneficiaryById(@Path("beneficiary-id") beneficiaryId: String): Response<ApiResponse>

    @DELETE(CustomersRepository.URL_CURRENCIES_BY_COUNTRY_CODE)
    suspend fun getCurrenciesByCountryCode(@Path("country") country: String): Response<ApiResponse>

    @GET(CustomersRepository.URL_SANCTIONED_COUNTRIES)
    suspend fun getSectionedCountries(): Response<SectionedCountriesResponseDTO>

    /* Household */

    @POST(CustomersRepository.URL_VERIFY_HOUSEHOLD_MOBILE)
    suspend fun verifyHouseholdMobile(@Body verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest): Response<ApiResponse>

    @POST(CustomersRepository.URL_VERIFY_PARENT_HOUSEHOLD_MOBILE)
    suspend fun verifyHouseholdParentMobile(@Query("mobileNo") mobileNumber: String?, @Body verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest): Response<ApiResponse>

    @POST(CustomersRepository.URL_HOUSEHOLD_USER_ONBOARD)
    suspend fun onboardHouseholdUser(@Body householdOnboardRequest: HouseholdOnboardRequest): Response<HouseholdOnBoardingResponse>

    @POST(CustomersRepository.URL_ADD_HOUSEHOLD_EMAIL)
    suspend fun addHouseholdEmail(@Body addHouseholdEmailRequest: AddHouseholdEmailRequest): Response<ApiResponse>

    @POST(CustomersRepository.URL_CREATE_HOUSEHOLD_PASSCODE)
    suspend fun createHouseholdPasscode(@Body createPassCodeRequest: CreatePassCodeRequest): Response<ApiResponse>


}