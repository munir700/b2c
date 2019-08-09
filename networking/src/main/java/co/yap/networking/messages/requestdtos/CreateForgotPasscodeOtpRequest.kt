package co.yap.networking.messages.requestdtos

data class CreateForgotPasscodeOtpRequest(val destination: String, val emailOTP: Boolean)