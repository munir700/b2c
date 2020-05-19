package co.yap.household.dashboard.home

import android.view.View
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import co.yap.yapcore.transactions.TransactionsAdapter
import co.yap.yapcore.transactions.interfaces.LoadMoreListener
import kotlinx.android.synthetic.main.fragment_household_home.*

class HouseholdHomeFragment :
    BaseNavViewModelFragment<FragmentHouseholdHomeBinding, IHouseholdHome.State, HouseHoldHomeVM>() {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_household_home

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        setUpAdapter()
        mViewDataBinding.transactionRecyclerView.setItemClickListener(adaptorClickListener)
        mViewDataBinding.transactionRecyclerView.setLoadMoreListener(loadMoreListener)
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

    private val adaptorClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
        }
    }

    private val loadMoreListener = object : LoadMoreListener {
        override fun onLoadMore() {
            if (viewModel.isLast.value == false) {
                viewModel.homeTransactionRequest.number =
                    viewModel.homeTransactionRequest.number.inc()
                viewModel.loadMore()
            } else {
                (mViewDataBinding.transactionRecyclerView.rvTransaction?.adapter as? TransactionsAdapter)?.itemCount?.let {
                    (mViewDataBinding.transactionRecyclerView.rvTransaction?.adapter as? TransactionsAdapter)?.notifyItemRemoved(
                        it
                    )
                }
            }
        }
    }

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> multiStateView?.viewState = MultiStateView.ViewState.LOADING
            Status.EMPTY -> multiStateView?.viewState = MultiStateView.ViewState.EMPTY
            Status.ERROR -> multiStateView?.viewState = MultiStateView.ViewState.ERROR
            Status.SUCCESS -> multiStateView?.viewState = MultiStateView.ViewState.CONTENT
        }
    }
}