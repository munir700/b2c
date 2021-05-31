package co.yap.modules.dashboard.cards.home.viewholder

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.modules.dashboard.cards.home.viewmodels.CardHeaderItemViewModel
import co.yap.modules.dashboard.transaction.search.SearchTransactionGroupItemVM
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
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