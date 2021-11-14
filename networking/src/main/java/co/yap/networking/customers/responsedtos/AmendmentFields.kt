package co.yap.networking.customers.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AmendmentFields(
    @SerializedName("sectionName") val sectionName: String?=null,
    @SerializedName("amendments") val amendments: MutableList<String>? = mutableListOf()
):ApiResponse(), Parcelable
