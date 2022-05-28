package co.yap.modules.onboarding.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import co.yap.yapcore.enums.AccountType
import java.util.*

@Keep
data class OnboardingData(
    @SerializedName("countryCode")
    var countryCode: String,
    @SerializedName("mobileNo")
    var mobileNo: String,
    @SerializedName("passcode")
    var passcode: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("accountType")
    var accountType: AccountType,
    @SerializedName("ibanNumber")
    var ibanNumber: String? = null,
    @SerializedName("formattedMobileNumber")
    var formattedMobileNumber: String,
    @SerializedName("token")
    var token: String? = ""
) : Serializable {
    @SerializedName("startTime")
    var startTime: Date? = null
    @SerializedName("elapsedOnboardingTime")
    var elapsedOnboardingTime: Long = 0
}
