package co.yap.modules.dashboard.store.young.benefits.adapter

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YoungBenefitsModel(
    @SerializedName("title")
    var benefits: String
) : ApiResponse(), Parcelable

