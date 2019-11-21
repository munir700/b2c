package co.yap.modules.dashboard.cards.analytics.adaptors.viewholders

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.MerchantAnalyticsAdaptor
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.viewmodels.AnalyticsItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class MerchantAnalyticsItemViewHolder(private val itemAnalyticsBinding: ItemAnalyticsBinding) :
    RecyclerView.ViewHolder(itemAnalyticsBinding.root) {

    fun onBind(
        adapter: MerchantAnalyticsAdaptor?,
        analyticsItem: AnalyticsItem?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {

        if (adapter?.checkedPosition != -1) {

            itemView.apply {
                setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        if (adapter?.checkedPosition == position) R.color.greyLight else R.color.white
                    )
                )
                isSelected = adapter?.checkedPosition == position
            }
        }



        itemAnalyticsBinding.viewModel =
            AnalyticsItemViewModel(analyticsItem, position, onItemClickListener)
        itemAnalyticsBinding.executePendingBindings()
    }
}