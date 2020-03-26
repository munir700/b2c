package co.yap.networking.authentication.requestdtos

import com.google.gson.annotations.SerializedName

data class SwitchProfileRequest(@SerializedName("account_uuid") val uuid: String)