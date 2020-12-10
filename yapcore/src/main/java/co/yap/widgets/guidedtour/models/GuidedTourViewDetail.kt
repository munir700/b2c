package co.yap.widgets.guidedtour.models

import android.view.View

data class GuidedTourViewDetail(
    val view: View,
    val title: String,
    val description: String,
    var padding: Float = 250f,
    var circleRadius: Float = 250f,
    var showPageNo: Boolean = true,
    var showSkip: Boolean = true,
    var btnText: String? = null,
    var isRectangle: Boolean = false
)