package co.yap.modules.dashboard.addionalinfo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdditionalDocument(
    val id: Int,
    var name: String,
    var isUploaded: Boolean
) : Parcelable