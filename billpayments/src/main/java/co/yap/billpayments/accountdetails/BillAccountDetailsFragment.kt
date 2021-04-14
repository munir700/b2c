package co.yap.billpayments.accountdetails

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addBill.base.AddBillBaseFragment
import co.yap.billpayments.databinding.FragmentBillAccountDetailsBinding
import co.yap.widgets.pieview.*
import co.yap.widgets.pieview.components.ToolTipView
import java.util.*

class BillAccountDetailsFragment : AddBillBaseFragment<IBillAccountDetails.ViewModel>(),
    IBillAccountDetails.View, OnChartValueSelectedListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_bill_account_details

    override val viewModel: BillAccountDetailsViewModel
        get() = ViewModelProviders.of(this).get(BillAccountDetailsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initChart()
    }

    private fun initChart() {

        // background color
        getViewBinding().billingHistoryChart.setBackgroundColor(Color.WHITE)

        // disable description text
        getViewBinding().billingHistoryChart.description.isEnabled = false

        // enable touch gestures
        getViewBinding().billingHistoryChart.setTouchEnabled(true)

        // set listeners
        getViewBinding().billingHistoryChart.setOnChartValueSelectedListener(this)
        getViewBinding().billingHistoryChart.setDrawGridBackground(false)

        // create marker to display box when values are selected
        val mv = ToolTipView(context, R.layout.item_bill_account_details_tooltip_view)

        // Set the marker to the chart
        mv.chartView = getViewBinding().billingHistoryChart
        getViewBinding().billingHistoryChart.marker = mv

        // enable scaling and dragging
        getViewBinding().billingHistoryChart.isDragEnabled = true
        getViewBinding().billingHistoryChart.setScaleEnabled(true)
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        getViewBinding().billingHistoryChart.setPinchZoom(false)

        var xAxis: XAxis
        {   // // X-Axis Style // //
            xAxis = getViewBinding().billingHistoryChart.xAxis

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)
            xAxis.setDrawGridLines(false)
        }

        val yAxis: YAxis = getViewBinding().billingHistoryChart.axisLeft

        // disable dual axis (only use LEFT axis)
        getViewBinding().billingHistoryChart.axisRight.isEnabled = false

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 0f, 0f)
        yAxis.setDrawGridLines(true)
        // axis range
        yAxis.setAxisMaximum(800f)
        yAxis.setAxisMinimum(0f)

        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.setLineWidth(4f)
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM)
        llXAxis.setTextSize(10f)
        val ll1 = LimitLine(150f, "Upper Limit")
        ll1.setLineWidth(4f)
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP)
        ll1.setTextSize(10f)
        val ll2 = LimitLine(-30f, "Lower Limit")
        ll2.setLineWidth(4f)
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM)
        ll2.setTextSize(10f)

        // draw limit lines behind data instead of on top
//            yAxis.setDrawLimitLinesBehindData(true);
//            xAxis.setDrawLimitLinesBehindData(true);

        // add limit lines
//            yAxis.addLimitLine(ll1);
//            yAxis.addLimitLine(ll2);
        //xAxis.addLimitLine(llXAxis);

        // add data
        setData(getViewBinding().billingHistoryChart, 10, 800f)

        // draw points over time

        // draw points over time
        getViewBinding().billingHistoryChart.animateX(1500)

        // get the legend (only possible after setting data)

        // get the legend (only possible after setting data)
        val l: Legend = getViewBinding().billingHistoryChart.legend

        // draw legend entries as lines

        // draw legend entries as lines
        l.form = Legend.LegendForm.LINE
    }

    private fun setData(chart: LineChart, count: Int, range: Float) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).toFloat() - 30
            values.add(Entry(i.toFloat(), `val`, null))
        }
        val set1: LineDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
//            set1.enableDashedLine(10f, 0f, 0f);

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
                IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_line_graph)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }

            /// to set cubic graph mode
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    val clickObserver = Observer<Int> {
        when (it) {
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    fun getViewBinding(): FragmentBillAccountDetailsBinding {
        return viewDataBinding as FragmentBillAccountDetailsBinding
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {}

    override fun onNothingSelected() {}
}
