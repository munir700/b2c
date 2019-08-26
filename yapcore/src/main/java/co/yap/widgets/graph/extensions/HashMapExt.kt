package co.yap.widgets.graph.extensions

import co.yap.widgets.graph.data.DataPoint

fun HashMap<String, Float>.toDataPoints(): List<DataPoint> {
    return entries.map {
        DataPoint(
            label = it.key,
            value = it.value,
            screenPositionX = 0f,
            screenPositionY = 0f
        )
    }
}