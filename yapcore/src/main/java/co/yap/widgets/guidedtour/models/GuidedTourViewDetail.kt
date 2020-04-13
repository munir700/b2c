package co.yap.widgets.guidedtour.models

import android.view.View

data class GuidedTourViewDetail(
    val view: View,
    val title: String,
    val description: String,
    var pointX: Int ,
    var pointY: Int
)