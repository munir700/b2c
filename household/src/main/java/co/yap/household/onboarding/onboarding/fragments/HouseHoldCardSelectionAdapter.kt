package co.yap.household.onboarding.onboarding.fragments

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.R
import co.yap.household.databinding.ItemHouseHoldCardBinding
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.helpers.Utils

class HouseHoldCardSelectionAdapter(
    context: Context,
    private val list: MutableList<CardColorSelectionModel>
) :
    BaseBindingRecyclerAdapter<CardColorSelectionModel, RecyclerView.ViewHolder>(list) {
    private var dimensions: IntArray = Utils.getCardDimensions(context, 50, 45)

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return HouseHoldCardSelectionItemViewHolder(binding as ItemHouseHoldCardBinding)
    }

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_house_hold_card

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is HouseHoldCardSelectionItemViewHolder) {
            holder.onBind(list[0], position, dimensions, onItemClickListener)
        }
    }
}