package co.yap.networking.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class BaseResponse<T> : ApiResponse(), Parcelable {
    @SerializedName("data")
    var data: T? = null
}