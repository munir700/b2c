package co.yap.household.dashboard.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
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
        viewModel.stateLiveData.observe(
            this,
            Observer { if (it.status != Status.IDEAL) handleState(it) })
    }

    private fun intRecyclerView() {
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.recyclerView.apply {
            adapter = mWrappedAdapter
            setHasFixedSize(false)
        }
    }

    fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> mViewDataBinding.multiStateView.viewState =
                MultiStateView.ViewState.LOADING
            Status.EMPTY -> mViewDataBinding.multiStateView.viewState =
                MultiStateView.ViewState.EMPTY
            Status.ERROR -> mViewDataBinding.multiStateView.viewState =
                MultiStateView.ViewState.ERROR
            Status.SUCCESS -> mViewDataBinding.multiStateView.viewState =
                MultiStateView.ViewState.CONTENT
        }
    }
}
