package co.yap.widgets.guidedtour.view

import android.graphics.Point
import android.view.View

val View.locationOnScreen: Point
    get() = IntArray(2).let {
        getLocationOnScreen(it)
        Point(it[0], it[1])

    }
