package co.yap.networking.customers

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseListResponse<T : ApiResponse>():ApiResponse(),Parcelable {
    @SerializedName("data") var data: MutableList<T>? = mutableListOf()
}