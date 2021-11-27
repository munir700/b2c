package co.yap.billpayments.dashboard.analytics.detail.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.billpayments.R
import co.yap.billpayments.databinding.LayoutItemPaidBillBinding
import co.yap.networking.transactions.responsedtos.billpayments.AnalyticsBill
import co.yap.yapcore.BaseBindingRecyclerAdapter

class BPAnalyticsDetailsAdapter(private val list: MutableList<AnalyticsBill>) :
    BaseBindingRecyclerAdapter<AnalyticsBill, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BPAnalyticsDetailsItemViewHolder(
            binding as LayoutItemPaidBillBinding
        )
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.layout_item_paid_bill

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BPAnalyticsDetailsItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}
