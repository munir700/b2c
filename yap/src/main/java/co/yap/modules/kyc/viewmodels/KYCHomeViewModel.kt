package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.app.YAPApplication
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.interfaces.IKYCHome
import co.yap.modules.kyc.states.KYCHomeState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.KycResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.dummyEID
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
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
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
        requestDocuments()
        parentViewModel?.name?.value =
            getString(Strings.screen_b2c_kyc_home_display_text_sub_heading).format(parentViewModel?.name?.value)
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
        //uploadDocument(result)
    }

    private fun uploadDocuments(result: IdentityScannerResult) {
        if (!result.document.files.isNullOrEmpty() && result.document.files.size < 3) {

            val file = if (YAPApplication.configManager.buildType == "debug") {
                context.dummyEID()
            } else {
                File(result.document.files[1].croppedFile)
            }
            parentViewModel?.paths?.clear()
            parentViewModel?.paths?.add(result.document.files[0].croppedFile)
            parentViewModel?.paths?.add(result.document.files[1].croppedFile)

            val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file!!)
            val part =
                MultipartBody.Part.createFormData("image", file.name, fileReqBody)
            launch {
                state.loading = true
                when (val response = repository.detectCardData(part)) {

                    is RetroApiResponse.Success -> {
                        val data = response.data.data
                        if (data != null) {
                            val identity = Identity()
                            identity.nationality = data.nationality
                            identity.gender =
                                if (data.sex.equals("M", true)) Gender.Male else Gender.Female
                            identity.sirName = data.surname
                            identity.givenName = data.names
                            identity.expirationDate =
                                DateUtils.stringToDate(data.expiration_date, "yyMMdd")
                            identity.dateOfBirth =
                                DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                            identity.citizenNumber = data.optional1
                            identity.isoCountryCode2Digit = data.isoCountryCode2Digit
                            identity.isoCountryCode3Digit = data.isoCountryCode3Digit
                            result.identity = identity
                            parentViewModel?.identity = identity
                            state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
                        } else {
                            state.toast = "${response.data.errors?.message
                                ?: "Invalid image"}^${AlertType.DIALOG.name}"
                            trackEvent(KYCEvents.EID_FAILURE.type)
                        }
                    }

                    is RetroApiResponse.Error -> {
                        trackEvent(KYCEvents.EID_FAILURE.type)
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                        parentViewModel?.paths?.forEach { filePath ->
                            File(filePath).deleteRecursively()
                        }
                    }
                }

                state.loading = false
            }
        } else {
            state.toast = "${"There is some issue with captured images"}^${AlertType.DIALOG.name}"
        }
    }

    override fun requestDocuments() {
        launch {
            state.loading = true
            when (val response = repository.getDocuments()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) state.eidScanStatus = DocScanStatus.DOCS_UPLOADED
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    // Test start
    //its for local instance testing
    interface UploadIdCardRetroService {
        @Multipart
        @POST(CustomersRepository.URL_DETECT)
        fun detectCardData(@Part file: MultipartBody.Part): Call<KycResponse>
    }

    fun uploadDocument(result: IdentityScannerResult) {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            //.addInterceptor(CookiesInterceptor())
            .addInterceptor(logger)
            .build()
        val retro: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.86:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
        val service = retro.create(UploadIdCardRetroService::class.java)

        if (!result.document.files.isNullOrEmpty() && result.document.files.size < 3) {
            val file = File(result.document.files[1].croppedFile)
            parentViewModel?.paths?.clear()
            parentViewModel?.paths?.add(result.document.files[0].croppedFile)
            parentViewModel?.paths?.add(result.document.files[1].croppedFile)

            val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
            val part =
                MultipartBody.Part.createFormData("image", file.name, fileReqBody)


            service.detectCardData(part).enqueue(object : Callback<KycResponse> {
                override fun onResponse(call: Call<KycResponse>, data: Response<KycResponse>) {
                    val data = data.body()?.data
                    if (data != null) {
                        val identity = Identity()
                        identity.nationality = data.nationality
                        identity.gender =
                            if (data.sex.equals("M", true)) Gender.Male else Gender.Female
                        identity.sirName = data.surname
                        identity.givenName = data.names
                        identity.expirationDate =
                            DateUtils.stringToDate(data.expiration_date, "yyMMdd")
                        identity.dateOfBirth =
                            DateUtils.stringToDate(data.date_of_birth, "yyMMdd")
                        identity.citizenNumber = data.optional1
                        identity.isoCountryCode2Digit = data.isoCountryCode2Digit
                        identity.isoCountryCode3Digit = data.isoCountryCode3Digit
                        result.identity = identity
                        parentViewModel?.identity = identity
                        state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
                    } else {
                        state.toast = "Invalid image"
                        trackEvent(KYCEvents.EID_FAILURE.type)
                    }
                }

                override fun onFailure(call: Call<KycResponse>, t: Throwable) {
                    trackEvent(KYCEvents.EID_FAILURE.type)
                    state.toast = t.toString()
                }
            })

            // test and
        }
        // test and
    }
}