package co.yap.billpayments.dashboard.analytics.detail

import androidx.databinding.ObservableField
import co.yap.billpayments.dashboard.analytics.detail.adapter.BPAnalyticsDetailsAdapter
import co.yap.billpayments.databinding.FragmentBpAnalyticsDetailsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBPAnalyticsDetail {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getBinding(): FragmentBpAnalyticsDetailsBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val adapter: BPAnalyticsDetailsAdapter
        fun handlePressOnView(id: Int)
        fun fetchAnalyticsDetails(categoryId: String, date: String)
        fun initBpDetails(
            bpAnalyticsModel: BPAnalyticsModel?,
            monthYear: String?
        )
    }

    interface State : IBase.State {
        val bpAnalyticsModel: ObservableField<BPAnalyticsModel?>
        val monthYearAndTxnCount: ObservableField<String>
    }
}
