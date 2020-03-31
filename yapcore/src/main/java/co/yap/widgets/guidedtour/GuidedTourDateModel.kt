package co.yap.widgets.guidedtour

import android.view.View

data class GuidedTourDataModel(val guidedTourDetailList: ArrayList<GuidedTourDetail>)

data class GuidedTourDetail(
    val view: View,
    val description: String
)