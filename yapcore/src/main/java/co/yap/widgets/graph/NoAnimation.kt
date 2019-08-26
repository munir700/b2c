package co.yap.widgets.graph

import co.yap.widgets.graph.data.DataPoint


class NoAnimation : ChartAnimation() {

    override fun animateFrom(
        y: Float,
        entries: List<DataPoint>,
        callback: () -> Unit
    ): ChartAnimation {
        callback()
        return this
    }
}