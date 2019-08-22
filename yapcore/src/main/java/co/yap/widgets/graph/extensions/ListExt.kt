package co.yap.widgets.graph.extensions

import co.yap.widgets.graph.data.Label
import co.yap.widgets.graph.data.Scale
import com.db.williamchart.data.DataPoint

fun List<Float>._limits(): Pair<Float, Float> {

    val min = min() ?: 0F
    var max = max() ?: 1F

    if (min == max)
        max += 1F

    return Pair(min, max)
}

fun List<DataPoint>.limits(): Pair<Float, Float> {

    if (isEmpty())
        Pair(0F, 1F)

    val values = map { it.value }
    return values._limits()
}

fun List<DataPoint>.toScale(): Scale {
    val limits = limits()
    return Scale(min = limits.first, max = limits.second)
}

fun List<DataPoint>.toLabels(): List<Label> {
    return map {
        Label(
            label = it.label,
            screenPositionX = 0f,
            screenPositionY = 0f
        )
    }
}