package co.yap.yapcore.helpers.extentions

import android.annotation.SuppressLint
import android.graphics.Color
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

//fun LineChart.initChart(entries: ArrayList<PieEntry>) {
//
//    // background color
//    this.setBackgroundColor(Color.WHITE)
//
//    // disable description text
//    this.description.isEnabled = false
//
//    // enable touch gestures
//    this.setTouchEnabled(true)
//
//    // set listeners
//    this.setOnChartValueSelectedListener(this)
//    this.setDrawGridBackground(false)
//
//    // create marker to display box when values are selected
//    val mv = ToolTipView(context, R.layout.item_bill_account_details_tooltip_view)
//
//    // Set the marker to the chart
//    mv.chartView = this
//    this.marker = mv
//
//    // enable scaling and dragging
//    this.isDragEnabled = true
//    this.setScaleEnabled(true)
//
//    // force pinch zoom along both axis
//    this.setPinchZoom(false)
//
//    var xAxis: XAxis
//    {   // // X-Axis Style // //
//        xAxis = this.xAxis
//
//        // vertical grid lines
//        xAxis.enableGridDashedLine(10f, 10f, 0f)
//        xAxis.setDrawGridLines(false)
//    }
//
//    val yAxis: YAxis = this.axisLeft
//
//    // disable dual axis (only use LEFT axis)
//    this.axisRight.isEnabled = false
//
//    // horizontal grid lines
//    yAxis.enableGridDashedLine(10f, 0f, 0f)
//    yAxis.setDrawGridLines(true)
//    // axis range
//    yAxis.axisMaximum = 800f
//    yAxis.axisMinimum = 0f
//
//    val llXAxis = LimitLine(9f, "Index 10")
//    llXAxis.lineWidth = 4f
//    llXAxis.enableDashedLine(10f, 10f, 0f)
//    llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
//    llXAxis.textSize = 10f
//    val ll1 = LimitLine(150f, "Upper Limit")
//    ll1.lineWidth = 4f
//    ll1.enableDashedLine(10f, 10f, 0f)
//    ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
//    ll1.textSize = 10f
//    val ll2 = LimitLine(-30f, "Lower Limit")
//    ll2.lineWidth = 4f
//    ll2.enableDashedLine(10f, 10f, 0f)
//    ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
//    ll2.textSize = 10f
//
//    // draw limit lines behind data instead of on top
////            yAxis.setDrawLimitLinesBehindData(true);
////            xAxis.setDrawLimitLinesBehindData(true);
//
//    // add limit lines
////            yAxis.addLimitLine(ll1);
////            yAxis.addLimitLine(ll2);
//    //xAxis.addLimitLine(llXAxis);
//
//    // add data
//    setData(this, 10, 800f)
//
//    // draw points over time
//
//    // draw points over time
//    this.animateX(1500)
//
//    // get the legend (only possible after setting data)
//
//    // get the legend (only possible after setting data)
//    val l: Legend = this.legend
//
//    // draw legend entries as lines
//
//    // draw legend entries as lines
//    l.form = Legend.LegendForm.LINE
//}