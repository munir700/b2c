package co.yap.household.dashboard.home

import android.view.View
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
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
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
        setUpAdapter()
        intRecyclerView()
        viewModel.stateLiveData.observe(
            this,
            Observer { if (it.status != Status.IDEAL) handleState(it) })
    }

    private fun setUpAdapter() {
        context?.let {
            viewModel.adapter.set(
                HHNotificationAdapter(
                    NotificationHelper.getNotifications(
                        MyUserManager.user,
                        MyUserManager.card.value,
                        it
                    ),
                    null
                )
            )
        }
        viewModel.adapter.get()?.onItemClickListener = userClickListener
    }

    private val userClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            showToast("pos $pos")
        }
    }

    private fun intRecyclerView() {
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.recyclerView.apply {
            adapter = mWrappedAdapter
            setHasFixedSize(false)
        }
    }

    private val adaptorClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
        }
    }

//    private val loadMoreListener = object : LoadMoreListener {
//        override fun onLoadMore() {
//            if (viewModel.isLast.value == false) {
//                viewModel.homeTransactionRequest.number =
//                    viewModel.homeTransactionRequest.number.inc()
//                viewModel.loadMore()
//            } else {
//                (mViewDataBinding.transactionRecyclerView.rvTransaction?.adapter as? TransactionsAdapter)?.itemCount?.let {
//                    (mViewDataBinding.transactionRecyclerView.rvTransaction?.adapter as? TransactionsAdapter)?.notifyItemRemoved(
//                        it
//                    )
//                }
//            }
//        }
//    }

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
