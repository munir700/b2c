package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.databinding.ItemCardHeaderBinding
import co.yap.modules.onboarding.interfaces.IMeetingConfirmation
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.CardListData
import co.yap.translation.Strings
import co.yap.widgets.advrecyclerview.decoration.IStickyHeaderViewHolder
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder

class CardGroupViewHolder(
    view: View,
    viewModel: CardHeaderItemViewModel,
    mDataBinding: ViewDataBinding
) : AbstractExpandableItemViewHolder<CardListData, CardHeaderItemViewModel>(
    view,
    viewModel,
    mDataBinding
), IStickyHeaderViewHolder {
    private var binding: ItemCardHeaderBinding = mDataBinding as (ItemCardHeaderBinding)

    override fun setItem(item: CardListData, position: Int) {
        super.setItem(item, position)
        if (Constants.CARD_TYPE_DEBIT == item.cardType) {
            binding.tvCardBalnc.visibility = View.VISIBLE
            binding.tvCardHeader.text = "Physical cards"
        } else {
            binding.tvCardBalnc.visibility = View.GONE
            binding.tvCardHeader.text = "Virtual cards"
        }
    }
}