package co.yap.modules.dashboard.cards.cardlist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.networking.cards.CardListData
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.advrecyclerview.utils.BaseExpandableRVAdapter
import co.yap.yapcore.helpers.extentions.onClick
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.helpers.extentions.parseToLong

class CardListAdapter(
    private var cardsData: MutableMap<String?, List<Card>>,
    val expandableItemManager: RecyclerViewExpandableItemManager
) :
    BaseExpandableRVAdapter<Card, CardChildItemViewModel, CardChildViewHolder, CardListData, CardHeaderItemViewModel, CardGroupViewHolder>() {

    var onItemClick: ((view: View, groupPosition: Int, childPosition: Int, data: Card?) -> Unit)? =
        null

    init {
        setHasStableIds(true)
    }

    override fun getInitialGroupExpandedState(groupPosition: Int) = false
    override fun onBindGroupViewHolder(
        holder: CardGroupViewHolder,
        groupPosition: Int,
        viewType: Int
    ) {
        val card = cardsData[cardsData.keys.toList()[groupPosition]] ?: mutableListOf()
        val groupData = CardListData(cardType = card[0].cardType, cards = card)
        holder.setItem(groupData, groupPosition)
    }

    override fun onBindChildViewHolder(
        holder: CardChildViewHolder,
        groupPosition: Int,
        childPosition: Int,
        viewType: Int
    ) {
        val cards =
            cardsData[cardsData.keys.toList()[groupPosition]]?.get(childPosition)
        cards?.let {card->
            card.showDivider = childPosition != cardsData[cardsData.keys.toList()[groupPosition]]?.size?.minus(1) ?: 0
            holder.setItem(card, childPosition)
        }
        holder.onClick { view, position, type ->
            onItemClick?.invoke(view, groupPosition, childPosition, cards)
        }
    }

    override fun getChildViewHolder(
        view: View,
        viewModel: CardChildItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = CardChildViewHolder(view, viewModel, mDataBinding)

    override fun getGroupViewHolder(
        view: View,
        viewModel: CardHeaderItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = CardGroupViewHolder(view, viewModel, mDataBinding)

    override fun getChildLayoutId(viewType: Int) = getChildViewModel(viewType).layoutRes()

    override fun getGroupLayoutId(viewType: Int) = getGroupViewModel(viewType).layoutRes()

    override fun getGroupViewModel(viewType: Int) = CardHeaderItemViewModel()

    override fun getGroupVariableId() = BR.cardsGroupItemViewModel

    override fun getChildViewModel(viewType: Int) = CardChildItemViewModel()

    override fun getChildVariableId() = BR.cardChildItemViewModel

    override fun getGroupCount() = cardsData.size

    override fun getChildCount(groupPosition: Int) =
        cardsData[cardsData.keys.toList()[groupPosition]]?.size ?: 0


    override fun getGroupId(groupPosition: Int) = groupPosition.plus(1L)

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        val childId =
             childPosition.plus(groupPosition).toLong()
        return childId
    }

    override fun getGroupItemViewType(groupPosition: Int) = groupPosition

    override fun getChildItemViewType(groupPosition: Int, childPosition: Int): Int {
        return childPosition.plus(groupPosition)
    }

    fun setData(cardsMap: MutableMap<String?, List<Card>>?) {
        cardsMap?.let {
            this.cardsData = cardsMap
            notifyDataSetChanged()
        } ?: run {
            cardsData = mutableMapOf()
            notifyDataSetChanged()
        }
    }
}