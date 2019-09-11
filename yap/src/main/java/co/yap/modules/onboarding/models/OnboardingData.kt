package co.yap.modules.onboarding.models

import co.yap.modules.onboarding.enums.AccountType
import java.util.*

data class OnboardingData(
    var countryCode: String,
    var mobileNo: String,
    var passcode: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var accountType: AccountType,
    var ibanNumber: String,
    var formattedMobileNumber: String

) {
    var startTime: Date? = null
    var elapsedOnboardingTime: Long = 0
}
