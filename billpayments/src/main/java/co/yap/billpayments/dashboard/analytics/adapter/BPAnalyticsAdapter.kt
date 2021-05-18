package co.yap.billpayments.dashboard.analytics.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemAnalyticsBinding
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class BPAnalyticsAdapter(private val list: MutableList<BPAnalyticsModel>) :
        BaseBindingRecyclerAdapter<BPAnalyticsModel, RecyclerView.ViewHolder>(list) {
    var selectedItem = 0
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BPAnalyticsItemViewHolder(
            binding as LayoutItemAnalyticsBinding
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_analytics

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BPAnalyticsItemViewHolder) {
            holder.onBind(selectedItem, list[position], position, onItemClickListener)
        }
    }
}
