package co.yap.networking.store.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(
    var id: Int,
    @SerializedName("title")
    var name: String,
    @SerializedName("description")
    var desc: String,
    @SerializedName("urlToImage")
    var image: Int,
    var storeIcon: Int
) : Parcelable

@Parcelize
data class StoreParent(
    @SerializedName("totalResults")
    var id: Int,
    @SerializedName("status")
    var name: String,
    @SerializedName("articles")
    var stores: List<Store>
) : ApiResponse(), Parcelable