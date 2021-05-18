package co.yap.billpayments.dashboard.analytics.adapter

import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.databinding.LayoutItemAnalyticsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BPAnalyticsItemViewHolder(private val layoutItemAnalyticsBinding: LayoutItemAnalyticsBinding) :
        RecyclerView.ViewHolder(layoutItemAnalyticsBinding.root) {

    fun onBind(
        selectedItem: Int,
        bpAnalyticsModel: BPAnalyticsModel,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        layoutItemAnalyticsBinding.selected = selectedItem == position
        layoutItemAnalyticsBinding.viewModel =
            BPAnalyticsItemViewModel(
                bpAnalyticsModel,
                position,
                onItemClickListener
            )
        layoutItemAnalyticsBinding.executePendingBindings()
    }
}
