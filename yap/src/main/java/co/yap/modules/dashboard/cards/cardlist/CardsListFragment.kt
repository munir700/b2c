package co.yap.modules.dashboard.cards.cardlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardsListBinding
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.SpacesItemDecoration
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.helpers.extentions.dimen

class CardsListFragment : YapDashboardChildFragment<ICardsList.ViewModel>(), ICardsList.View {

    private lateinit var mAdapter: CardListAdapter
    private lateinit var mWrappedAdapter: RecyclerView.Adapter<*>
    private lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager

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
            bundle.getParcelableArrayList<Card>("cardslist")?.apply {
                viewModel.state.cardMap = sortedBy { card ->
                    card.cardType
                }.distinct().groupBy { card ->
                    card.cardType
                }.toMutableMap()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intRecyclersView()
    }

    private fun intRecyclersView() {
        mRecyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(null)
        mAdapter = CardListAdapter(mutableMapOf(), mRecyclerViewExpandableItemManager)
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(mAdapter)
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        getDataBindingView<FragmentCardsListBinding>().recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.cardAdapter?.set(mAdapter)
            mAdapter.setData(viewModel.state.cardMap)
            setHasFixedSize(true)
        }
        mAdapter.onItemClick =
            { view: View, groupPosition: Int, childPosition: Int, data: Card? ->
                data?.let {
                }
            }
    }

}