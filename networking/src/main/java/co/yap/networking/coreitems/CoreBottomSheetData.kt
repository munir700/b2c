package co.yap.networking.coreitems

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
open class CoreBottomSheetData(
    var sheetImage: Int? = -1,
    var content: String? = "",
    var subTitle: String? = "",
    var isSelected: Boolean? = false,
    var subContent : String? = "",
    var key : String? = "None"
) : ApiResponse(),Parcelable
