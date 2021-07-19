package co.yap.yapcore.helpers.extentions

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.DashPathEffect
import androidx.core.content.ContextCompat
import co.yap.widgets.pieview.*
import co.yap.widgets.pieview.components.ToolTipView
import co.yap.yapcore.R


@SuppressLint("NewApi")
fun PieChart.initPieChart(
    entries: ArrayList<PieEntry>,
    graphColors: List<Int> = resources.getIntArray(R.array.analyticsColors).toList(),
    isEmptyList: Boolean,
    shouldHighlightFirstIndex: Boolean = true,
    listener: OnChartValueSelectedListener
) {
    this.setUsePercentValues(false)
    this.description.isEnabled = false
    this.dragDecelerationFrictionCoef = 0.95f
    this.isDrawHoleEnabled = true
    this.setHoleColor(Color.TRANSPARENT)
    this.setTransparentCircleColor(Color.WHITE)
    this.setTransparentCircleAlpha(200)
    this.holeRadius = 70f
    this.transparentCircleRadius = 68f
    this.setDrawCenterText(true)
    this.rotationAngle = -90f
    this.isRotationEnabled = false
    this.setOnChartValueSelectedListener(listener)
    this.animateY(1400, Easing.EaseInOutQuad)
    this.legend.isEnabled = false
    this.setEntryLabelColor(Color.WHITE)
    this.setEntryLabelTextSize(0f)

    // Setting array data to display pi chart
    val colors = ArrayList<Int>()
    if (isEmptyList) {
        this.isHighlightPerTapEnabled = false
        colors.add(ColorTemplate.getEmptyColor())
    } else {
        this.isHighlightPerTapEnabled = true
    }

    colors.addAll(graphColors)
    val dataSet = PieDataSet(entries, "")
    dataSet.setDrawIcons(false)
    dataSet.sliceSpace = 0f
    dataSet.iconsOffset = MPPointF(0f, 40f)
    dataSet.selectionShift = 12f
    dataSet.setDrawValues(false)
    dataSet.colors = colors
    val data = PieData(dataSet)
    data.setValueTextSize(11f)
    data.setValueTextColor(Color.WHITE)
    this.data = data
    if (shouldHighlightFirstIndex)
        this.highlightValue(0f, 0)
    else
        this.highlightValue(0f, -1)

    this.invalidate()
}

fun LineChart.initChart(entries: ArrayList<PieEntry>) {

    // background color
    this.setBackgroundColor(Color.WHITE)

    // disable description text
    this.description.isEnabled = false

    // enable touch gestures
    this.setTouchEnabled(true)

    // set listeners
    this.setDrawGridBackground(false)

    // create marker to display box when values are selected
    val mv = ToolTipView(context, R.layout.item_bill_account_details_tooltip_view)

    // Set the marker to the chart
    mv.chartView = this
    this.marker = mv

    // enable scaling and dragging
    this.isDragEnabled = true
    this.setScaleEnabled(true)

    // force pinch zoom along both axis
    this.setPinchZoom(false)

    val yAxis: YAxis = this.axisLeft

    // disable dual axis (only use LEFT axis)
    this.axisRight.isEnabled = false

    // horizontal grid lines
    yAxis.enableGridDashedLine(10f, 0f, 0f)
    yAxis.setDrawGridLines(true)
    // axis range
    yAxis.axisMaximum = 800f
    yAxis.axisMinimum = 0f

    val llXAxis = LimitLine(9f, "Index 10")
    llXAxis.lineWidth = 4f
    llXAxis.enableDashedLine(10f, 10f, 0f)
    llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
    llXAxis.textSize = 10f
    val ll1 = LimitLine(150f, "Upper Limit")
    ll1.lineWidth = 4f
    ll1.enableDashedLine(10f, 10f, 0f)
    ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
    ll1.textSize = 10f
    val ll2 = LimitLine(-30f, "Lower Limit")
    ll2.lineWidth = 4f
    ll2.enableDashedLine(10f, 10f, 0f)
    ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
    ll2.textSize = 10f

    // add data
    val values = entries as List<Entry>?
    val set1: LineDataSet
    if (this.data != null &&
            this.data.dataSetCount > 0
    ) {
        set1 = this.data.getDataSetByIndex(0) as LineDataSet
        set1.values = values
        set1.notifyDataSetChanged()
        this.data.notifyDataChanged()
        this.notifyDataSetChanged()
    } else {
        // create a dataset and give it a type
        set1 = LineDataSet(values, "DataSet 1")
        set1.setDrawIcons(false)

        // black lines and points
        set1.color = Color.parseColor("#5E35B1")
        set1.setCircleColor(Color.parseColor("#5E35B1"))
        set1.circleHoleColor = Color.parseColor("#FFFFFF")

        // line thickness and point size
        set1.lineWidth = 2f
        set1.circleRadius = 5f
        set1.circleHoleRadius = 3f

        // draw points as solid circles
        set1.setDrawCircleHole(true)

        // customize legend entry
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        // text size of values
        set1.valueTextSize = 9f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(true)
        set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> this.axisLeft.axisMinimum }

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            val drawable = ContextCompat.getDrawable(this.context, R.drawable.bg_line_graph)
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = Color.BLACK
        }

        /// to set cubic graph mode
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        val dataSets = java.util.ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the data sets

        // create a data object with the data sets
        val data = LineData(dataSets)

        // set data
        this.data = data
    }

    // draw points over time
    this.animateX(1500)

    // get the legend (only possible after setting data)
    val l: Legend = this.legend

    // draw legend entries as lines

    // draw legend entries as lines
    l.form = Legend.LegendForm.LINE



}