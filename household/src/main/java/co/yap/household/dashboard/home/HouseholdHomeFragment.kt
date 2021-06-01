package co.yap.household.dashboard.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.modules.dashboard.home.filters.activities.TransactionFiltersActivity
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.notification.responsedtos.HomeNotification
import co.yap.networking.notification.responsedtos.NotificationAction
import co.yap.networking.transactions.requestdtos.REQUEST_PAGE_SIZE
import co.yap.translation.Strings
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.MANUAL_CREDIT
import co.yap.yapcore.constants.Constants.MANUAL_DEBIT
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.livedata.GetAccountInfoLiveData
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
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
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setupToolbar(toolbar = mViewDataBinding.toolbar, toolbarMenu = R.menu.menu_home)
        setHasOptionsMenu(true)
        GetAccountBalanceLiveData.get()
            .observe(this, Observer { state.availableBalance?.value = it?.availableBalance })
        intRecyclersView()
    }

    private fun intRecyclersView() {
        mNotificationAdapter.onItemClickListener = onItemClickListener
        mNotificationAdapter.onChildViewClickListener =
            { view: View, position: Int, data: HomeNotification? ->
                onItemClickListener.onItemClick(view, data!!, position)
            }
        viewModel.notificationAdapter.set(mNotificationAdapter)
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        mViewDataBinding.lyInclude.recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.transactionAdapter?.set(mAdapter)
            pagination = viewModel.getPaginationListener()
            setHasFixedSize(false)
        }
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is HomeNotification) {
                val notification: HomeNotification = mNotificationAdapter.getData()[pos]
                when (notification.action) {
                    NotificationAction.COMPLETE_VERIFICATION -> {
                        launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                            putExtra(
                                Constants.name,
                                SessionManager.user?.currentCustomer?.firstName.toString()
                            )
                            putExtra(Constants.data, false)
                        }
                    }
                    NotificationAction.UPDATE_EMIRATES_ID -> {

                    }
                    NotificationAction.CARD_FEATURES_BLOCKED -> {
                        requireContext().makeCall(SessionManager.helpPhoneNumber)
                    }
                    NotificationAction.HELP_AND_SUPPORT -> {
                        startActivity(
                            FragmentPresenterActivity.getIntent(
                                requireContext(),
                                Constants.MODE_HELP_SUPPORT, null
                            )
                        )
                    }
                    NotificationAction.SET_PIN -> {
                        launchActivity<NavHostPresenterActivity>(
                            options = bundleOf(Card::class.java.name to viewModel.state.card?.value)
                        ) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodes.REQUEST_KYC_DOCUMENTS -> {
                data?.let {
                    val result =
                        data.getBooleanExtra(Constants.result, false)
                    if (result) {
                        startActivityForResult(
                            LocationSelectionActivity.newIntent(
                                context = requireContext(),
                                address = SessionManager.userAddress ?: Address(),
                                headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                                subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle),
                                onBoarding = true
                            ), RequestCodes.REQUEST_FOR_LOCATION
                        )
                    } else {
                        val kycAction =
                            data.getValue(
                                "status",
                                ExtraType.STRING.name
                            ) as? String
//                        if (KYCAction.ACTION_EID_UPDATE.name == kycAction) checkUserStatus()
                    }
                }
            }
            RequestCodes.REQUEST_FOR_LOCATION -> {
                data?.let {
                    val result = it.getBooleanExtra(Constants.ADDRESS_SUCCESS, false)
                    if (result) {
                        GetAccountInfoLiveData.get().observe(viewLifecycleOwner, Observer { })
                    }
                }
            }

            RequestCodes.REQUEST_MEETING_CONFIRMED -> {
                SessionManager.getAccountInfo()
            }

        }

    }


    override fun onClick(id: Int) {
        when (id) {
            R.id.tvFilters -> {
                launchActivityForResult<TransactionFiltersActivity>(init = {
                    putExtra(
                        TransactionFiltersActivity.KEY_FILTER_TXN_FILTERS,
                        TransactionFilters(
                            state.transactionRequest?.amountStartRange,
                            state.transactionRequest?.amountEndRange,
                            incomingTxn = state.transactionRequest?.txnType == MANUAL_CREDIT,
                            outgoingTxn = state.transactionRequest?.txnType == MANUAL_DEBIT,
                            totalAppliedFilter = state.transactionRequest?.totalAppliedFilter ?: 0
                        )
                    )
                }, completionHandler = { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        data?.getParcelableExtra<TransactionFilters?>("txnRequest")?.apply {
                            state.transactionRequest?.number = 0
                            state.transactionRequest?.size = REQUEST_PAGE_SIZE
                            state.transactionRequest?.txnType = getTxnType()
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
