package co.yap.widgets.graph.renderer

import co.yap.widgets.graph.ChartAnimation
import co.yap.widgets.graph.ChartContract
import co.yap.widgets.graph.Painter
import co.yap.widgets.graph.data.*
import co.yap.widgets.graph.extensions.limits
import co.yap.widgets.graph.extensions.toDataPoints
import co.yap.widgets.graph.extensions.toLabels
import co.yap.widgets.graph.renderer.executor.DebugWithLabelsFrame
import co.yap.widgets.graph.renderer.executor.MeasureBarChartPaddings

class BarChartRenderer(
    private val view: ChartContract.View,
    private val painter: Painter,
    private var animation: ChartAnimation
) : ChartContract.Renderer {

    private lateinit var data: List<DataPoint>
//    private lateinit var data: DataPoint

    private lateinit var axis: AxisType

    private lateinit var outerFrame: Frame

    private lateinit var innerFrame: Frame

    private var labelsSize: Float = notInitialized

    private val xLabels: List<Label> by lazy {
        data.toLabels()
    }

    private val yLabels by lazy {
        val scale = Scale(min = 0F, max = data.limits().second)
        val scaleStep = (scale.max - scale.min) / defaultScaleNumberOfSteps

        List(defaultScaleNumberOfSteps + 1) {
            val scaleValue = scale.min + scaleStep * it
            Label(
                label = scaleValue.toString(),
                screenPositionX = 0F,
                screenPositionY = 0F
            )
        }
    }

    override fun preDraw(
        width: Int,
        height: Int,
        paddingLeft: Int,
        paddingTop: Int,
        paddingRight: Int,
        paddingBottom: Int,
        axis: AxisType,
        labelsSize: Float
    ): Boolean {

        if (this.labelsSize != notInitialized)
            return true

        this.axis = axis
        this.labelsSize = labelsSize

        outerFrame = Frame(
            left = paddingLeft.toFloat(),
            top = paddingTop.toFloat(),
            right = width - paddingRight.toFloat(),
            bottom = height - paddingBottom.toFloat()
        )

        val longestChartLabel = yLabels.maxBy { painter.measureLabelWidth(it.label, labelsSize) }
            ?: throw IllegalArgumentException("Looks like there's no labels to find the longest width.")

        val paddings = MeasureBarChartPaddings()(
            axisType = axis,
            labelsHeight = painter.measureLabelHeight(labelsSize),
            longestLabelWidth = painter.measureLabelWidth(longestChartLabel.label, labelsSize),
            labelsPaddingToInnerChart = labelsPaddingToInnerChart
        )

        innerFrame = Frame(
            left = outerFrame.left + paddings.left,
            top = outerFrame.top + paddings.top,
            right = outerFrame.right - paddings.right,
            bottom = outerFrame.bottom - paddings.bottom
        )

        if (axis.shouldDisplayAxisX())
            placeLabelsX(innerFrame)

        if (axis.shouldDisplayAxisY())
            placeLabelsY(innerFrame)

        placeDataPoints(innerFrame)

        animation.animateFrom(innerFrame.bottom, data) { view.postInvalidate() }

        return false
    }

    override fun draw() {

        if (axis.shouldDisplayAxisX())
            view.drawLabels(xLabels)

        if (axis.shouldDisplayAxisY())
            view.drawLabels(yLabels)

        view.drawData(innerFrame, data)

        if (inDebug) {
            view.drawDebugFrame(
                outerFrame,
                innerFrame,
                DebugWithLabelsFrame()(
                    painter = painter,
                    axisType = axis,
                    xLabels = xLabels,
                    yLabels = yLabels,
                    labelsSize = labelsSize
                )
            )
        }
    }

    override fun render(entries: LinkedHashMap<String, Float>) {
        data = entries.toDataPoints()
        view.postInvalidate()
    }

    override fun anim(entries: DataPoint, animation: ChartAnimation) {
        val dataPoint=entries
//        data = entries.toDataPoints()
//        data[]
        data = listOf(entries)
//        data=data.add(dataPoint)
        this.animation = animation
        view.postInvalidate()
    }

    private fun placeLabelsX(innerFrame: Frame) {

        val halfBarWidth = (innerFrame.right - innerFrame.left) / xLabels.size / 2
        val labelsLeftPosition = innerFrame.left + halfBarWidth
        val labelsRightPosition = innerFrame.right - halfBarWidth
        val widthBetweenLabels = (labelsRightPosition - labelsLeftPosition) / (xLabels.size - 1)
        val xLabelsVerticalPosition =
            innerFrame.bottom -
                painter.measureLabelAscent(labelsSize) +
                labelsPaddingToInnerChart

        xLabels.forEachIndexed { index, label ->
            label.screenPositionX = labelsLeftPosition + (widthBetweenLabels * index)
            label.screenPositionY = xLabelsVerticalPosition
        }
    }

    private fun placeLabelsY(innerFrame: Frame) {

        val heightBetweenLabels = (innerFrame.bottom - innerFrame.top) / defaultScaleNumberOfSteps
        val labelsBottomPosition = innerFrame.bottom + painter.measureLabelHeight(labelsSize) / 2

        yLabels.forEachIndexed { index, label ->
            label.screenPositionX =
                innerFrame.left -
                    labelsPaddingToInnerChart -
                    painter.measureLabelWidth(label.label, labelsSize) / 2
            label.screenPositionY = labelsBottomPosition - heightBetweenLabels * index
        }
    }

    private fun placeDataPoints(innerFrame: Frame) {

        val scale = Scale(min = 0F, max = data.limits().second)
        val scaleSize = scale.max - scale.min
        val chartHeight = innerFrame.bottom - innerFrame.top
        val halfBarWidth = (innerFrame.right - innerFrame.left) / xLabels.size / 2
        val labelsLeftPosition = innerFrame.left + halfBarWidth
        val labelsRightPosition = innerFrame.right - halfBarWidth
        val widthBetweenLabels = (labelsRightPosition - labelsLeftPosition) / (xLabels.size - 1)

        data.forEachIndexed { index, dataPoint ->
            dataPoint.screenPositionX = labelsLeftPosition + (widthBetweenLabels * index)
            dataPoint.screenPositionY =
                innerFrame.bottom -
                    (chartHeight * (dataPoint.value - scale.min) / scaleSize)
        }
    }
}
