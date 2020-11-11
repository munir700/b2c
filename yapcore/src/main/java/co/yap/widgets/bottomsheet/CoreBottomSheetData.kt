package co.yap.widgets.bottomsheet

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class CoreBottomSheetData(
    @SerializedName("image")
    var sheetImage: Int? = -1,
    @SerializedName("content")
    var content: String? = "",
    @SerializedName("subTitle")
    var subTitle: String? = "",
    @Transient
    var isSelected: Boolean? = false
) : Parcelable
