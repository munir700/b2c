package co.yap.modules.dashboard.cards.analytics.adaptors

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.viewholders.MerchantAnalyticsItemViewHolder
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.yapcore.BaseBindingRecyclerAdapter

class MerchantAnalyticsAdaptor(private val list: MutableList<AnalyticsItem>) :
    BaseBindingRecyclerAdapter<AnalyticsItem, RecyclerView.ViewHolder>(list) {

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return MerchantAnalyticsItemViewHolder(binding as ItemAnalyticsBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_analytics


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is MerchantAnalyticsItemViewHolder) {
            holder.onBind(list[position], position, onItemClickListener)
        }
    }
}