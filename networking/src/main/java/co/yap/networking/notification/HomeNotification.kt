package co.yap.networking.notification

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeNotification(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String?="",
    @SerializedName("action")
    val action: NotificationAction,
    @SerializedName("subtitle")
    val subTitle: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("imgResId")
    val imgResId: Int? = 0,
    @Transient var isPinned: Boolean? = false,
    @Transient var isDeleteAble: Boolean = false
) : ApiResponse(), Parcelable