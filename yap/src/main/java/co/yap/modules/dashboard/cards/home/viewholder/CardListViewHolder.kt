package co.yap.modules.dashboard.cards.home.viewholder

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.modules.dashboard.cards.home.viewmodels.CardChildItemViewModel
import co.yap.modules.dashboard.transaction.search.SearchTransactionChildItemVM
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.transaction.Transaction
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