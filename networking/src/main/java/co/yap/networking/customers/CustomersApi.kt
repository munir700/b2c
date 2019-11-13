package co.yap.networking.customers

import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiariesResponse
import co.yap.networking.customers.responsedtos.beneficiary.TopUpBeneficiariesResponse
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import okhttp3.MultipartBody

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
}