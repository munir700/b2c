package co.yap.billpayments.dashboard.analytics

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.databinding.FragmentBillPaymentsAnalyticsBinding
import co.yap.widgets.pieview.Entry
import co.yap.widgets.pieview.Highlight
import co.yap.widgets.pieview.OnChartValueSelectedListener
import co.yap.widgets.pieview.PieEntry
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.initPieChart

class BillPaymentAnalyticsFragment : BaseBindingFragment<IBillPaymentAnalytics.ViewModel>(),
        IBillPaymentAnalytics.View, OnChartValueSelectedListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_bill_payments_analytics

    override val viewModel: BillPaymentAnalyticsViewModel
        get() = ViewModelProviders.of(this).get(BillPaymentAnalyticsViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().chart1.initPieChart(getEntries(), isEmptyList = true, listener = this)
        showPieView(0)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickListener)
    }

    private val clickListener = Observer<Int> {
        when (it) {
        }
    }

    private fun getEntries(): ArrayList<PieEntry> {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(100f))
        return entries
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
    }

}