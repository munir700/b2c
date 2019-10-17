package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.enums.DocScanStatus
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
import com.digitify.identityscanner.core.mrz.types.Gender
import com.digitify.identityscanner.core.mrz.types.MrzDate
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType
import com.digitify.identityscanner.modules.docscanner.models.Identity
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult
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
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class EidInfoReviewViewModel(application: Application) :
    KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewState = EidInfoReviewState()

    override fun onCreate() {
        super.onCreate()
//        state.titleName[0] = parentViewModel?.name
        state.titleName[0] = parentViewModel?.identity?.identity?.givenName
        parentViewModel?.let { populateState(it.identity) }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun handlePressOnRescanBtn() {
        clickEvent.setValue(EVENT_RESCAN)
    }

    override fun handlePressOnConfirmBtn() {
        parentViewModel?.identity?.identity?.let {
            val expiry = it.expirationDate.run { DateUtils.toDate(day, month, year) }
            when {
                DateUtils.isDatePassed(expiry) -> clickEvent.setValue(EVENT_ERROR_EXPIRED_EID)
                DateUtils.getAge(it.dateOfBirth.run {
                    DateUtils.toDate(
                        day,
                        month,
                        year
                    )
                }) < 18 -> clickEvent.setValue(
                    EVENT_ERROR_UNDER_AGE
                )
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
        uploadDocument(result)
//        parentViewModel?.identity = result
//        populateState(result)
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
                response: Response<CardScanResponse>
            ) {
                state.loading = false
                if(response.body()?.success!!) {
                    val identity = Identity()
                    identity.nationality = response.body()?.nationality

                    identity.gender =
                        if (response.body()?.sex.equals("M")) Gender.Male else Gender.Female
                    identity.sirName = response.body()?.surname
                    identity.givenName = response.body()?.names
                    identity.citizenNumber = response.body()?.number
                    val calender = Calendar.getInstance()
                    calender.time =
                        DateUtils.stringToDate(response.body()?.expiration_date!!, "yyMMdd")
                    identity.expirationDate = MrzDate(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH))
                    calender.time =
                        DateUtils.stringToDate(response.body()?.date_of_birth!!, "yyMMdd")
                    identity.dateOfBirth = MrzDate(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH))
                    result.identity = identity

                    parentViewModel?.identity = result
                     populateState(result)
                    //state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
                }
                else {
                    state.toast = getString(Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable)
                }

            }
        })

    }

    private fun performUploadDocumentsRequest() {
        parentViewModel?.identity?.let {
            launch {
                val request = UploadDocumentsRequest(
                    documentType = if (it.document.type == DocumentType.EID) "EMIRATES_ID" else "PASSPORT",
                    firstName = it.identity.givenName,
                    lastName = it.identity.sirName,
                    dateExpiry = it.identity.expirationDate.run {
                        DateUtils.toDate(
                            day,
                            month,
                            year
                        )
                    },
                    dob = it.identity.dateOfBirth.run { DateUtils.toDate(day, month, year) },
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

    private fun populateState(identity: IdentityScannerResult?) {
        identity?.let {
            state.fullName = it.identity.givenName + " " + it.identity.sirName
            state.fullNameValid = state.fullName.isNotBlank()

            state.nationality = it.identity.nationality
            state.nationalityValid =
                state.nationality.isNotBlank() && !state.nationality.equals("USA", false)

            state.dateOfBirth = it.identity.dateOfBirth.run {
                DateUtils.dateToString(day, month, year)
            }
            state.dateOfBirthValid = it.identity.dateOfBirth.run {
                if (isDateValid) {
                    val age = DateUtils.getAge(day, month, year)
                    age >= 18
                } else false
            }

            state.expiryDate = it.identity.expirationDate.run {
                DateUtils.dateToString(day, month, year)
            }
            state.expiryDateValid = it.identity.expirationDate.run {
                !DateUtils.isDatePassed(DateUtils.toDate(day, month, year))
            }

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
}