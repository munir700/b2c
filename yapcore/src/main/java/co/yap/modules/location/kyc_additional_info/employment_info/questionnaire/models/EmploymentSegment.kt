package co.yap.modules.location.kyc_additional_info.employment_info.questionnaire.models

import android.os.Parcelable
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmploymentSegment(
    var subSegmentCode:String,
    var subSegmentDesc:String
): Parcelable, CoreBottomSheetData()