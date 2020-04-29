package co.yap.networking.customers

import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.beneficiary.TopUpBeneficiariesResponse
import co.yap.networking.customers.responsedtos.sendmoney.*
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Body

interface CustomersApi {
    suspend fun signUp(signUpRequest: SignUpRequest): RetroApiResponse<SignUpResponse>
    suspend fun sendVerificationEmail(verificationEmailRequest: SendVerificationEmailRequest): RetroApiResponse<ApiResponse>
    suspend fun getAccountInfo(): RetroApiResponse<AccountInfoResponse>
    suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse>
    suspend fun validateDemographicData(deviceId: String): RetroApiResponse<ValidateDeviceResponse>
    suspend fun uploadDocuments(document: UploadDocumentsRequest): RetroApiResponse<ApiResponse>
    suspend fun getDocuments(): RetroApiResponse<ApiResponse>
    suspend fun validateEmail(email: String): RetroApiResponse<ApiResponse>
    suspend fun getMoreDocumentsByType(documentType: String): RetroApiResponse<ApiResponse>
    suspend fun uploadProfilePicture(profilePicture: MultipartBody.Part): RetroApiResponse<UploadProfilePictureResponse>
    suspend fun validatePhoneNumber(
        countryCode: String,
        mobileNumber: String
    ): RetroApiResponse<ApiResponse>

    suspend fun changeMobileNumber(
        countryCode: String,
        mobileNumber: String
    ): RetroApiResponse<ApiResponse>

    suspend fun changeVerifiedEmail(email: String): RetroApiResponse<ApiResponse>
    suspend fun changeUnverifiedEmail(newEmail: String): RetroApiResponse<ApiResponse>
    suspend fun detectCardData(file: MultipartBody.Part): RetroApiResponse<ApiResponse>
    suspend fun getY2YBeneficiaries(contacts: List<Contact>): RetroApiResponse<Y2YBeneficiariesResponse>
    suspend fun getRecentY2YBeneficiaries(): RetroApiResponse<RecentBeneficiariesResponse>
    suspend fun getTopUpBeneficiaries(): RetroApiResponse<TopUpBeneficiariesResponse>
    suspend fun deleteBeneficiary(cardId: String): RetroApiResponse<ApiResponse>
    suspend fun createBeneficiary(createBeneficiaryRequest: CreateBeneficiaryRequest): RetroApiResponse<CreateBeneficiaryResponse>
    suspend fun getCardsLimit(): RetroApiResponse<CardsLimitResponse>

    /*  send money */
    suspend fun getRecentBeneficiaries(): RetroApiResponse<GetAllBeneficiaryResponse>
    suspend fun getAllBeneficiaries(): RetroApiResponse<GetAllBeneficiaryResponse>
    suspend fun getAllCountries(): RetroApiResponse<CountryModel>
    suspend fun addBeneficiary(beneficiary: Beneficiary): RetroApiResponse<AddBeneficiaryResponseDTO>
    suspend fun validateBeneficiary(beneficiary: Beneficiary): RetroApiResponse<ApiResponse>
    suspend fun editBeneficiary(beneficiary: Beneficiary?): RetroApiResponse<ApiResponse>
    suspend fun deleteBeneficiaryFromList(beneficiaryId: String): RetroApiResponse<ApiResponse>

    suspend fun getCurrenciesByCountryCode(country: String): RetroApiResponse<ApiResponse>
    suspend fun findOtherBank(otherBankQuery: OtherBankQuery): RetroApiResponse<ApiResponse>
    suspend fun getOtherBankParams(countryName: String): RetroApiResponse<ApiResponse>

    suspend fun getSectionedCountries(): RetroApiResponse<SectionedCountriesResponseDTO>
    /* Household */

    suspend fun verifyHouseholdMobile(verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyHouseholdParentMobile(
        mobileNumber: String?, verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest
    ): RetroApiResponse<ApiResponse>

    suspend fun onboardHousehold(householdOnboardRequest: HouseholdOnboardRequest): RetroApiResponse<HouseholdOnBoardingResponse>
    suspend fun addHouseholdEmail(addHouseholdEmailRequest: AddHouseholdEmailRequest): RetroApiResponse<ApiResponse>
    suspend fun createHouseholdPasscode(createPassCodeRequest: CreatePassCodeRequest): RetroApiResponse<ApiResponse>
    suspend fun getCountryDataWithISODigit(countryCodeWith2Digit: String): RetroApiResponse<Country>
    suspend fun getCountryTransactionLimits(
        countryCode: String,
        currencyCode: String
    ): RetroApiResponse<CountryLimitsResponseDTO>

    suspend fun getSubAccountInviteStatus(notificationStatus: String): RetroApiResponse<SubAccountInvitationResponse>

    suspend fun saveReferalInvitation(@Body saveReferalRequest: SaveReferalRequest): RetroApiResponse<ApiResponse>

    /*
    * fun that comes from admin repo to be replaced
    * */
    suspend fun verifyUsername(username: String): RetroApiResponse<VerifyUsernameResponse>

    suspend fun forgotPasscode(forgotPasscodeRequest: ForgotPasscodeRequest): RetroApiResponse<ApiResponse>
    suspend fun validateCurrentPasscode(passcode: String): RetroApiResponse<ApiResponse>
    suspend fun changePasscode(newPasscode: String): RetroApiResponse<ApiResponse>
    suspend fun appUpdate(): RetroApiResponse<AppUpdateResponse>
}
