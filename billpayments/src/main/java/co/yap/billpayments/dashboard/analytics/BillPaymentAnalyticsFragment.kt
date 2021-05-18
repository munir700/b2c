package co.yap.billpayments.dashboard.analytics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentBillPaymentsAnalyticsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.widgets.pieview.Entry
import co.yap.widgets.pieview.Highlight
import co.yap.widgets.pieview.OnChartValueSelectedListener
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.initPieChart
import co.yap.yapcore.interfaces.OnItemClickListener

class BillPaymentAnalyticsFragment : BaseBindingFragment<IBillPaymentAnalytics.ViewModel>(),
        IBillPaymentAnalytics.View, OnChartValueSelectedListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_bill_payments_analytics
    private val billPaymentAnalyticsViewModel: BillPaymentAnalyticsViewModel by viewModels()
    override val viewModel: BillPaymentAnalyticsViewModel
        get() = billPaymentAnalyticsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.analyticsAdapter.allowFullItemClickListener = true
        viewModel.analyticsAdapter.onItemClickListener = itemClickListener
        viewModel.clickEvent.observe(this, clickListener)
        viewModel.analyticsData.observe(this, Observer {
            setupPieChart(it)
            if (!it.isNullOrEmpty())
                viewModel.setSelectedItemState(it.first())
        })
    }

    private fun setupPieChart(list: List<BPAnalyticsModel>) {
        val entries = viewModel.getEntries(list)
        getBinding().chart1.initPieChart(
            entries,
            isEmptyList = list.isNullOrEmpty(),
            listener = this
        )
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            showPieView(pos)
            if (data is BPAnalyticsModel)
                viewModel.setSelectedItemState(data)
        }
    }

    private val clickListener = Observer<Int> {
        when (it) {

        }
    }


    private fun showPieView(indexValue: Int) {
        when (indexValue) {
            0 -> {
                getBinding().chart1.highlightValue(0f, 0, true)
            }
            1 -> {
                getBinding().chart1.highlightValue(1f, 0, true)
            }
            2 -> {
                getBinding().chart1.highlightValue(2f, 0, true)
            }
            3 -> {
                getBinding().chart1.highlightValue(3f, 0, true)
            }
        }
    }

    override fun getBinding(): FragmentBillPaymentsAnalyticsBinding =
            viewDataBinding as FragmentBillPaymentsAnalyticsBinding

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        h?.let { highlight ->
            if (!viewModel.analyticsAdapter.getDataList().isNullOrEmpty())
                viewModel.setSelectedItemState(
                    viewModel.analyticsAdapter.getDataForPosition(
                        highlight.x.toInt()
                    )
                )
        }
    }

}