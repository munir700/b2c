package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.databinding.ItemCardHeaderBinding
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.CardListData
import co.yap.widgets.advrecyclerview.decoration.IStickyHeaderViewHolder
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager

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
            binding.tvCardBalnc.text = item.cards[0].availableBalance.toFormattedCurrency(
                true,
                SessionManager.getDefaultCurrency(),
                true
            )
            binding.tvCardHeader.text = "Physical cards"
        } else {
            binding.tvCardBalnc.text = "${item.cards.size} cards"
            binding.tvCardHeader.text = "Virtual cards"
        }
    }
}