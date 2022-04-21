package co.yap.networking.customers.responsedtos


import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

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
) : ApiResponse(), Parcelable {

}