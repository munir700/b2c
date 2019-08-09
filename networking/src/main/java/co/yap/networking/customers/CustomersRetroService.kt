package co.yap.networking.customers

import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.customers.requestdtos.SignUpRequest
import co.yap.networking.customers.responsedtos.AccountInfoResponse
import co.yap.networking.customers.responsedtos.GetDocumentsResponse
import co.yap.networking.customers.responsedtos.SignUpResponse
import co.yap.networking.customers.responsedtos.ValidateDeviceResponse
import co.yap.networking.models.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

}