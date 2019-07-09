package co.yap.networking.authentication.requestdtos

data class VerifyOtpRequest(val action: String, val otp: String)