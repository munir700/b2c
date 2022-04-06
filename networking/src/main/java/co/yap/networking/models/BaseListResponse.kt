package co.yap.networking.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
open class BaseListResponse<T : ApiResponse> : ApiResponse(),Parcelable {
    @SerializedName("data")
    var data: MutableList<T>? = mutableListOf()
}