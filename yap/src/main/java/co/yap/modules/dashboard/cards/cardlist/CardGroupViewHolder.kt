package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.decoration.IStickyHeaderViewHolder
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder

class CardGroupViewHolder (
    view: View,
    viewModel: CardHeaderItemViewModel,
    mDataBinding: ViewDataBinding
) : AbstractExpandableItemViewHolder<Card, CardHeaderItemViewModel>(
    view,
    viewModel,
    mDataBinding
), IStickyHeaderViewHolder {
}