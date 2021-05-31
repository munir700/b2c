package co.yap.modules.dashboard.cards.cardlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import kotlinx.android.synthetic.main.fragment_transaction_search.*

class CardsListFragment : YapDashboardChildFragment<ICardsList.ViewModel>(), ICardsList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_cards_list

    override val viewModel: CardsListViewModel
        get() = ViewModelProviders.of(this).get(CardsListViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun intRecyclersView() {
        viewModel.mRecyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(null)
        viewModel.mAdapter.set(
            CardListAdapter(
                mutableMapOf(),
                viewModel.mRecyclerViewExpandableItemManager
            )
        )
        viewModel.mWrappedAdapter.set(
            viewModel.mRecyclerViewExpandableItemManager.createWrappedAdapter(
                viewModel.mAdapter
            )
        )
        viewModel.mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            viewModel.mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = viewModel.mWrappedAdapter.get()
            /* viewModel.transactionAdapter?.set(mAdapter)
             pagination = viewModel.getPaginationListener()*/
            setHasFixedSize(true)
        }
        viewModel.mAdapter.get()?.onItemClick =
            { view: View, groupPosition: Int, childPosition: Int, data: Card? ->
                data?.let {

                }
            }
    }

}