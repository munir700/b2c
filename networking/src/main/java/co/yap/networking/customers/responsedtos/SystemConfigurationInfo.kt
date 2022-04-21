package co.yap.networking.customers.responsedtos


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SystemConfigurationInfo(
    @SerializedName("createdOn")
    val createdOn: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("isActive")
    val isActive: Boolean? = false,
    @SerializedName("key")
    val key: String? = "",
    @SerializedName("value")
    val value: String? = ""
) : Parcelable