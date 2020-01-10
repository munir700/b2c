package co.yap.household.onboarding.onboarding.fragments

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.R
import co.yap.household.databinding.ItemCardColorSelectionBinding
import co.yap.household.onboarding.onboarding.viewmodels.CircleColorItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class CircleColorItemViewHolder(private var itemCardColorSelectionBinding: ItemCardColorSelectionBinding) :
    RecyclerView.ViewHolder(itemCardColorSelectionBinding.root) {
    fun onBind(
        cardColorSelectionModel: CardColorSelectionModel,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {

        itemCardColorSelectionBinding.clMain.background =
            ContextCompat.getDrawable(itemCardColorSelectionBinding.clMain.context, R.drawable.bg_card_color_selection_circle_grey)
        itemCardColorSelectionBinding.itemColorViewModel?.position = position
        itemCardColorSelectionBinding.itemColorViewModel =
            CircleColorItemViewModel(position, cardColorSelectionModel, onItemClickListener)
        itemCardColorSelectionBinding.executePendingBindings()

    }
}