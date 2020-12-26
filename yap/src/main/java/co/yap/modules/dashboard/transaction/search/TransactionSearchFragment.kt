package co.yap.modules.dashboard.transaction.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.transaction.activities.TransactionDetailsActivity
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.skeletonlayout.Skeleton
import co.yap.widgets.skeletonlayout.applySkeleton
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.launchActivity
import kotlinx.android.synthetic.main.fragment_transaction_search.*

class TransactionSearchFragment : BaseBindingFragment<ITransactionSearch.ViewModel>() {
    private lateinit var mAdapter: HomeTransactionAdapter
    private lateinit var skeleton: Skeleton
    private lateinit var mWrappedAdapter: RecyclerView.Adapter<*>
    private lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_transaction_search

    override val viewModel: ITransactionSearch.ViewModel
        get() = ViewModelProviders.of(this).get(TransactionSearchViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intRecyclersView()
        setObservers()
        skeleton = recyclerView.applySkeleton(
            R.layout.item_transaction_list_shimmer,
            5
        )
    }

    private fun setObservers() {
        viewModel.state.stateLiveData?.observe(this, Observer {
            handleShimmerState(it)
        })
        svTransactions.afterTextChanged {
            // if (it.isNotEmpty()) {
            // viewModel.clearCoroutine()
            viewModel.state.transactionRequest?.searchField = it.toLowerCase()
            recyclerView.pagination?.notifyPaginationRestart()
            // }
        }
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.ivCloseSearch -> {
                    requireActivity().finish()
                }
            }
        })
    }

    private fun handleShimmerState(state: State?) {
        when (state?.status) {
            Status.LOADING -> {
                multiStateView.viewState = MultiStateView.ViewState.CONTENT
                //recyclerView.pagination = null
                skeleton.showSkeleton()
            }
            Status.EMPTY -> {
                multiStateView.viewState = MultiStateView.ViewState.EMPTY
            }
            else -> {
                multiStateView.viewState = MultiStateView.ViewState.CONTENT
                //recyclerView.pagination = viewModel.getPaginationListener()
                skeleton.showOriginal()
            }
        }
    }

    private fun intRecyclersView() {
        mRecyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(null)
        mAdapter = HomeTransactionAdapter(emptyMap(), mRecyclerViewExpandableItemManager)
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(mAdapter)
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(mAdapter)
            pagination = viewModel.getPaginationListener()
            setHasFixedSize(false)
        }
        mAdapter.onItemClick =
            { view: View, groupPosition: Int, childPosition: Int, data: Transaction? ->
                data?.let {
                    launchActivity<TransactionDetailsActivity>(requestCode = RequestCodes.REQUEST_FOR_TRANSACTION_NOTE_ADD_EDIT) {
                        putExtra(
                            ExtraKeys.TRANSACTION_OBJECT_STRING.name,
                            it
                        )
                        putExtra(
                            ExtraKeys.TRANSACTION_OBJECT_GROUP_POSITION.name,
                            groupPosition
                        )
                        putExtra(
                            ExtraKeys.TRANSACTION_OBJECT_CHILD_POSITION.name,
                            childPosition
                        )
                    }

                }
            }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}