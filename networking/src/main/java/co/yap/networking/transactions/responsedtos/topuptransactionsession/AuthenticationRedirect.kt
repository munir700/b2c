package co.yap.networking.transactions.responsedtos.topuptransactionsession

import com.google.gson.annotations.SerializedName

data class AuthenticationRedirect(@SerializedName("Simple3DEnrollmentObject") val simple: Simple3DEnrollmentObject) {
}