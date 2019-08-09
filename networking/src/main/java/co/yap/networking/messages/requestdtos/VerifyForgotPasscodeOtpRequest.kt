package co.yap.networking.messages.requestdtos

data class VerifyForgotPasscodeOtpRequest(val destination: String, val otp: String, val emailOTP: Boolean) {
}