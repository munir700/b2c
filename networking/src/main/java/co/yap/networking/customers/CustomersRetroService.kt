package co.yap.networking.customers

import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.customers.requestdtos.SignUpRequest
import co.yap.networking.customers.responsedtos.AccountInfoResponse
import co.yap.networking.customers.responsedtos.SignUpResponse
import co.yap.networking.customers.responsedtos.ValidateDeviceResponse
import co.yap.networking.models.ApiResponse
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

}