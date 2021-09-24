package co.yap.billpayments.dashboard.analytics.detail.adapter

import android.view.View
import co.yap.networking.transactions.responsedtos.billpayments.AnalyticsBill
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BPAnalyticsDetailsItemViewModel(
    val bill: AnalyticsBill,
    val position: Int,
    private val onItemClickListener: OnItemClickListener?
) {
    fun onViewClicked(view: View) {
        onItemClickListener?.onItemClick(view, bill, position)
    }
}
