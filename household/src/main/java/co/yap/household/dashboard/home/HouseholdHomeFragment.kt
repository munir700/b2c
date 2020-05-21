package co.yap.household.dashboard.home

import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import javax.inject.Inject


class HouseholdHomeFragment :
    BaseNavViewModelFragment<FragmentHouseholdHomeBinding, IHouseholdHome.State, HouseHoldHomeVM>() {
    @Inject
    lateinit var mAdapter: HomeTransactionAdapter

    @Inject
    lateinit var mWrappedAdapter: RecyclerView.Adapter<*>

    @Inject
    lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_household_home
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        intRecyclerView()
    }

    private fun intRecyclerView() {
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.recyclerView.apply {
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(mAdapter)
            setHasFixedSize(false)
        }
//        TODO unComment this line only for testing
//        state.transactionMap?.observe(this, Observer {
//            mAdapter.setTransactionData(it)
//        })
    }
}
