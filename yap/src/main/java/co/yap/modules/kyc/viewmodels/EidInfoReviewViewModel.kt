package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.enums.DocScanStatus
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
import com.digitify.identityscanner.modules.docscanner.enums.DocumentType
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EidInfoReviewViewModel(application: Application) : KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository
        get() = CustomersRepository

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: EidInfoReviewState = EidInfoReviewState()

    override fun onCreate() {
        super.onCreate()
//        state.titleName[0] = parentViewModel?.name
        state.titleName[0] = parentViewModel?.identity?.identity?.givenName
    }

    override fun onResume() {
        super.onResume()
        parentViewModel?.let { populateState(it.identity) }
    }

    override fun handlePressOnRescanBtn() {
        clickEvent.setValue(EVENT_RESCAN)
    }

    override fun handlePressOnConfirmBtn() {
        parentViewModel?.identity?.identity?.let {
            val expiry = it.expirationDate.run { DateUtils.toDate(day, month, year) }
            when {
                DateUtils.isDatePassed(expiry) -> clickEvent.setValue(EVENT_ERROR_EXPIRED_EID)
                DateUtils.getAge(it.dateOfBirth.run { DateUtils.toDate(day, month, year) }) < 18 -> clickEvent.setValue(
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
        parentViewModel?.identity = result
        populateState(result)
    }

    private fun performUploadDocumentsRequest() {
        parentViewModel?.identity?.let {
            launch {
                val request = UploadDocumentsRequest(
                    documentType = if (it.document.type == DocumentType.EID) "EMIRATES_ID" else "PASSPORT",
                    firstName = it.identity.givenName,
                    lastName = it.identity.sirName,
                    dateExpiry = it.identity.expirationDate.run { DateUtils.toDate(day, month, year) },
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
            state.nationalityValid = state.nationality.isNotBlank() && !state.nationality.equals("USA", false)

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