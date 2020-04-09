package co.yap.widgets.guidedtour.models

import android.view.View

//data class GuidedTourDataModel(val guidedTourDetailList: ArrayList<GuidedTourDetail>)

data class GuidedTourViewDetail(
    val view: View,
    val title: String,
    val description: String
)