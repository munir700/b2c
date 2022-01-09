package co.yap.billpayments.billdetail.billaccountdetail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.billdetail.base.BillDetailBaseFragment
import co.yap.billpayments.databinding.FragmentBillAccountDetailBinding
import co.yap.billpayments.paybill.main.PayBillMainActivity
import co.yap.networking.transactions.responsedtos.billpayment.BillLineChartHistory
import co.yap.widgets.pieview.*
import co.yap.widgets.pieview.components.ToolTipView2
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.launchActivity
import java.util.*


class BillAccountDetailFragment :
    BillDetailBaseFragment<IBillAccountDetail.ViewModel>(),
    IBillAccountDetail.View, OnChartValueSelectedListener {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_bill_account_detail
    override val viewModel: BillAccountDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.getBillAccountHistory(viewModel.parentViewModel?.selectedBill?.uuid.toString())
        viewModel.getBillAccountLineChartHistory(viewModel.parentViewModel?.selectedBill?.uuid.toString())
        viewModel.parentViewModel?.state?.enableRightIcon?.set(viewModel.parentViewModel?.selectedBill?.isBillerNotUnavailable() == false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity().intent.hasExtra(ExtraKeys.IS_UPDATED.name))
            if (requireActivity().intent.getBooleanExtra(ExtraKeys.IS_UPDATED.name, false)) {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.billAccountDetailFragment, true) // starting destination skipped
                    .build()
                findNavController().navigate(
                    R.id.action_billAccountDetailFragment_to_editBillFragment,
                    null,
                    navOptions
                )
            }

    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, onViewClickObserver)
        viewModel.parentViewModel?.toolBarClickEvent?.observe(this, toolbarClickObserver)
        viewModel.lineChartHistoryResponse.observe(this, Observer {
            if (!it.isNullOrEmpty()) initChart(it)
        })
    }

    private val onViewClickObserver = Observer<Int> {
        when (it) {
            R.id.btnPayNow -> {
                launchActivity<PayBillMainActivity>(requestCode = RequestCodes.REQUEST_PAY_BILL) {
                    putExtra(ExtraKeys.SELECTED_BILL.name, viewModel.parentViewModel?.selectedBill)
                }
            }
        }
    }

    private val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> {
                if (viewModel.parentViewModel?.selectedBill?.isBillerNotUnavailable() == false)
                    navigate(
                        destinationId = R.id.action_billAccountDetailFragment_to_editBillFragment
                    )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_PAY_BILL -> {
                    if (data?.getBooleanExtra(ExtraKeys.IS_UPDATED.name, false) == true) {
                        navigate(
                            destinationId = R.id.action_billAccountDetailFragment_to_editBillFragment
                        )
                    } else
                        setIntentResult()
                }
            }
        }
    }

    private fun setIntentResult() {
        val intent = Intent()
        requireActivity().setResult(Activity.RESULT_OK, intent)
        requireActivity().finish()
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.parentViewModel?.toolBarClickEvent?.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    private fun initChart(it: MutableList<BillLineChartHistory>) {

        // background color
        getViewBinding().billingHistoryChart.setBackgroundColor(Color.WHITE)
        getViewBinding().billingHistoryChart.setOnChartValueSelectedListener(this)


        // disable description text
        getViewBinding().billingHistoryChart.description.isEnabled = false

        // enable touch gestures
        getViewBinding().billingHistoryChart.setTouchEnabled(true)

        // set listeners
        getViewBinding().billingHistoryChart.setOnChartValueSelectedListener(this)
        getViewBinding().billingHistoryChart.setDrawGridBackground(false)

        // create marker to display box when values are selected
        val mv = ToolTipView2(requireContext(), R.layout.item_bill_account_details_tooltip_view, it)

        // Set the marker to the chart
        mv.chartView = getViewBinding().billingHistoryChart
        getViewBinding().billingHistoryChart.marker = mv

        // enable scaling and dragging
        getViewBinding().billingHistoryChart.isDragEnabled = false
        getViewBinding().billingHistoryChart.setScaleEnabled(false)

        // force pinch zoom along both axis
        getViewBinding().billingHistoryChart.setPinchZoom(false)

        val yAxis: YAxis = getViewBinding().billingHistoryChart.axisLeft

        // disable dual axis (only use LEFT axis)
        getViewBinding().billingHistoryChart.axisRight.isEnabled = false

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 0f, 0f)
        yAxis.setDrawGridLines(true)
        yAxis.setDrawLimitLinesBehindData(true)
        yAxis.axisLineColor = Color.parseColor("#dae0f0")
        // axis range
        yAxis.axisMaximum =
            it.maxWith(Comparator.comparingDouble { it.amount!! })?.amount?.toFloat()
                ?: 0f
        yAxis.axisMinimum = 0f
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxis.textColor = Color.parseColor("#9391B1")
        yAxis.setLabelCount(it.size, true)

        // add data
        setData(getViewBinding().billingHistoryChart, it)

        // draw points over time
        getViewBinding().billingHistoryChart.animateX(500)

        // get the legend (only possible after setting data)
        val l: Legend = getViewBinding().billingHistoryChart.legend
        l.isWordWrapEnabled = true

        // draw legend entries as lines
        l.form = Legend.LegendForm.LINE
    }

    private fun setData(chart: LineChart, it: MutableList<BillLineChartHistory>) {
        val values = ArrayList<Entry>()
        for (i in 0 until it.size) {
            values.add(Entry(i.toFloat(), it[i].amount?.toFloat() ?: 0.toFloat(), null))
        }
        chart.setDrawGridBackground(false)
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(false)
        chart.axisRight.setDrawGridLines(false)
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
            set1 = LineDataSet(
                values,
                ""
            ) //Dont assign value to label. This will add Label ate the bottom of chart.
            set1.setDrawIcons(false)
            val valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return it[(value % it.size).toInt()].month.toString()
                }
            }
            set1.setDrawHighlightIndicators(false)
            val xAxis: XAxis = chart.xAxis
            xAxis.granularity = 1f // minimum axis-step (interval) is 1
            xAxis.valueFormatter = valueFormatter
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textColor = Color.parseColor("#9391B1")
            xAxis.textSize = 20f
            xAxis.setAvoidFirstLastClipping(true)

            // black lines and points
            set1.color = Color.parseColor("#7c4dff")
            set1.setCircleColor(Color.parseColor("#7c4dff"))
            set1.circleHoleColor = Color.parseColor("#FFFFFF")

            set1.lineWidth = 4f
            set1.circleRadius = 5f
            set1.circleHoleRadius = 3f
            set1.setDrawCircleHole(true)

            // set the filled area
            set1.setDrawFilled(true)
            // set draw values on circle
            set1.setDrawValues(false)

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
            chart.invalidate()
        }
    }

    fun getViewBinding(): FragmentBillAccountDetailBinding {
        return viewDataBinding as FragmentBillAccountDetailBinding
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.x?.let {

            getViewBinding().billingHistoryChart.xAxis?.textColor = Color.parseColor("#7c4dff")
//             getViewBinding().billingHistoryChart.xAxis?.valueFormatter?.getAxisLabel()
        }
    }

    override fun onNothingSelected() {

    }
}
