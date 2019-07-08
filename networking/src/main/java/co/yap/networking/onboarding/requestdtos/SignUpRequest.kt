package co.yap.networking.onboarding.requestdtos


data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val countryCode: String,
    val mobileNo: String,
    val email: String,
    val passcode: String,
    val accountType: String
)
