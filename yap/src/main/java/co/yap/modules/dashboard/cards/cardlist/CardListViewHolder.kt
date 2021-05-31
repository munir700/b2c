package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder

class CardListViewHolder(
    view: View,
    viewModel: CardChildItemViewModel,
    mDataBinding: ViewDataBinding
) : AbstractExpandableItemViewHolder<Card, CardChildItemViewModel>(
    view,
    viewModel,
    mDataBinding
) {
}