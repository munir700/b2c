package co.yap.household.dashboard.home

import android.app.Activity
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.yap.app.YAPApplication
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.main.HouseHoldDashBoardVM
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.modules.dashboard.home.filters.activities.TransactionFiltersActivity
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.networking.notification.HomeNotification
import co.yap.networking.notification.NotificationAction
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.dagger.di.ViewModelInjectionField
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.interfaces.OnItemClickListener
import javax.inject.Inject

class HouseholdHomeFragment :
    BaseNavViewModelFragment<FragmentHouseholdHomeBinding, IHouseholdHome.State, HouseHoldHomeVM>() {
    @Inject
    lateinit var mNotificationAdapter: HHNotificationAdapter

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
        setupToolbar(mViewDataBinding.toolbar, R.menu.menu_home)
        setHasOptionsMenu(true)
        GetAccountBalanceLiveData.get()
            .observe(this, Observer { state.availableBalance?.value = it?.availableBalance })
        intRecyclersView()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun intRecyclersView() {
        mNotificationAdapter.onItemClickListener = onItemClickListener
        viewModel.notificationAdapter.set(mNotificationAdapter)
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.lyInclude.recyclerView.apply {
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(mAdapter)
            setHasFixedSize(false)
        }
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is HomeNotification) {
                val notification: HomeNotification = mNotificationAdapter.getData().get(pos)
                when (notification.action) {
                    NotificationAction.SET_PIN -> {
                        launchActivity<NavHostPresenterActivity> {
                            putExtra(NAVIGATION_Graph_ID, R.navigation.hh_set_card_pin_navigation)
                            putExtra(
                                NAVIGATION_Graph_START_DESTINATION_ID,
                                R.id.HHSetPinCardReviewFragment
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.tvFilters -> {
                launchActivityForResult<TransactionFiltersActivity>(init = {
                    putExtra(
                        TransactionFiltersActivity.KEY_FILTER_TXN_FILTERS,
                        TransactionFilters()
                    )
                }, completionHandler = { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        data?.getParcelableExtra<TransactionFilters?>("txnRequest")?.apply {
                            state.transactionRequest?.number = 0
                            state.transactionRequest?.size = 100
                            state.transactionRequest?.txnType = null
                            state.transactionRequest?.amountStartRange = amountStartRange
                            state.transactionRequest?.amountEndRange = amountEndRange
                            state.transactionRequest?.title = null
                            state.transactionRequest?.totalAppliedFilter =
                                totalAppliedFilter
                            viewModel.requestTransactions(state.transactionRequest, false)
                        }
                    }
                })

            }
        }
    }

    fun onCloseClick(notification: HomeNotification) {
        state.showNotification.value = false
    }

    override fun setHomeAsUpIndicator() = R.drawable.ic_search_white
    override fun toolBarVisibility() = false
}
