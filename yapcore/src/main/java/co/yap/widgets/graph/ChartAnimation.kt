package co.yap.widgets.graph

import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import co.yap.widgets.graph.data.DataPoint

abstract class ChartAnimation {

    var duration: Long = 1000

    var interpolator: Interpolator = DecelerateInterpolator()

    abstract fun animateFrom(y: Float, entries: List<DataPoint>, callback: () -> Unit): ChartAnimation
}