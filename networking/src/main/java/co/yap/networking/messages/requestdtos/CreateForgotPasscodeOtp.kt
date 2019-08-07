package co.yap.networking.messages.requestdtos

data class CreateForgotPasscodeOtp(val destination: String, val emailOTP: Boolean)