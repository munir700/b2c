package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.UploadDocumentsRequest
import co.yap.networking.interfaces.IRepositoryHolder
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
        state.titleName[0] = parentViewModel?.name
    }

    override fun onResume() {
        super.onResume()
        parentViewModel?.let { populateState(it.identity) }
    }

    override fun handlePressOnRescanBtn() {

    }

    override fun handlePressOnConfirmBtn() {
        // TODO: perform mandatory validation checks and then upload docs if passed

        // performUploadDocumentsRequest()
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

                repository.uploadDocuments(request)

            }
        }
    }

    private fun populateState(identity: IdentityScannerResult?) {
        identity?.let {
            state.fullName = it.identity.givenName + " " + it.identity.sirName
            state.fullNameValid = true

            state.nationality = it.identity.nationality
            state.nationalityValid = true

            state.dateOfBirth = it.identity.dateOfBirth.toString()
            state.dateOfBirthValid = it.identity.dateOfBirth.run {
                if (isDateValid) {
                    val age = DateUtils.getAge(day, month, year)
                    age >= 18
                } else false
            }

            state.expiryDate = it.identity.expirationDate.toString()
            state.expiryDateValid = it.identity.expirationDate.run {
                DateUtils.isDatePassed(DateUtils.toDate(day, month, year))
            }

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