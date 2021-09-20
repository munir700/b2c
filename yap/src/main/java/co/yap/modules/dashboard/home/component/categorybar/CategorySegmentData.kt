package co.yap.modules.dashboard.home.component.categorybar

import androidx.annotation.Keep

//TODO("should remove this, as we can use the same data model as used in response")
@Keep
data class CategorySegmentData(
    val progress: Float,
    val icon: String
)