package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.fragments.CardScanResponse
import co.yap.modules.kyc.fragments.UploadIdCardRetroService
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class KYCHomeViewModel(application: Application) : KYCChildViewModel<IKYCHome.State>(application),
    IKYCHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository
    override val state: KYCHomeState = KYCHomeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.name[0] = parentViewModel?.name
    }

    override fun onResume() {
        super.onResume()
        //TODO Stop Old upload document api call
        requestDocuments()
    }

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnScanCard(id: Int) {
        if (state.eidScanStatus != DocScanStatus.DOCS_UPLOADED && state.eidScanStatus != DocScanStatus.SCAN_COMPLETED) {
            clickEvent.setValue(id)
        }
    }

    override fun handlePressOnSkipButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        uploadDocuments(result)

//        parentViewModel?.identity = result
//        state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
    }

    // TODO Remove this method
    fun uploadDocument(result: IdentityScannerResult) {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(logger).build()
        val retro: Retrofit = Retrofit.Builder()
            .baseUrl("http://172.21.200.181:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
        val service = retro.create(UploadIdCardRetroService::class.java)
        val file = File(result.document.files[1].croppedFile)
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part =
            MultipartBody.Part.createFormData("image", file.name, fileReqBody)
        state.loading = true
        service.uploadIdCard(file = part).enqueue(object : Callback<CardScanResponse> {
            override fun onFailure(call: Call<CardScanResponse>, t: Throwable) {
                state.loading = false
            }

            override fun onResponse(
                call: Call<CardScanResponse>,
                response: Response<CardScanResponse>?
            ) {
                state.loading = false
                if (response?.body()?.success!!) {
                    var identity = Identity()
                    identity.nationality = response.body()?.nationality
                    identity.gender =
                        if (response.body()?.sex.equals("M")) Gender.Male else Gender.Female
                    identity.sirName = response.body()?.surname
                    identity.givenName = response.body()?.names
                    identity.citizenNumber = response.body()?.number
                    identity.expirationDate =
                        DateUtils.stringToDate(response.body()?.expiration_date!!, "yyMMdd")
                    identity.dateOfBirth =
                        DateUtils.stringToDate(response.body()?.date_of_birth!!, "yyMMdd")

                    result.identity = identity
                    parentViewModel?.identity = result
                    state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
                } else {
                    state.toast = getString(
                        idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable
                    )
                }

            }
        })

    }

    fun uploadDocuments(result: IdentityScannerResult) {
        val file = File(result.document.files[1].croppedFile)
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part =
            MultipartBody.Part.createFormData("image", file.name, fileReqBody)
        launch {
            state.loading = true
            when (val response = repository.detectCardData(part)) {

                is RetroApiResponse.Success -> {
                    val data = response.data.data
                    if (data != null) {
                        var identity = Identity()
                        identity.nationality = data.nationality
                        identity.gender =
                            if (data.sex.equals("M")) Gender.Male else Gender.Female
                        identity.sirName = data.surname
                        identity.givenName = data.names
                        identity.expirationDate =
                            DateUtils.stringToDate(data.expiration_date, "yyMMdd")
                        identity.dateOfBirth =
                            DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                        identity.citizenNumber = data.optional1
                        result.identity = identity
                        parentViewModel?.identity = result
                        state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
                    } else {
                        state.toast = getString(
                            idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable
                        )
                    }
                    //}
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun requestDocuments() {
        launch {
            state.loading = true
            when (val response = repository.getDocuments()) {

                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {
                        state.eidScanStatus = DocScanStatus.DOCS_UPLOADED
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }
}