package co.yap.networking.customers

import co.yap.networking.authentication.responsedtos.KycResponse
import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.beneficiary.TopUpBeneficiariesResponse
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.models.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CustomersRetroService {

    // User sign up request
    @POST(CustomersRepository.URL_SIGN_UP)
    suspend fun signUp(@Body ignUpRequest: SignUpRequest): Response<SignUpResponse>

    // In onboarding send verification email to verify uer
    @POST(CustomersRepository.URL_SEND_VERIFICATION_EMAIL)
    suspend fun sendVerificationEmail(@Body sendVerificationEmailRequest: SendVerificationEmailRequest): Response<ApiResponse>


    // Get user account(s) Info
    @GET(CustomersRepository.URL_ACCOUNT_INFO)
    suspend fun getAccountInfo(): Response<AccountInfoResponse>

    // Post demographic data
    @PUT(CustomersRepository.URL_POST_DEMOGRAPHIC_DATA)
    suspend fun postDemographicData(@Body demographicDataRequest: DemographicDataRequest): Response<ApiResponse>

    // Validate demographic data
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
}