package co.yap.household.onboarding.onboarding.fragments

import androidx.recyclerview.widget.RecyclerView
import co.yap.household.databinding.ItemHouseHoldCardBinding
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCardSelectionItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class HouseHoldCardSelectionItemViewHolder(private val itemHouseHoldCardBinding: ItemHouseHoldCardBinding) :
    RecyclerView.ViewHolder(itemHouseHoldCardBinding.root) {
    fun onBind(
        cardColorSelectionModel: CardColorSelectionModel,
        position: Int,
        dimensions: IntArray,
        onItemClickListener: OnItemClickListener?
    ) {
        val params = itemHouseHoldCardBinding.ivCard.layoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        itemHouseHoldCardBinding.ivCard.layoutParams = params
        itemHouseHoldCardBinding.houseHoldCardItemViewModel?.position = position
        // itemHouseHoldCardBinding.houseHoldCardItemViewModel?.color = cardColorSelectionModel.cardColor
        itemHouseHoldCardBinding.houseHoldCardItemViewModel =
            HouseHoldCardSelectionItemViewModel(
                position,
                cardColorSelectionModel,
                onItemClickListener
            )
        itemHouseHoldCardBinding.executePendingBindings()
    }
}