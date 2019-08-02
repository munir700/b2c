package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.states.EidInfoReviewState
import co.yap.yapcore.helpers.DateUtils
import com.digitify.identityscanner.core.mrz.types.Gender
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult
import java.text.SimpleDateFormat
import java.util.*

class EidInfoReviewViewModel(application: Application) : KYCChildViewModel<IEidInfoReview.State>(application),
    IEidInfoReview.ViewModel {

    override val state: EidInfoReviewState = EidInfoReviewState()

    override fun onCreate() {
        super.onCreate()
        state.titleName[0] = parentViewModel?.name
    }

    override fun onResume() {
        super.onResume()
        parentViewModel?.let { populateState(it.identity) }
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
                    this == Gender.Male -> "Male"
                    this == Gender.Female -> "Female"
                    else -> "Unknown"
                }
            }

        }
    }
}