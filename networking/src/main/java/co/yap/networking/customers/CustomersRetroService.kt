package co.yap.networking.customers

import co.yap.networking.customers.models.CityModel
import co.yap.networking.customers.models.dashboardwidget.UpdateWidgetResponse
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfoResponse
import co.yap.networking.customers.responsedtos.beneficiary.BankParamsResponse
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.beneficiary.TopUpBeneficiariesResponse
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
import co.yap.networking.transactions.requestdtos.EditBillerRequest
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CustomersRetroService {

    // User sign up request
    @POST(CustomersRepository.URL_SIGN_UP)
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    //get System configuration settings
    @GET(CustomersRepository.URL_SYSTEM_CONFIGURATION)
    suspend fun getSystemConfigurations(): Response<BaseListResponse<SystemConfigurationInfo>>

    // In onboarding send verification email to verify uer
    @POST(CustomersRepository.URL_SEND_VERIFICATION_EMAIL)
    suspend fun sendVerificationEmail(@Body sendVerificationEmailRequest: SendVerificationEmailRequest): Response<OtpValidationResponse>

    // Get user account(s) Info
    @GET(CustomersRepository.URL_ACCOUNT_INFO)
    suspend fun getAccountInfo(): Response<AccountInfoResponse>

    // Post demographic dataList
    @PUT(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA)
    suspend fun postDemographicData(@Body demographicDataRequest: DemographicDataRequest): Response<ApiResponse>

    // Post demographic dataList
    @POST(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA_SIGN_IN)
    suspend fun generateOTPForDeviceVerification(@Body demographicDataRequest: DemographicDataRequest): Response<ValidateDeviceResponse>

    // Post demographic dataList
    @PUT(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA_SIGN_IN)
    suspend fun verifyOTPForDeviceVerification(@Body demographicDataRequest: DemographicDataRequest): Response<OtpValidationResponse>

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
        @Part("middleName") middleName: RequestBody? = null,
        @Part("lastName") lastName: RequestBody? = null,
        @Part("nationality") nationality: RequestBody,
        @Part("dateExpiry") dateExpiry: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("fullName") fullName: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("identityNo") identityNo: RequestBody,
        @Part("isAmendment") isAmendment: RequestBody
    ): Response<ApiResponse>

    @Multipart
    @POST(CustomersRepository.URL_SAVE_EMPLOYMENT_INFO_WITH_DOCUMENTS)
    suspend fun submitEmploymentInfoWithDocument(
        @Part files: List<MultipartBody.Part>,
        @Part documentTypes: List<MultipartBody.Part>?,
        @Part businessCountries: List<MultipartBody.Part>?,
        @Part("companyName") companyName: RequestBody?,
        @Part("employerName") employerName: RequestBody?,
        @Part("employmentStatus") employmentStatus: RequestBody?,
        @Part("employmentType") employmentType: RequestBody?,
        @Part("expectedMonthlyCredit") expectedMonthlyCredit: RequestBody?,
        @Part industrySubSegmentCodes: List<MultipartBody.Part>?,
        @Part("monthlySalary") monthlySalary: RequestBody?,
        @Part("sponsorName") sponsorName: RequestBody?,
        @Part("typeOfSelfEmployment") typeOfSelfEmployment: RequestBody?
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
    suspend fun validatePhoneNumber(
        @Query("country-code") countryCode: String,
        @Query("mobile-number") mobileNumber: String
    ): Response<ApiResponse>

    @PUT(CustomersRepository.URL_CHANGE_MOBILE_NUMBER)
    suspend fun changeMobileNumber(
        @Path("country-code") countryCode: String,
        @Path("mobile-number") mobileNumber: String
    ): Response<ApiResponse>

    @PUT(CustomersRepository.URL_CHANGE_VERIFIED_EMAIL)
    suspend fun changeVerifiedEmail(@Path("email") email: String): Response<ApiResponse>

    @PUT(CustomersRepository.URL_CHANGE_UNVERIFIED_EMAIL)
    suspend fun changeUnverifiedEmail(@Query("newEmail") newEmail: String): Response<ApiResponse>

    @Multipart
    @POST(CustomersRepository.URL_DETECT)
    suspend fun uploadIdCard(
        @Part fileFront: MultipartBody.Part,
        @Part fileBack: MultipartBody.Part
    ): Response<KycResponse>

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
    suspend fun getCountryDataWithISODigit(@Path("country-code") countryCodeWith2Digit: String): Response<CountryDataWithISODigit>

    @GET(CustomersRepository.URL_GET_COUNTRY_TRANSACTION_LIMITS)
    suspend fun getCountryTransactionLimits(
        @Query("countryCode") countryCode: String,
        @Query("currencyCode") currencyCode: String
    ): Response<CountryLimitsResponseDTO>

    /*  send money */
    @GET(CustomersRepository.URL_GET_RECENT_BENEFICIARIES)
    suspend fun getRecentBeneficiaries(): Response<GetAllBeneficiaryResponse>

    @GET(CustomersRepository.URL_GET_ALL_BENEFICIARIES)
    suspend fun getAllBeneficiaries(): Response<GetAllBeneficiaryResponse>

    @GET(CustomersRepository.URL_GET_COUNTRIES)
    suspend fun getCountries(): Response<CountryModel>

    @GET(CustomersRepository.URL_GET_ALL_COUNTRIES)
    suspend fun getAllCountries(): Response<CountryModel>

    @GET(CustomersRepository.URL_GET_ALL_CitIES)
    suspend fun getAllCities(@Path("country-code") countryCode: String): Response<CityModel>

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
    suspend fun verifyHouseholdParentMobile(
        @Query("mobileNo") mobileNumber: String?,
        @Body verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest
    ): Response<ValidateParentMobileResponse>

    @POST(CustomersRepository.URL_HOUSEHOLD_USER_ONBOARD)
    suspend fun onboardHouseholdUser(@Body householdOnboardRequest: HouseholdOnboardRequest?): Response<HouseholdOnBoardingResponse>

    @POST(CustomersRepository.URL_ADD_HOUSEHOLD_EMAIL)
    suspend fun addHouseholdEmail(@Body addHouseholdEmailRequest: AddHouseholdEmailRequest): Response<ValidateParentMobileResponse>

    @POST(CustomersRepository.URL_CREATE_HOUSEHOLD_PASSCODE)
    suspend fun createHouseholdPasscode(@Body createPassCodeRequest: CreatePassCodeRequest): Response<ValidateParentMobileResponse>

    @POST(CustomersRepository.URL_SAVE_REFERAL_INVITATION)
    suspend fun saveReferalInvitation(@Body saveReferalRequest: SaveReferalRequest): Response<ApiResponse>

    // Verify username
    @POST(CustomersRepository.URL_VERIFY_USERNAME)
    suspend fun verifyUsername(@Query("username") username: String): Response<VerifyUsernameResponse>

    //Forgot passcode request
    @PUT(CustomersRepository.URL_FORGOT_PASSCODE)
    suspend fun forgotPasscode(@Body forgotPasscodeRequest: ForgotPasscodeRequest): Response<ApiResponse>

    //validate current passcode
    @POST(CustomersRepository.URL_VALIDATE_CURRENT_PASSCODE)
    suspend fun validateCurrentPasscode(@Body verifyPasscodeRequest: VerifyPasscodeRequest): Response<OtpValidationResponse>

    //change passcode
    @POST(CustomersRepository.URL_CHANGE_PASSCODE)
    suspend fun changePasscode(@Body changePasscodeRequest: ChangePasscodeRequest): Response<ApiResponse>

    //  App Update
    @GET(CustomersRepository.URL_APP_VERSION)
    suspend fun appUpdate(): Response<AppUpdateResponse>

    //  Sub Account Invitation
    @POST(CustomersRepository.URL_SUB_ACCOUNT_INVITATION + "{notificationStatus}")
    suspend fun subAccountInvitation(@Path("notificationStatus") notificationStatus: String): Response<SubAccountInvitationResponse>

    @GET(CustomersRepository.URL_CITIES)
    suspend fun getCities(): Response<CitiesModel>

    @GET(CustomersRepository.URL_TAX_REASONS)
    suspend fun getTaxReasons(): Response<TaxReasonResponse>

    @POST(CustomersRepository.URL_BIRTH_INFO)
    suspend fun saveBirthInfo(@Body birthInfoRequest: BirthInfoRequest): Response<ApiResponse>

    @POST(CustomersRepository.URL_TAX_INFO)
    suspend fun saveTaxInfo(@Body taxInfoRequest: TaxInfoRequest): Response<TaxInfoResponse>

    @GET(CustomersRepository.URL_AMENDMENTS_Birth_INFO)
    suspend fun getAmendmentsBirthInfo(@Query("accountUuid") accountUuid: String): Response<BaseResponse<BirthInfoAmendmentResponse>>

    @GET(CustomersRepository.URL_AMENDMENTS_TAX_INFO)
    suspend fun getAmendmentsTaxInfo(@Query("accountUuid") accountUuid: String): Response<BaseResponse<TaxInfoAmendmentResponse>>

    @GET(CustomersRepository.URL_AMENDMENTS_Employment_INFO)
    suspend fun getAmendmentsEmploymentInfo(@Query("accountUuid") accountUuid: String): Response<BaseResponse<EmploymentInfoAmendmentResponse>>

    @GET(CustomersRepository.URL_GET_FAILED_SUBSCRIPTIONS_NOTIFICATIONS)
    suspend fun getSubscriptionsNotifications(): Response<BaseListResponse<HomeNotification>>

    @GET(CustomersRepository.URL_GET_ALL_CURRENCIES)
    suspend fun getAllCurrencies(): Response<CurrenciesResponse>

    @GET(CustomersRepository.URL_GET_BY_CURRENCY_CODE)
    suspend fun getCurrencyByCode(@Path("currencyCode") currencyCode: String): Response<CurrenciesByCodeResponse>

    @POST(CustomersRepository.URL_RESEND_EMAIL)
    suspend fun resendVerificationEmail(): Response<ApiResponse>

    // delete profile picture
    @DELETE(CustomersRepository.URL_DELETE_PROFILE_PICTURE)
    suspend fun removeProfilePicture(): Response<ApiResponse>

    @GET(CustomersRepository.URL_GET_COOLING_PERIOD)
    suspend fun getCoolingPeriod(
        @Query("beneficiaryId") beneficiaryId: String,
        @Query("productCode") productCode: String
    ): Response<SMCoolingPeriodResponseDTO>

    @POST(CustomersRepository.URL_GET_QR_CONTACT)
    suspend fun getQRContact(@Body qrContactRequest: QRContactRequest): Response<QRContactResponse>

    @PATCH(CustomersRepository.URL_UPDATE_HOME_COUNTRY)
    suspend fun updateHomeCountry(@Body homeCountry: UpdateHomeCountryRequest): Response<ApiResponse>

    @POST(CustomersRepository.URL_HOME_COUNTRY_FX_RATE)
    suspend fun updateFxRate(@Body fxRate: FxRateRequest): Response<FxRateResponse>

    @POST(CustomersRepository.URL_TOUR_GUIDES)
    suspend fun updateTourGuideStatus(@Body tourGuide: TourGuideRequest): Response<UpdateTourGuideResponse>

    @GET(CustomersRepository.URL_TOUR_GUIDES)
    suspend fun getTourGuides(): Response<TourGuideResponse>

    //Get additional info required
    @GET(CustomersRepository.URL_GET_ADDITIONAL_DOCUMENT)
    suspend fun getAdditionalInfoRequired(): Response<AdditionalInfoResponse>

    // Upload Addition Documents Request
    @Multipart
    @POST(CustomersRepository.URL_ADDITIONAL_DOCUMENT_UPLOAD)
    suspend fun uploadAdditionalDocuments(
        @Part files: MultipartBody.Part,
        @Part("documentType") documentType: RequestBody
    ): Response<ApiResponse>

    @POST(CustomersRepository.URL_ADDITIONAL_QUESTION_ADD)
    suspend fun uploadAdditionalQuestion(@Body uploadAdditionalInfo: UploadAdditionalInfo): Response<ApiResponse>

    @POST(CustomersRepository.URL_SEND_INVITE_FRIEND)
    suspend fun sendInviteFriend(@Body sendInviteFriendRequest: SendInviteFriendRequest): Response<ApiResponse>

    @POST(CustomersRepository.URL_ADDITIONAL_SUBMIT)
    suspend fun submitAdditionalInfo(@Body uploadAdditionalInfo: UploadAdditionalInfo): Response<ApiResponse>

    @GET(CustomersRepository.URL_GET_RANKING)
    suspend fun getWaitingRanking(): Response<WaitingRankingResponse>

    @POST(CustomersRepository.URL_COMPLETE_VERIFICATION)
    suspend fun completeVerification(@Body completeVerificationRequest: CompleteVerificationRequest): Response<SignUpResponse>

    @GET(CustomersRepository.URL_GET_INDUSTRY_SEGMENTS)
    suspend fun getIndustriesSegments(): Response<IndustrySegmentsResponse>

    @POST(CustomersRepository.URL_SAVE_EMPLOYMENT_INFO)
    suspend fun submitEmploymentInfo(@Body employmentInfoRequest: EmploymentInfoRequest): Response<ApiResponse>

    @PUT(CustomersRepository.URL_STOP_RANKING_MSG)
    suspend fun stopRankingMsgRequest(): Response<ApiResponse>

    @GET(CustomersRepository.URL_DASHBOARD_WIDGETS)
    suspend fun getDashboardWidget(): Response<BaseListResponse<WidgetData>>

    @PUT(CustomersRepository.URL_DASHBOARD_WIDGETS_UPDATE)
    suspend fun updateDashboardWidget(@Body dashboardWidgetBody: List<WidgetData>): Response<UpdateWidgetResponse>

    @POST(CustomersRepository.URL_UPDATE_PROFILE_FSS)
    suspend fun updateCardName(@Body cardNameRequest: CardNameRequest): Response<ApiResponse>

    @GET(CustomersRepository.URL_BILL_PROVIDERS)
    suspend fun getBillProviders(): Response<BillProviderResponse>

    @GET(CustomersRepository.URL_BILLER_CATALOGS)
    suspend fun getBillerCatalogs(@Path("category-id") categoryId: String): Response<BillerCatalogResponse>

    @GET(CustomersRepository.URL_BILLER_INPUTS_DETAILS)
    suspend fun getBillerInputsDetails(@Path("biller-id") billerId: String): Response<BillerDetailResponse>

    @POST(CustomersRepository.URL_ADD_BILLER)
    suspend fun addBiller(@Body addBillerInformationRequest: AddBillerInformationRequest): Response<BillAddedResponse>

    @GET(CustomersRepository.URL_GET_ADDED_BILLS)
    suspend fun getAddedBills(): Response<BillResponse>

    @DELETE(CustomersRepository.URL_DELETE_BILL)
    suspend fun deleteBill(@Path("id") id: String): Response<ApiResponse>

    @PUT(CustomersRepository.URL_EDIT_BILLER)
    suspend fun editBiller(@Body editBillerRequest: EditBillerRequest): Response<ApiResponse>

    @GET(CustomersRepository.URL_VALIDATE_EID)
    suspend fun getEIDConfigurations(): Response<BaseResponse<ConfigureEIDResponse>>

    // Get Missing Info
    @GET(CustomersRepository.URL_GET_AMENDMENT_FIELDS)
    suspend fun getMissingInfoList(@Query("accountUuid") accountUuid: String): Response<BaseListResponse<AmendmentFields>>

    //Get Customer KYC Data
    @GET(CustomersRepository.URL_GET_CUSTOMER_KYC_DOCUMENTS)
    suspend fun getCustomerKYCData(@Query("accountUuid") accountUuid: String): Response<BaseResponse<EIDDocumentsResponse>>

    //Get Customer Documents Data (Passport)
    @GET(CustomersRepository.URL_GET_CUSTOMER_DOCUMENTS)
    suspend fun getCustomerDocuments(@Query("accountUuid") accountUuid: String?): Response<BaseResponse<PassportRequest>>

    @Multipart
    @POST(CustomersRepository.URL_UPDATE_PASSPORT_AMENDMENT)
    suspend fun uploadPassportAmendments(
        @Part files: MultipartBody.Part,
        @Part("passportNumber") passportNumber: RequestBody,
        @Part("passportIssueDate") passportIssueDate: RequestBody,
        @Part("passportExpiryDate") passportExpiryDate: RequestBody
    ): Response<ApiResponse>

    @GET(CustomersRepository.URL_GET_EMPLOYMENT_INFORMATION)
    suspend fun getEmploymentInfo(): Response<BaseResponse<EmploymentInfoAmendmentResponse>>

    @GET(CustomersRepository.URL_GET_ALL_DOCUMENT_FOR_EMPLOYMENT)
    suspend fun getAllDocumentsForEmploymentAmendment(): Response<BaseListResponse<DocumentResponse>>
    //Get Uqudo Token
    @GET(CustomersRepository.URL_GET_UQUDO_AUTH_TOKEN)
    suspend fun getUqudoAuthToken(): Response<BaseResponse<UqudoTokenResponse>>

    @GET(CustomersRepository.URL_GET_SIGN_UP_COUNTRIES)
    suspend fun getAppCountries(): Response<BaseListResponse<Country>>

    //Key Facts Statement
    @GET(CustomersRepository.URL_KEY_FACTS_STATEMENT)
    suspend fun getKeyFactStatement(): Response<TaxInfoResponse>

    //feature flag
    @GET(CustomersRepository.URL_GET_FEATURE_FLAG)
    suspend fun getFeatureFlag(
        @Path("customer_id") customer_id: String,
        @Path("email") email: String
    ): Response<BaseResponse<FeatureFlagResponse>>
}