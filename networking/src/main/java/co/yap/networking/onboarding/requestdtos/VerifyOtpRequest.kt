package co.yap.networking.onboarding.requestdtos

data class VerifyOtpRequest(val countryCode: String, val mobileNo: String, val otp: String)
