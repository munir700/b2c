package co.yap.household.onboarding.onboarding.fragments

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.databinding.ItemHouseHoldCardBinding
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCardSelectionItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class HouseHoldCardSelectionItemViewHolder(private val itemHouseHoldCardBinding: ItemHouseHoldCardBinding) :
    RecyclerView.ViewHolder(itemHouseHoldCardBinding.root) {
    fun onBind(
        color: Int,
        position: Int,
        dimensions: IntArray,
        onItemClickListener: OnItemClickListener?
    ) {
        val params = itemHouseHoldCardBinding.ivCard.layoutParams as ConstraintLayout.LayoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        itemHouseHoldCardBinding.ivCard.layoutParams = params
        itemHouseHoldCardBinding.houseHoldCardItemViewModel?.postion = position
        itemHouseHoldCardBinding.houseHoldCardItemViewModel?.color = color
        itemHouseHoldCardBinding.houseHoldCardItemViewModel =
            HouseHoldCardSelectionItemViewModel(position, color, onItemClickListener)
        itemHouseHoldCardBinding.executePendingBindings()
    }
}