package co.yap.billpayments.dashboard.analytics.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseFragment
import co.yap.billpayments.databinding.FragmentBpAnalyticsDetailsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BPAnalyticsDetailFragment :
    BillDashboardBaseFragment<FragmentBpAnalyticsDetailsBinding, IBPAnalyticsDetail.ViewModel>(),
    IBPAnalyticsDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_bp_analytics_details
    private val billPaymentAnalyticsViewModel: BPAnalyticsDetailViewModel by viewModels()
    override val viewModel: BPAnalyticsDetailViewModel
        get() = billPaymentAnalyticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.state.bpAnalyticsModel.set(it.getParcelable("analyticsModel") as? BPAnalyticsModel)
            val monthYear = it.getString("monthYear")
            viewModel.initBpDetails(viewModel.state.bpAnalyticsModel.get(), monthYear)
        }
    }

    override fun setObservers() {
        viewModel.adapter.allowFullItemClickListener = true
        viewModel.adapter.onItemClickListener = itemClickListener
        viewModel.clickEvent.observe(this, clickListener)

    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

        }
    }

    private val clickListener = Observer<Int> {
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
}