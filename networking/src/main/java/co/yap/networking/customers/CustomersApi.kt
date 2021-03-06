package co.yap.networking.customers

import co.yap.networking.customers.models.CityModel
import co.yap.networking.customers.models.dashboardwidget.UpdateWidgetResponse
import co.yap.networking.customers.models.dashboardwidget.WidgetData
import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.additionalinfo.AdditionalInfoResponse
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.beneficiary.TopUpBeneficiariesResponse
import co.yap.networking.customers.responsedtos.billpayment.*
import co.yap.networking.customers.responsedtos.birthinfoamendment.BirthInfoAmendmentResponse
import co.yap.networking.customers.responsedtos.currency.CurrenciesByCodeResponse
import co.yap.networking.customers.responsedtos.currency.CurrenciesResponse
import co.yap.networking.customers.responsedtos.documents.ConfigureEIDResponse
import co.yap.networking.customers.responsedtos.documents.EIDDocumentsResponse
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
import co.yap.networking.models.BaseResponse
import co.yap.networking.models.BaseListResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.EditBillerRequest
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import okhttp3.MultipartBody
import retrofit2.http.Body

interface CustomersApi {
    suspend fun signUp(signUpRequest: SignUpRequest): RetroApiResponse<SignUpResponse>
    suspend fun getSystemConfigurations(): RetroApiResponse<BaseListResponse<SystemConfigurationInfo>>
    suspend fun sendVerificationEmail(verificationEmailRequest: SendVerificationEmailRequest): RetroApiResponse<OtpValidationResponse>
    suspend fun getAccountInfo(): RetroApiResponse<AccountInfoResponse>
    suspend fun postDemographicData(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse>
    suspend fun generateOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): RetroApiResponse<ApiResponse>
    suspend fun verifyOTPForDeviceVerification(demographicDataRequest: DemographicDataRequest): RetroApiResponse<OtpValidationResponse>
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
    suspend fun detectCardData(
        fileFront: MultipartBody.Part,
        fileBack: MultipartBody.Part
    ): RetroApiResponse<ApiResponse>

    suspend fun getY2YBeneficiaries(contacts: List<Contact>): RetroApiResponse<Y2YBeneficiariesResponse>
    suspend fun getRecentY2YBeneficiaries(): RetroApiResponse<RecentBeneficiariesResponse>
    suspend fun getTopUpBeneficiaries(): RetroApiResponse<TopUpBeneficiariesResponse>
    suspend fun deleteBeneficiary(cardId: String): RetroApiResponse<ApiResponse>
    suspend fun createBeneficiary(createBeneficiaryRequest: CreateBeneficiaryRequest): RetroApiResponse<CreateBeneficiaryResponse>
    suspend fun getCardsLimit(): RetroApiResponse<CardsLimitResponse>
    suspend fun removeProfilePicture(): RetroApiResponse<ApiResponse>

    /*  send money */
    suspend fun getRecentBeneficiaries(): RetroApiResponse<GetAllBeneficiaryResponse>
    suspend fun getAllBeneficiaries(): RetroApiResponse<GetAllBeneficiaryResponse>
    suspend fun getCountries(): RetroApiResponse<CountryModel>
    suspend fun getAllCountries(): RetroApiResponse<CountryModel>
    suspend fun getAllCities(countryCode: String): RetroApiResponse<CityModel>
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
    suspend fun verifyHouseholdParentMobile(verifyHouseholdMobileRequest: VerifyHouseholdMobileRequest
    ): RetroApiResponse<ValidateParentMobileResponse>

    suspend fun onboardHousehold(householdOnboardRequest: HouseholdOnboardRequest?): RetroApiResponse<HouseholdOnBoardingResponse>
    suspend fun addHouseholdEmail(addHouseholdEmailRequest: AddHouseholdEmailRequest): RetroApiResponse<ValidateParentMobileResponse>
    suspend fun createHouseholdPasscode(createPassCodeRequest: CreatePassCodeRequest): RetroApiResponse<ValidateParentMobileResponse>
    suspend fun getCountryDataWithISODigit(countryCodeWith2Digit: String): RetroApiResponse<CountryDataWithISODigit>
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
    suspend fun validateCurrentPasscode(verifyPasscodeRequest: VerifyPasscodeRequest): RetroApiResponse<OtpValidationResponse>
    suspend fun changePasscode(changePasscodeRequest: ChangePasscodeRequest): RetroApiResponse<ApiResponse>
    suspend fun appUpdate(): RetroApiResponse<AppUpdateResponse>
    suspend fun getCities(): RetroApiResponse<CitiesModel>
    suspend fun getTaxReasons(): RetroApiResponse<TaxReasonResponse>
    suspend fun saveBirthInfo(birthInfoRequest: BirthInfoRequest): RetroApiResponse<ApiResponse>
    suspend fun saveTaxInfo(taxInfoRequest: TaxInfoRequest): RetroApiResponse<TaxInfoResponse>
    suspend fun resendVerificationEmail(): RetroApiResponse<ApiResponse>
    suspend fun getAllCurrenciesConfigs(): RetroApiResponse<CurrenciesResponse>
    suspend fun getCurrencyByCode(currencyCode: String?): RetroApiResponse<CurrenciesByCodeResponse>
    suspend fun getCoolingPeriod(smCoolingPeriodRequest: SMCoolingPeriodRequest): RetroApiResponse<SMCoolingPeriodResponseDTO>
    suspend fun getSubscriptionsNotifications(): RetroApiResponse<BaseListResponse<HomeNotification>>
    suspend fun getQRContact(qrContactRequest: QRContactRequest): RetroApiResponse<QRContactResponse>
    suspend fun updateHomeCountry(homeCountry: String): RetroApiResponse<ApiResponse>
    suspend fun updateFxRate(fxRate: FxRateRequest): RetroApiResponse<FxRateResponse>
    suspend fun updateTourGuideStatus(tourGuide: TourGuideRequest): RetroApiResponse<UpdateTourGuideResponse>
    suspend fun getTourGuides(): RetroApiResponse<TourGuideResponse>
    suspend fun getAdditionalInfoRequired(): RetroApiResponse<AdditionalInfoResponse>
    suspend fun uploadAdditionalDocuments(uploadAdditionalInfo: UploadAdditionalInfo): RetroApiResponse<ApiResponse>
    suspend fun saveEmploymentInfoWithDocument(
        employmentInfoRequest: EmploymentInfoRequest,
        files: ArrayList<MultipartBody.Part>,
        documentTypeList: ArrayList<String>
    ): RetroApiResponse<ApiResponse>

    suspend fun uploadAdditionalQuestion(uploadAdditionalInfo: UploadAdditionalInfo): RetroApiResponse<ApiResponse>
    suspend fun sendInviteFriend(sendInviteFriendRequest: SendInviteFriendRequest): RetroApiResponse<ApiResponse>
    suspend fun submitAdditionalInfo(uploadAdditionalInfo: UploadAdditionalInfo): RetroApiResponse<ApiResponse>
    suspend fun getWaitingRanking(): RetroApiResponse<WaitingRankingResponse>
    suspend fun completeVerification(completeVerificationRequest: CompleteVerificationRequest): RetroApiResponse<SignUpResponse>
    suspend fun getIndustrySegments(): RetroApiResponse<IndustrySegmentsResponse>
    suspend fun saveEmploymentInfo(employmentInfoRequest: EmploymentInfoRequest): RetroApiResponse<ApiResponse>
    suspend fun stopRankingMsgRequest(): RetroApiResponse<ApiResponse>
    suspend fun getDashboardWidget(): RetroApiResponse<BaseListResponse<WidgetData>>
    suspend fun updateDashboardWidget(list: List<WidgetData>): RetroApiResponse<UpdateWidgetResponse>
    suspend fun updateCardName(cardNameRequest: CardNameRequest): RetroApiResponse<ApiResponse>
    suspend fun getMissingInfoList(accountUuid: String): RetroApiResponse<BaseListResponse<AmendmentFields>>
    suspend fun getCustomerKYCData(accountUuid: String): RetroApiResponse<BaseResponse<EIDDocumentsResponse>>
    suspend fun getAmendmentsBirthInfo(accountUuid: String): RetroApiResponse<BaseResponse<BirthInfoAmendmentResponse>>
    suspend fun getAmendmentsTaxInfo(accountUuid: String): RetroApiResponse<BaseResponse<TaxInfoAmendmentResponse>>
    suspend fun getAmendmentsEmploymentInfo(accountUuid: String): RetroApiResponse<BaseResponse<EmploymentInfoAmendmentResponse>>
    suspend fun uploadPassportAmendments(request: PassportRequest): RetroApiResponse<ApiResponse>
    suspend fun getCustomerDocuments(accountUuid: String?): RetroApiResponse<BaseResponse<PassportRequest>>

    //Bill payments feature apis
    suspend fun getBillProviders(): RetroApiResponse<BillProviderResponse>
    suspend fun getBillerCatalogs(categoryId: String): RetroApiResponse<BillerCatalogResponse>
    suspend fun getBillerInputDetails(billerId: String): RetroApiResponse<BillerDetailResponse>
    suspend fun addBiller(billerInformation: AddBillerInformationRequest): RetroApiResponse<BillAddedResponse>
    suspend fun getAddedBills(): RetroApiResponse<BillResponse>
    suspend fun deleteBill(id: String): RetroApiResponse<ApiResponse>
    suspend fun editBiller(editBillerRequest: EditBillerRequest): RetroApiResponse<ApiResponse>
    suspend fun getEIDConfigurations(): RetroApiResponse<BaseResponse<ConfigureEIDResponse>>
    suspend fun getUqudoAuthToken(): RetroApiResponse<BaseResponse<UqudoTokenResponse>>
    suspend fun getEmploymentInfo(): RetroApiResponse<BaseResponse<EmploymentInfoAmendmentResponse>>
    suspend fun getAllDocumentsForEmploymentAmendment(): RetroApiResponse<BaseListResponse<DocumentResponse>>
    suspend fun getAppCountries(): RetroApiResponse<BaseListResponse<Country>>
    suspend fun getKeyFactStatement(): RetroApiResponse<TaxInfoResponse>

    //Feature Flag
    suspend fun getFeatureFlag(
        customer_id: String,
        email: String
    ): RetroApiResponse<BaseResponse<FeatureFlagResponse>>
}
