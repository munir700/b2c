package co.yap.networking.admin.requestdtos

import com.google.gson.annotations.SerializedName

data class ForgotPasscodeRequest(@SerializedName("mobileNo") val mobileNo:String,
                                 @SerializedName ("newPassword")val newPassword:String) {
}