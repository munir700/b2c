package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.fragments.CardScanResponse
import co.yap.modules.kyc.fragments.UploadIdCardRetroService
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.enums.DocumentType
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
import java.util.*
import java.util.concurrent.TimeUnit

class EidInfoReviewViewModel(application: Application) :
    KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewState = EidInfoReviewState()

    override fun onCreate() {
        super.onCreate()
        state.titleName[0] = parentViewModel?.identity?.identity?.givenName
        state.titleName[0] = parentViewModel?.name
        parentViewModel?.let { populateState(it.identity) }
    }

    override fun handlePressOnRescanBtn() {
        parentViewModel?.let { populateState(it.identity) }
        clickEvent.setValue(EVENT_RESCAN)
    }

    override fun handlePressOnConfirmBtn() {
        parentViewModel?.identity?.identity?.let {
            //            val expiry = it.expirationDate.run { DateUtils.toDate(day, month, year) }
            when {
                !it.isExpiryDateValid -> clickEvent.setValue(EVENT_ERROR_EXPIRED_EID)
                !it.isDateOfBirthValid -> clickEvent.setValue(EVENT_ERROR_UNDER_AGE)

                it.nationality.equals("USA", true) -> clickEvent.setValue(EVENT_ERROR_FROM_USA)
                else -> {
                    // All checks passed.
                    performUploadDocumentsRequest()
                }
            }
        }
    }

    override fun handleUserRejection(reason: Int) {
        handlePressOnRescanBtn()
    }

    override fun handleUserAcceptance(reason: Int) {
        clickEvent.setValue(EVENT_NEXT_WITH_ERROR)
    }

    override fun onEIDScanningComplete(result: IdentityScannerResult) {
        uploadDocuments(result)
    }

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
                    val identity = Identity()
                    identity.nationality = response.body()?.nationality

                    identity.gender =
                        if (response.body()?.sex.equals("M")) Gender.Male else Gender.Female
                    identity.sirName = response.body()?.surname
                    identity.givenName = response.body()?.names
                    identity.citizenNumber = response.body()?.number
                    val calender = Calendar.getInstance()
//                    calender.time =
                    identity.expirationDate =
                        DateUtils.stringToDate(response.body()?.expiration_date!!, "yyMMdd")
                    identity.dateOfBirth =
                        DateUtils.stringToDate(response.body()?.date_of_birth!!, "yyMMdd")
                    result.identity = identity

                    parentViewModel?.identity = result
                    populateState(result)
                } else {
                    state.toast =
                        getString(Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable)
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
                        val identity = Identity()
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
                        populateState(result)
                    } else {
                        clickEvent.setValue(EVENT_FINISH)
                        state.toast = response.data.errors?.message!!
                        clearData()
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    clickEvent.setValue(EVENT_FINISH)
                }
            }
            state.loading = false
        }
    }

    private fun performUploadDocumentsRequest() {
        parentViewModel?.identity?.let {
            launch {
                val request = UploadDocumentsRequest(
                    documentType = if (it.document.type == DocumentType.EID) "EMIRATES_ID" else "PASSPORT",
                    firstName = it.identity.givenName,
                    lastName = it.identity.sirName,
                    dateExpiry = it.identity.expirationDate,
                    dob = it.identity.dateOfBirth,
                    fullName = it.identity.givenName + " " + it.identity.sirName,
                    gender = it.identity.gender.mrz.toString(),
                    nationality = it.identity.nationality,
                    identityNo = it.identity.citizenNumber,
                    filePaths = it.document.files.run {
                        val files: ArrayList<String> = arrayListOf()
                        forEach { files.add(it.originalFile) }
                        files
                    }
                )

                state.loading = true
                val response = repository.uploadDocuments(request)
                state.loading = false

                when (response) {
                    is RetroApiResponse.Success -> {
                        clickEvent.setValue(EVENT_NEXT)
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                    }
                }
            }
        }
    }

    fun populateState(identity: IdentityScannerResult?) {
        identity?.let {
            state.fullName = it.identity.givenName + " " + it.identity.sirName
            state.fullNameValid = state.fullName.isNotBlank()
            state.nationality = it.identity.nationality
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.nationality.equals("USA", false)
            state.dateOfBirth = DateUtils.dateToString(it.identity.dateOfBirth)
            state.dateOfBirthValid = it.identity.isDateOfBirthValid
            state.expiryDate = DateUtils.dateToString(it.identity.expirationDate)
            state.expiryDateValid = it.identity.isExpiryDateValid
            state.genderValid = true
            state.gender = it.identity.gender.run {
                when {
                    this == Gender.Male -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_male)
                    this == Gender.Female -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_female)
                    else -> getString(Strings.screen_b2c_eid_info_review_display_text_gender_unknown)
                }
            }

        }
    }

    override fun clearData() {
        state.fullName = ""
        state.fullNameValid = false
        state.nationality = ""
        state.nationalityValid = false
        state.dateOfBirth = ""
        state.dateOfBirthValid = false
        state.expiryDate = ""
        state.expiryDateValid = false
        state.genderValid = false
        state.gender = ""
    }
}