package co.yap.widgets.graph.renderer.executor

import co.yap.widgets.graph.Painter
import co.yap.widgets.graph.data.*


class DebugWithLabelsFrame {

    operator fun invoke(
        painter: Painter,
        axisType: AxisType,
        xLabels: List<Label>,
        yLabels: List<Label>,
        labelsSize: Float
    ): List<Frame> {
        val ascent = painter.measureLabelAscent(labelsSize)
        val descent = painter.measureLabelDescent(labelsSize)

        val labelsFrames = mutableListOf<Frame>()

        if (axisType.shouldDisplayAxisX())
            labelsFrames += xLabels.map {
                val labelHalfWidth = painter.measureLabelWidth(it.label, labelsSize) / 2
                Frame(
                    left = it.screenPositionX - labelHalfWidth,
                    top = it.screenPositionY + ascent,
                    right = it.screenPositionX + labelHalfWidth,
                    bottom = it.screenPositionY + descent
                )
            }

        if (axisType.shouldDisplayAxisY())
            labelsFrames += yLabels.map {
                val labelHalfWidth = painter.measureLabelWidth(it.label, labelsSize) / 2
                Frame(
                    left = it.screenPositionX - labelHalfWidth,
                    top = it.screenPositionY + ascent,
                    right = it.screenPositionX + labelHalfWidth,
                    bottom = it.screenPositionY + descent
                )
            }
        return labelsFrames.toList()
    }
}