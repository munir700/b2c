package co.yap.modules.dashboard.cards.analytics.adaptors.viewholders

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemContactsBinding
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.viewmodels.AnalyticsItemViewModel
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class MerchantAnalyticsItemViewHolder(private val itemContactsBinding: ItemContactsBinding) :
    RecyclerView.ViewHolder(itemContactsBinding.root) {

    fun onBind(
        analyticsItem: AnalyticsItem?,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemContactsBinding.lyUserImage.tvNameInitials.background = Utils.getContactBackground(
            itemContactsBinding.lyUserImage.tvNameInitials.context,
            position
        )

        itemContactsBinding.lyUserImage.tvNameInitials.setTextColor(
            Utils.getContactColors(
                itemContactsBinding.lyUserImage.tvNameInitials.context, position
            )
        )

        itemContactsBinding.viewModel =
            AnalyticsItemViewModel(analyticsItem, position, onItemClickListener)
        itemContactsBinding.executePendingBindings()
    }

}