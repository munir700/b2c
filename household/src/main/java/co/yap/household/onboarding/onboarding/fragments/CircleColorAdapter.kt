package co.yap.household.onboarding.onboarding.fragments

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.R
import co.yap.household.databinding.ItemCardColorSelectionBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter

class CircleColorAdapter(
    private val list: MutableList<CardColorSelectionModel>
) : BaseBindingRecyclerAdapter<CardColorSelectionModel, RecyclerView.ViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return CircleColorItemViewHolder(binding as ItemCardColorSelectionBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_card_color_selection


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is CircleColorItemViewHolder) {
            holder.onBind(list[0], position, onItemClickListener)
        }
    }

}