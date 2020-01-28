package co.yap.modules.dashboard.home.fragments

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.app.YAPApplication.Companion.homeTransactionsRequest
import co.yap.databinding.FragmentYapHomeBinding
import co.yap.modules.dashboard.cards.analytics.main.activities.CardAnalyticsActivity
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter
import co.yap.modules.dashboard.home.adaptor.NotificationAdapter
import co.yap.modules.dashboard.home.adaptor.TransactionsHeaderAdapter
import co.yap.modules.dashboard.home.filters.activities.TransactionFiltersActivity
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.dashboard.home.helpers.AppBarStateChangeListener
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.interfaces.NotificationItemClickListener
import co.yap.modules.dashboard.home.models.Notification
import co.yap.modules.dashboard.home.viewmodels.YapHomeViewModel
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.modules.dashboard.transaction.activities.TransactionDetailsActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants.ADDRESS
import co.yap.yapcore.constants.Constants.ADDRESS_SUCCESS
import co.yap.yapcore.constants.Constants.BROADCAST_UPDATE_TRANSACTION
import co.yap.yapcore.constants.Constants.MODE_MEETING_CONFORMATION
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.NotificationStatus
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.fixSwipeToRefresh
import co.yap.yapcore.helpers.extentions.trackEvent
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.leanplum.TrackEvents
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.appbar.AppBarLayout
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.content_fragment_yap_home.*
import kotlinx.android.synthetic.main.fragment_yap_home.*
import kotlinx.android.synthetic.main.view_graph.*
import kotlin.math.abs


class YapHomeFragment : YapDashboardChildFragment<IYapHome.ViewModel>(), IYapHome.View,
    NotificationItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mAdapter: NotificationAdapter
    private lateinit var parentViewModel: YapDashBoardViewModel
    private var notificationsList: ArrayList<Notification> = ArrayList()
    override var transactionViewHelper: TransactionsViewHelper? = null

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerTransactionBroadcast()
        initComponents()
        setObservers()
        setAvailableBalance(viewModel.state.availableBalance)
        trackEvent(TrackEvents.YAP_ONBOARDED)
    }

    private fun initComponents() {
        rvTransaction.layoutManager = LinearLayoutManager(context)
        rvTransaction.adapter =
            TransactionsHeaderAdapter(mutableListOf(), adaptorlistener)
        getRecycleViewAdaptor()?.allowFullItemClickListener = true

        getBindings().refreshLayout.setOnRefreshListener(this)
        rvTransactionsBarChart.adapter = GraphBarsAdapter(mutableListOf(), viewModel)

        getBindings().lyInclude.rvTransaction.apply {
            fixSwipeToRefresh(getBindings().refreshLayout)
        }

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val pram = frameLayout.layoutParams
            if (abs(verticalOffset) <= 5) {
                frameLayout.alpha = 1f
                //Log.d("vertical Alpha>>", "$alp")
                pram.height = appBarLayout.totalScrollRange
                frameLayout.layoutParams = pram
            } else {
                if (Math.abs(verticalOffset) > 0)
                    frameLayout.alpha = 10 / abs(verticalOffset).toFloat()
                pram.height = appBarLayout?.totalScrollRange?.plus(verticalOffset)!!
                frameLayout.layoutParams = pram
            }
        })
    }

    override fun onRefresh() {
        if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
            viewModel.isRefreshing.value = true
            homeTransactionsRequest.number = 0
            viewModel.requestAccountTransactions()
            getBindings().refreshLayout.isRefreshing = false
            getBindings().appbar.setExpanded(true)
        } else {
            getBindings().refreshLayout.isRefreshing = false
        }
    }

    private val adaptorlistener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Content) {
                startActivity(
                    TransactionDetailsActivity.newIntent(
                        requireContext(),
                        data.transactionId
                    )
                )
            }
        }
    }

    override fun setObservers() {
        listenForToolbarExpansion()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                viewModel.EVENT_SET_CARD_PIN -> {
                    startActivity(
                        SetCardPinWelcomeActivity.newIntent(
                            requireContext(),
                            viewModel.debitCardSerialNumber
                        )
                    )
                }
                viewModel.ON_ADD_NEW_ADDRESS_EVENT -> {
                    startActivity(
                        FragmentPresenterActivity.getIntent(
                            requireContext(),
                            MODE_MEETING_CONFORMATION,
                            null
                        )
                    )
                    MyUserManager.user?.notificationStatuses =
                        NotificationStatus.MEETING_SCHEDULED.name
                    activity?.finish()
                }
                R.id.ivMenu -> parentView?.toggleDrawer()
                R.id.rlFilter -> {
                    if (null != viewModel.transactionsLiveData.value && viewModel.transactionsLiveData.value?.isEmpty() == true && homeTransactionsRequest.totalAppliedFilter == 0 || viewModel.state.isTransEmpty.get() == true) {
                        if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus)

//                            showErrorSnackBar("No Transactions Found")
                            return@Observer
                    } else {
                        if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus)
                            startActivityForResult(
                                TransactionFiltersActivity.newIntent(
                                    requireContext(),
                                    viewModel.txnFilters
                                ),
                                RequestCodes.REQUEST_TXN_FILTER
                            )
                    }
                }

                R.id.lyAnalytics -> startActivity(
                    Intent(
                        requireContext(),
                        CardAnalyticsActivity::class.java
                    )
                )
                R.id.lyAdd -> Utils.showComingSoon(requireContext())

            }
        })
        parentViewModel =
            activity?.let { ViewModelProviders.of(it).get(YapDashBoardViewModel::class.java) }!!

        parentViewModel.getAccountInfoSuccess.observe(this, Observer { value ->
            when (value) {
                true -> checkUserStatus()
            }
        })

        MyUserManager.cardBalance.observe(this, Observer { value ->
            setAvailableBalance(value.availableBalance.toString())
        })

        viewModel.transactionsLiveData.observe(this, Observer {
            if (viewModel.isLoadMore.value!!) {
                if (getRecycleViewAdaptor()?.itemCount!! == 0) getBindings().appbar.setExpanded(true)

                if (getRecycleViewAdaptor()?.itemCount!! > 0)
                    getRecycleViewAdaptor()?.removeItemAt(getRecycleViewAdaptor()?.itemCount!! - 1)

                val listToAppend: MutableList<HomeTransactionListData> = mutableListOf()
                val oldData = getGraphRecycleViewAdapter()?.getDataList()
                for (parentItem in it) {

                    var shouldAppend = false
                    for (i in 0 until oldData?.size!!) {
                        if (parentItem.date == oldData[i].date) {
                            if (parentItem.content.size != oldData[i].content.size) {
                                shouldAppend = true
                                break
                            }
                            shouldAppend = true
                            break
                        }
                    }
                    if (!shouldAppend)
                        listToAppend.add(parentItem)
                }
                getGraphRecycleViewAdapter()?.addList(listToAppend)
                getRecycleViewAdaptor()?.addList(listToAppend)
            } else {
                if (it.isEmpty()) {
                    if (0 < viewModel.state.filterCount.get() ?: 0)
                        viewModel.state.isTransEmpty.set(true)
                } else {
                    checkUserStatus()
                    getRecycleViewAdaptor()?.setList(it)
                    getGraphRecycleViewAdapter()?.setList(it)
                    transactionViewHelper?.setTooltipOnZero()
                }
            }
        })

//        getGraphRecycleViewAdapter()?.setItemListener(listener)
        getRecycleViewAdaptor()?.setItemListener(adaptorlistener)
        getRecycleViewAdaptor()?.allowFullItemClickListener = true
        //getBindings().lyInclude.rvTransaction.addOnScrollListener(endlessScrollListener)
        getBindings().lyInclude.rvTransaction.addOnScrollListener(
            object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        getBindings().lyInclude.rvTransaction.layoutManager as LinearLayoutManager
                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisiblePosition == layoutManager.itemCount - 1) {
                        if (!viewModel.isLoadMore.value!! && !viewModel.isLast.value!!) {
                            viewModel.isLoadMore.value = true
                        }
                    }
                }
            })

        viewModel.isLoadMore.observe(this, Observer
        {
            if (it) {
                YAPApplication.homeTransactionsRequest.number =
                    YAPApplication.homeTransactionsRequest.number + 1
                val item =
                    getRecycleViewAdaptor()?.getDataForPosition(getRecycleViewAdaptor()?.itemCount!! - 1)
                        ?.copy()
                item?.totalAmount = "loader"
                getRecycleViewAdaptor()?.addListItem(item!!)
                viewModel.loadMore()
            } else {
                // if (getRecycleViewAdaptor()?.itemCount!! > 0)
                //     getRecycleViewAdaptor()?.removeItemAt(getRecycleViewAdaptor()?.itemCount!! - 1)
            }

        })
    }

    private fun checkUserStatus() {

        when (MyUserManager.user?.notificationStatuses) {
            Constants.USER_STATUS_ON_BOARDED -> {
                ivNoTransaction.visibility = View.VISIBLE
                addCompleteVerificationNotification()
            }
            Constants.USER_STATUS_MEETING_SUCCESS -> {
                ivNoTransaction.visibility = View.VISIBLE
                addSetPinNotification()
            }
            Constants.USER_STATUS_MEETING_SCHEDULED -> {
                ivNoTransaction.visibility = View.VISIBLE
                notificationsList.clear()
                mAdapter = NotificationAdapter(
                    notificationsList,
                    requireContext(),
                    this
                )
                mAdapter.notifyDataSetChanged()
            }
            co.yap.yapcore.constants.Constants.USER_STATUS_CARD_ACTIVATED -> {
                notificationsList.clear()
                mAdapter = NotificationAdapter(
                    notificationsList,
                    requireContext(),
                    this
                )
                mAdapter.notifyDataSetChanged()
            }
        }

        if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
            showTransactionsAndGraph()
            notificationsList.clear()
            mAdapter = NotificationAdapter(
                notificationsList,
                requireContext(),
                this
            )
            mAdapter.notifyDataSetChanged()
        } else {
            viewModel.state.isTransEmpty.set(true)
        }
    }

    private fun showTransactionsAndGraph() {
        if (viewModel.transactionsLiveData.value.isNullOrEmpty()) {
            viewModel.state.isTransEmpty.set(true)
        } else {
            viewModel.state.isTransEmpty.set(false)
            view?.let {
                transactionViewHelper = TransactionsViewHelper(
                    requireContext(),
                    it,
                    viewModel
                )
                getGraphRecycleViewAdapter()?.helper = transactionViewHelper
            }
        }
    }

    private fun addSetPinNotification() {
        notificationsList.add(
            Notification(
                "Set your card PIN",
                "Now create a unique 4-digit PIN to be able to use your debit card for purchases and withdrawals",
                "",
                Constants.NOTIFICATION_ACTION_SET_PIN,
                "",
                ""
            )
        )
        mAdapter = NotificationAdapter(
            notificationsList,
            requireContext(),
            this
        )
        rvNotificationList.setSlideOnFling(false)
        rvNotificationList.setOverScrollEnabled(true)
        rvNotificationList.adapter = mAdapter
        //rvNotificationList.addOnItemChangedListener(this)
        //rvNotificationList.addScrollStateChangeListener(this)
        rvNotificationList.smoothScrollToPosition(0)
        rvNotificationList.setItemTransitionTimeMillis(100)
        rvNotificationList.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )
    }

    private fun addCompleteVerificationNotification() {
        notificationsList.add(
            Notification(
                "Complete Verification",
                "Complete verification to activate your account",
                "",
                Constants.NOTIFICATION_ACTION_COMPLETE_VERIFICATION,
                "",
                ""
            )
        )
        mAdapter = NotificationAdapter(
            notificationsList,
            requireContext(),
            this
        )
        rvNotificationList.setSlideOnFling(false)
        rvNotificationList.setOverScrollEnabled(true)
        rvNotificationList.adapter = mAdapter
        //rvNotificationList.addOnItemChangedListener(this)
        //rvNotificationList.addScrollStateChangeListener(this)
        rvNotificationList.smoothScrollToPosition(0)
        rvNotificationList.setItemTransitionTimeMillis(100)
        rvNotificationList.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )

    }

    override fun onResume() {
        super.onResume()
        if (co.yap.yapcore.constants.Constants.USER_STATUS_CARD_ACTIVATED == MyUserManager.user?.notificationStatuses) {
            checkUserStatus()
        }
        viewModel.state.filterCount.set(homeTransactionsRequest.totalAppliedFilter)
        MyUserManager.updateCardBalance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterTransactionBroadcast()
        getBindings().appbar.removeOnOffsetChangedListener(appbarListener)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    private fun setAvailableBalance(balance: String) {
        try {
            val ss1 = SpannableString(Utils.getFormattedCurrency(balance))
            if (ss1.isNotEmpty() && ss1.contains(".")) {
                val balanceAfterDot = ss1.split(".")
                ss1.setSpan(
                    RelativeSizeSpan(0.5f),
                    balanceAfterDot[0].length,
                    balanceAfterDot[0].length + balanceAfterDot[1].length + 1,
                    0
                )
                getBindings().tvAvailableBalance.text = ss1
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(notification: Notification) {
        when (notification.action) {
            Constants.NOTIFICATION_ACTION_SET_PIN -> viewModel.getDebitCards()
            Constants.NOTIFICATION_ACTION_COMPLETE_VERIFICATION -> {

                startActivityForResult(
                    DocumentsDashboardActivity.getIntent(
                        requireContext(),
                        MyUserManager.user?.currentCustomer?.firstName.toString(),
                        false
                    ), RequestCodes.REQUEST_KYC_DOCUMENTS
                )
                //activity?.finish()
            }

        }
    }

    private val appbarListener = object : AppBarStateChangeListener() {
        override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
            if (state == State.COLLAPSED) {
                transactionViewHelper?.onToolbarCollapsed()
            } else if (state == State.EXPANDED) {
                transactionViewHelper?.onToolbarExpanded()
            }
        }
    }

    private fun listenForToolbarExpansion() {
        getBindings().appbar.addOnOffsetChangedListener(appbarListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodes.REQUEST_KYC_DOCUMENTS -> {
                data?.let {
                    val result = data.getBooleanExtra(DocumentsDashboardActivity.result, false)
                    if (result)
                        startActivityForResult(
                            LocationSelectionActivity.newIntent(
                                context = requireContext(),
                                address = MyUserManager.userAddress ?: Address(),
                                headingTitle = getString(Strings.screen_meeting_location_display_text_add_new_address_title),
                                subHeadingTitle = getString(Strings.screen_meeting_location_display_text_subtitle)
                            ), RequestCodes.REQUEST_FOR_LOCATION
                        )
                }
            }
            RequestCodes.REQUEST_FOR_LOCATION -> {
                data?.let {
                    val result = it.getBooleanExtra(ADDRESS_SUCCESS, false)
                    if (result) {
                        val address = it.getParcelableExtra<Address>(ADDRESS)
                        viewModel.requestOrderCard(address)
                    }
                }
            }
            RequestCodes.REQUEST_TXN_FILTER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val filters: TransactionFilters? =
                        data?.getParcelableExtra<TransactionFilters?>("txnRequest")
                    if (viewModel.txnFilters != filters) {
                        setTransactionRequest(filters)
                        getFilterTransactions()
                    }
                }
            }
        }
    }

    private fun setTransactionRequest(filters: TransactionFilters?) {
        filters?.let {
            viewModel.txnFilters = it
            homeTransactionsRequest.number = 0
            homeTransactionsRequest.size = YAPApplication.pageSize
            homeTransactionsRequest.txnType = getTxnType()
            homeTransactionsRequest.amountStartRange = it.amountStartRange
            homeTransactionsRequest.amountEndRange = it.amountEndRange
            homeTransactionsRequest.title = null
            homeTransactionsRequest.totalAppliedFilter = getTotalAppliedFilter()
        }
    }

    private fun getTxnType(): String? {
        return if (viewModel.txnFilters.incomingTxn == false && viewModel.txnFilters.outgoingTxn == false || viewModel.txnFilters.incomingTxn == true && viewModel.txnFilters.outgoingTxn == true) {
            null
        } else if (viewModel.txnFilters.incomingTxn == true)
            co.yap.yapcore.constants.Constants.MANUAL_CREDIT
        else
            co.yap.yapcore.constants.Constants.MANUAL_DEBIT
    }

    private fun getTotalAppliedFilter(): Int {
        var count = viewModel.txnFilters.totalAppliedFilter
        if (viewModel.txnFilters.incomingTxn == true) count++
        if (viewModel.txnFilters.outgoingTxn == true) count++

        return count
    }

    private fun getFilterTransactions() {
        //rvTransaction.adapter =
        //    TransactionsHeaderAdapter(mutableListOf(), adaptorlistener)

        //rvTransactionsBarChart.adapter =
        //    GraphBarsAdapter(mutableListOf(), viewModel)

        viewModel.filterTransactions()
    }

    private fun getRecycleViewAdaptor(): TransactionsHeaderAdapter? {
        return if (rvTransaction.adapter is TransactionsHeaderAdapter) {
            (rvTransaction.adapter as TransactionsHeaderAdapter)
        } else {
            null
        }
    }

    private fun getGraphRecycleViewAdapter(): GraphBarsAdapter? {
        return if (rvTransactionsBarChart.adapter is GraphBarsAdapter) {
            (rvTransactionsBarChart.adapter as GraphBarsAdapter)
        } else {
            null
        }
    }

    private fun getBindings(): FragmentYapHomeBinding {
        return viewDataBinding as FragmentYapHomeBinding
    }

    private fun showErrorSnackBar(error: String) {
        CustomSnackbar.showErrorCustomSnackbar(
            context = requireContext(),
            layout = getBindings().clSnackbar,
            message = error
        )
    }

    private fun registerTransactionBroadcast() {
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_UPDATE_TRANSACTION))
    }

    private fun unregisterTransactionBroadcast() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(broadCastReceiver)
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                BROADCAST_UPDATE_TRANSACTION -> {
                    onRefresh()
                }
            }
        }
    }
}