package co.yap.billpayments.dashboard.analytics.detail

import androidx.databinding.ObservableField
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.BaseState

class BPAnalyticsDetailState : BaseState(), IBPAnalyticsDetail.State {
    override val bpAnalyticsModel: ObservableField<BPAnalyticsModel?> = ObservableField()
    override val monthYearAndTxnCount: ObservableField<String> = ObservableField()
}
