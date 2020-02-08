package co.yap.networking.transactions.responsedtos.topuptransactionsession

import com.google.gson.annotations.SerializedName

class Check3DEnrollmentSessionData(
    @SerializedName("3DSecureId") val `3DSecureId`: String,
    @SerializedName("3DSecure") val `3DSecure`: Enrollment3DSecure
)