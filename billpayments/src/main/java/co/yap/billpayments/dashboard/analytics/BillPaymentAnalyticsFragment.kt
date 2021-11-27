package co.yap.billpayments.dashboard.analytics

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseFragment
import co.yap.billpayments.databinding.FragmentBillPaymentsAnalyticsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.widgets.pieview.Entry
import co.yap.widgets.pieview.Highlight
import co.yap.widgets.pieview.OnChartValueSelectedListener
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_MON_YEAR
import co.yap.yapcore.helpers.extentions.initPieChart
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.interfaces.OnItemClickListener
import java.util.*

class BillPaymentAnalyticsFragment : BillDashboardBaseFragment<IBillPaymentAnalytics.ViewModel>(),
    IBillPaymentAnalytics.View, OnChartValueSelectedListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_bill_payments_analytics
    private val billPaymentAnalyticsViewModel: BillPaymentAnalyticsViewModel by viewModels()
    override val viewModel: BillPaymentAnalyticsViewModel
        get() = billPaymentAnalyticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initCurrentDate()
        val currDate = DateUtils.dateToString(
            Calendar.getInstance().time, FORMAT_MON_YEAR, DateUtils.TIME_ZONE_Default
        )
        viewModel.fetchBillCategoryAnalytics(
            currDate.split(" ").joinToString(separator = ",") { it }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.analyticsAdapter.allowFullItemClickListener = true
        viewModel.analyticsAdapter.onItemClickListener = itemClickListener
        viewModel.clickEvent.observe(this, clickListener)
        viewModel.analyticsData.observe(this.viewLifecycleOwner, Observer {
            setupPieChart(it)
            if (!it.isNullOrEmpty()) {
                viewModel.state.selectedItemSpentValue =
                    viewModel.getTotalSpentAmountOnBills(it).toString().toFormattedCurrency(
                        showCurrency = true,
                        withComma = true
                    )
                viewModel.state.selectedItemName = "Total Bills"
                viewModel.state.isNTRYShow.set(false)
            } else {
                viewModel.state.selectedItemSpentValue = ""
                viewModel.state.selectedItemName = ""
                viewModel.state.isNTRYShow.set(true)
            }
        })
    }

    private fun setupPieChart(list: List<BPAnalyticsModel>?) {
        val entries = viewModel.getEntries(list)
        val colors = viewModel.getPieChartColors(list)
        getBinding().chart1.initPieChart(
            entries = entries,
            graphColors = colors,
            shouldHighlightFirstIndex = false,
            isEmptyList = list.isNullOrEmpty(),
            listener = this
        )
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is BPAnalyticsModel) {
                navigate(
                    R.id.action_billPaymentAnalyticsFragment_to_BPAnalyticsDetailFragment,
                    bundleOf(
                        "analyticsModel" to data,
                        "monthYear" to viewModel.state.displayMonth.get()
                    )
                )
            }
        }
    }

    private val clickListener = Observer<Int> {
        when (it) {

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
                    model = viewModel.analyticsAdapter.getDataForPosition(
                        highlight.x.toInt()
                    ),
                    currentPosition = highlight.x.toInt()
                )
        }
    }

}