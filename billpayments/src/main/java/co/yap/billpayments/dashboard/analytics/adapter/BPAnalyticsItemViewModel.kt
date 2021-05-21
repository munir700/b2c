package co.yap.billpayments.dashboard.analytics.adapter

import android.view.View
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BPAnalyticsItemViewModel(
        val bpAnalyticsModel: BPAnalyticsModel,
        val position: Int,
        private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, bpAnalyticsModel, position)
    }
}
