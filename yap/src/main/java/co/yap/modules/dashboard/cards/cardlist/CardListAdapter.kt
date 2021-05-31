package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.advrecyclerview.utils.BaseExpandableRVAdapter

class CardListAdapter(
    internal var cardsData: MutableMap<String?, List<Card>>,
    val expandableItemManager: RecyclerViewExpandableItemManager
) :
    BaseExpandableRVAdapter<Card, CardChildItemViewModel, CardListViewHolder, Card, CardHeaderItemViewModel, CardGroupViewHolder>() {

    var onItemClick: ((view: View, groupPosition: Int, childPosition: Int, data: Card?) -> Unit)? =
        null
    override fun onBindGroupViewHolder(
        holder: CardGroupViewHolder,
        groupPosition: Int,
        viewType: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onBindChildViewHolder(
        holder: CardListViewHolder,
        groupPosition: Int,
        childPosition: Int,
        viewType: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getChildViewHolder(
        view: View,
        viewModel: CardChildItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): CardListViewHolder {
        TODO("Not yet implemented")
    }

    override fun getGroupViewHolder(
        view: View,
        viewModel: CardHeaderItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): CardGroupViewHolder {
        TODO("Not yet implemented")
    }

    override fun getChildLayoutId(viewType: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getGroupLayoutId(viewType: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getGroupViewModel(viewType: Int): CardHeaderItemViewModel {
        TODO("Not yet implemented")
    }

    override fun getGroupVariableId(): Int {
        TODO("Not yet implemented")
    }

    override fun getChildViewModel(viewType: Int): CardChildItemViewModel {
        TODO("Not yet implemented")
    }

    override fun getChildVariableId(): Int {
        TODO("Not yet implemented")
    }

    override fun getGroupCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getChildCount(groupPosition: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getGroupId(groupPosition: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getGroupItemViewType(groupPosition: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getChildItemViewType(groupPosition: Int, childPosition: Int): Int {
        TODO("Not yet implemented")
    }
}