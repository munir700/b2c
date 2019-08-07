package co.yap.networking.customers

import co.yap.networking.BaseRepository
import co.yap.networking.CookiesManager
import co.yap.networking.RetroNetwork
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.customers.requestdtos.SignUpRequest
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.customers.responsedtos.AccountInfoResponse
import co.yap.networking.customers.responsedtos.SignUpResponse
import co.yap.networking.customers.responsedtos.ValidateDeviceResponse
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
    const val URL_VALIDATE_DEMOGRAPHIC_DATA = "customers/api/demographics/validate/user-device/{device_id}"
    const val URL_UPLOAD_DOCUMENTS = "customers/api/v2/documents"

    private val api: CustomersRetroService = RetroNetwork.createService(CustomersRetroService::class.java)

    override suspend fun signUp(signUpRequest: SignUpRequest): RetroApiResponse<SignUpResponse> {
        val response = executeSafely(call = { api.signUp(signUpRequest) })
        when (response) {
            is RetroApiResponse.Success -> CookiesManager.jwtToken = response.data.data
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

    override suspend fun uploadDocuments(document: UploadDocumentsRequest): RetroApiResponse<ApiResponse> = document.run {
        val dateFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
        val files = ArrayList<MultipartBody.Part>()
        filePaths.forEach {
            val file = File(it)
            val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/" + file.extension), file)
            val body = MultipartBody.Part.createFormData("files", file.name, reqFile)
            files.add(body)
        }

        val response = executeSafely(call = {
            api.uploadDocuments(
                files,
                RequestBody.create(MediaType.parse("multipart/form-data"), documentType),
                RequestBody.create(MediaType.parse("multipart/form-data"), firstName),
                RequestBody.create(MediaType.parse("multipart/form-data"), lastName),
                RequestBody.create(MediaType.parse("multipart/form-data"), nationality),
                RequestBody.create(MediaType.parse("multipart/form-data"), dateFormatter.format(dateExpiry)),
                RequestBody.create(MediaType.parse("multipart/form-data"), dateFormatter.format(dob)),
                RequestBody.create(MediaType.parse("multipart/form-data"), fullName),
                RequestBody.create(MediaType.parse("multipart/form-data"), gender),
                RequestBody.create(MediaType.parse("multipart/form-data"), identityNo)
            )
        })
        response
    }

}