package co.yap.modules.dashboard.cards.cardlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.helpers.DateUtils
import kotlinx.android.synthetic.main.fragment_transaction_search.*
import kotlinx.android.synthetic.main.layout_core_toolbar.*

class CardsListFragment : YapDashboardChildFragment<ICardsList.ViewModel>(), ICardsList.View {

    private val mWrappedAdapter: RecyclerView.Adapter<*> by lazy {
        mRecyclerViewExpandableItemManager.createWrappedAdapter(mAdapter)
    }
    private val mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager by lazy {
        RecyclerViewExpandableItemManager(null)
    }
    private val mAdapter: CardListAdapter by lazy {
        CardListAdapter(
            viewModel.cardMap ?: mutableMapOf(),
            mRecyclerViewExpandableItemManager
        )
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_cards_list

    override val viewModel: CardsListViewModel
        get() = ViewModelProviders.of(this).get(CardsListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
    }

    private fun initArguments() {
        arguments?.let { bundle ->
            viewModel.cards = bundle.getParcelableArrayList<Card>("cardslist") ?: mutableListOf()
            viewModel.cardMap =
                viewModel.cards.sortedByDescending { card ->
                    DateUtils.stringToDate(
                        card.activationDate ?: "",
                        DateUtils.SERVER_DATE_FORMAT,
                        DateUtils.UTC
                    )?.time
                }.distinct().groupBy { card ->
                        card.cardType
                    }.toMutableMap()
            mergeReduce(viewModel.cardMap)
        }
    }

    private fun mergeReduce(newMap: MutableMap<String?, List<Card>>) {
        viewModel.cardMap.let { map ->
            val tempMap = mutableMapOf<String?, List<Card>>()
            var keyToRemove: String? = null
            tempMap.putAll(newMap)
            newMap.keys.forEach { key ->
                if (map.containsKey(key)) {
                    keyToRemove = key
                    return@forEach
                }
            }
            keyToRemove?.let {
                val newCards = newMap.getValue(it)
                val oldCards = map.getValue(it).toMutableList()
                oldCards.addAll(newCards)
                viewModel.cardMap[it] = oldCards
                tempMap.remove(it)
            }
            val groupCount = viewModel.cardAdapter.get()?.groupCount ?: 0
            viewModel.cardMap.putAll(tempMap)
            viewModel.cardAdapter.get()?.expandableItemManager?.notifyGroupItemRangeInserted(
                groupCount - 1,
                tempMap.size
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intRecyclersView()
    }

    private fun intRecyclersView() {
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.cardAdapter.set(mAdapter)
            setHasFixedSize(true)
        }
        mAdapter.onItemClick =
            { view: View, groupPosition: Int, childPosition: Int, data: Card? ->
                data?.let {

                }
            }
    }

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when(id){
            R.id.ivLeftIcon ->{activity?.onBackPressed()}
            R.id.ivRightIcon -> {

            }
        }
    }

}