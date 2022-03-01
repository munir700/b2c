package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.R
import co.yap.databinding.ItemCardBinding
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.managers.SessionManager

class CardChildViewHolder(
    view: View,
    viewModel: CardChildItemViewModel,
    mDataBinding: ViewDataBinding
) : AbstractExpandableItemViewHolder<Card, CardChildItemViewModel>(
    view,
    viewModel,
    mDataBinding
) {
    private var binding: ItemCardBinding = mDataBinding as (ItemCardBinding)

    override fun setItem(item: Card, position: Int) {
        super.setItem(item, position)
        if (Constants.CARD_TYPE_DEBIT == item.cardType) {
            binding.tvCardCurrency.visibility = View.GONE
            binding.tvCardBalance.visibility = View.GONE
            binding.ivCardArrow.visibility = View.VISIBLE
            if (SessionManager.isFounder.value == true) {
                binding.imgCard.setImageResource(R.drawable.founder_front)
            } else {
                binding.imgCard.setImageResource(R.drawable.card_spare)
            }
        } else {
            binding.imgCard.loadCardImage(item.frontImage)
            binding.tvCardCurrency.visibility = View.VISIBLE
            binding.tvCardBalance.visibility = View.VISIBLE
            binding.ivCardArrow.visibility = View.GONE
        }
    }

}