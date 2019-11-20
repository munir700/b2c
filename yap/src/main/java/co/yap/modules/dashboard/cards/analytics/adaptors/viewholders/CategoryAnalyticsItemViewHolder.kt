package co.yap.modules.dashboard.cards.analytics.adaptors.viewholders

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.viewmodels.AnalyticsItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class CategoryAnalyticsItemViewHolder(private val itemAnalyticsBinding: ItemAnalyticsBinding) :
    RecyclerView.ViewHolder(itemAnalyticsBinding.root) {

    fun onBind(
        analyticsItem: AnalyticsItem?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    )
    {
        itemAnalyticsBinding.viewModel =
            AnalyticsItemViewModel(analyticsItem, position, onItemClickListener)
        itemAnalyticsBinding.executePendingBindings()
    }
}