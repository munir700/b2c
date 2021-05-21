package co.yap.yapcore.helpers.extentions

import android.annotation.SuppressLint
import android.graphics.Color
import co.yap.widgets.pieview.*
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