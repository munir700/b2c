package co.yap.networking.customers

import co.yap.networking.BaseRepository
import co.yap.networking.CookiesManager
import co.yap.networking.RetroNetwork
import co.yap.networking.customers.requestdtos.*
import co.yap.networking.customers.responsedtos.*
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
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
    const val URL_TOPUP_BENEFICIARIES = "customers/api/mastercard/beneficiaries"

    const val URL_DETECT = "digi-ocr/detect/"

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
                    RequestBody.create(MediaType.parse("multipart/form-data"), documentType),
                    RequestBody.create(MediaType.parse("multipart/form-data"), firstName),
                    RequestBody.create(MediaType.parse("multipart/form-data"), lastName),
                    RequestBody.create(MediaType.parse("multipart/form-data"), nationality),
                    RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        dateFormatter.format(dateExpiry)
                    ),
                    RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        dateFormatter.format(dob)
                    ),
                    RequestBody.create(MediaType.parse("multipart/form-data"), fullName),
                    RequestBody.create(MediaType.parse("multipart/form-data"), gender),
                    RequestBody.create(MediaType.parse("multipart/form-data"), identityNo)
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

    override suspend fun getTopUpBeneficiaries() =
        executeSafely(call = { api.getTopUpBeneficiaries() })

}