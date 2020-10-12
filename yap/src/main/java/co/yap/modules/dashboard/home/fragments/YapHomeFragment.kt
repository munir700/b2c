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
import co.yap.databinding.ActivityYapDashboardBinding
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
import co.yap.modules.dashboard.home.models.HomeNotification
import co.yap.modules.dashboard.home.status.DashboardNotificationStatusAdapter
import co.yap.modules.dashboard.home.status.NotificationProgressStatus
import co.yap.modules.dashboard.home.status.StatusDataModel
import co.yap.modules.dashboard.home.viewmodels.YapHomeViewModel
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.modules.dashboard.more.yapforyou.activities.YAPForYouActivity
import co.yap.modules.dashboard.transaction.activities.TransactionDetailsActivity
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.ADDRESS_SUCCESS
import co.yap.yapcore.constants.Constants.BROADCAST_UPDATE_TRANSACTION
import co.yap.yapcore.constants.Constants.MODE_MEETING_CONFORMATION
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.enums.NotificationAction
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.appbar.AppBarLayout
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.content_fragment_yap_home.*
import kotlinx.android.synthetic.main.view_graph.*
import kotlin.math.abs


class YapHomeFragment : YapDashboardChildFragment<IYapHome.ViewModel>(), IYapHome.View,
    NotificationItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private var mAdapter: NotificationAdapter? = null
    private var parentViewModel: YapDashBoardViewModel? = null
    override var transactionViewHelper: TransactionsViewHelper? = null
    var dashboardNotificationStatusAdapter: DashboardNotificationStatusAdapter? = null

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewModel =
            activity?.let { ViewModelProviders.of(it).get(YapDashBoardViewModel::class.java) }
    }

    private fun startFlowForSetPin() {
        if (MyUserManager.getPrimaryCard() != null) {
            if (isShowSetPin(MyUserManager.getPrimaryCard())) {
                if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
                    viewModel.clickEvent.setValue(viewModel.EVENT_SET_CARD_PIN)
                }
            }
        } else toast("Invalid card found")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerTransactionBroadcast()
        initComponents()
        setObservers()
        setClickOnWelcomeYapItem()
        setAvailableBalance(viewModel.state.availableBalance)

        setUpDashBoardNotificationsView()
    }

    private fun setClickOnWelcomeYapItem() {
        getBindings().lyInclude.multiStateView.getView(MultiStateView.ViewState.EMPTY)
            ?.setOnClickListener { openYapForYou() }
    }

    private fun openYapForYou() {
        startActivity(Intent(requireContext(), YAPForYouActivity::class.java))
    }

    private fun initComponents() {
        getBindings().lyInclude.rvTransaction.layoutManager = LinearLayoutManager(context)
        getBindings().lyInclude.rvTransaction.adapter =
            TransactionsHeaderAdapter(mutableListOf(), transactionClickListener)
        getRecycleViewAdaptor()?.allowFullItemClickListener = true

        getBindings().refreshLayout.setOnRefreshListener(this)
        rvTransactionsBarChart.adapter = GraphBarsAdapter(mutableListOf(), viewModel)

        getBindings().lyInclude.rvTransaction.apply {
            fixSwipeToRefresh(getBindings().refreshLayout)
        }

        getBindings().appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val pram = getBindings().lyInclude.lyHomeAction.layoutParams
            if (abs(verticalOffset) <= 5) {
                getBindings().lyInclude.lyHomeAction.alpha = 1f
                pram.height = appBarLayout.totalScrollRange
                getBindings().lyInclude.lyHomeAction.layoutParams = pram
            } else {
                if (Math.abs(verticalOffset) > 0)
                    getBindings().lyInclude.lyHomeAction.alpha = 10 / abs(verticalOffset).toFloat()
                pram.height = appBarLayout?.totalScrollRange?.plus(verticalOffset)!!
                getBindings().lyInclude.lyHomeAction.layoutParams = pram
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

    private val transactionClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.clickEvent.setPayload(
                SingleClickEvent.AdaptorPayLoadHolder(
                    view,
                    data,
                    pos
                )
            )
            viewModel.clickEvent.setValue(view.id)
        }
    }

    private fun openTransactionFilters() {
        if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus)
            startActivityForResult(
                TransactionFiltersActivity.newIntent(
                    requireContext(),
                    viewModel.txnFilters
                ),
                RequestCodes.REQUEST_TXN_FILTER
            )
    }

    override fun setObservers() {
        MyUserManager.onAccountInfoSuccess.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                checkUserStatus()
            }
        })
        getBindings().ivSearch.setOnLongClickListener {
            return@setOnLongClickListener activity?.let {
                //val tour = TourSetup(it, setViewsArray())
                //tour.startTour()
                //showToast("YAP Signature Info${YAPApplication.configManager?.toString()}" + "^" + AlertType.DIALOG)
                true
            } ?: false
        }

        listenForToolbarExpansion()
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.lyTransaction -> {
                    viewModel.clickEvent.getPayload()?.let {
                        if (it.itemData is Transaction) {
                            launchActivity<TransactionDetailsActivity> {
                                putExtra("transaction", it.itemData as Transaction)
                            }
                        }
                    }
                    viewModel.clickEvent.setPayload(null)
                }
                viewModel.EVENT_SET_CARD_PIN -> {
                    startActivityForResult(
                        SetCardPinWelcomeActivity.newIntent(
                            requireContext(),
                            MyUserManager.getPrimaryCard()
                        ), RequestCodes.REQUEST_FOR_SET_PIN
                    )
                }
                viewModel.ON_ADD_NEW_ADDRESS_EVENT -> {
                    startActivityForResult(
                        FragmentPresenterActivity.getIntent(
                            requireContext(),
                            MODE_MEETING_CONFORMATION,
                            null
                        ), RequestCodes.REQUEST_MEETING_CONFIRMED
                    )
                }
                R.id.ivMenu -> parentView?.toggleDrawer()
                R.id.rlFilter -> {
                    if (viewModel.state.isTransEmpty.get() == false) {
                        openTransactionFilters()
                    } else {
                        if (homeTransactionsRequest.totalAppliedFilter > 0) {
                            openTransactionFilters()
                        } else {
                            return@Observer
                        }
                    }
                }
                R.id.lyAnalytics -> launchActivity<CardAnalyticsActivity>()//startFragment(CardAnalyticsDetailsFragment::class.java.name)
                R.id.lyAdd -> {
                    if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
                        openTopUpScreen()
                    } else {
                        showToast("Account activation pending")

                    }
                }

            }
        })

        MyUserManager.card.observe(this, Observer { primaryCard ->
            primaryCard?.let {
                startFlowForSetPin()
                checkUserStatus()
            }
        })

        MyUserManager.cardBalance.observe(this, Observer { value ->
            setAvailableBalance(value.availableBalance.toString())
        })

        viewModel.transactionsLiveData.observe(this, Observer {
            if (true == viewModel.isLoadMore.value) {
                if (getRecycleViewAdaptor()?.itemCount!! == 0) getBindings().appbar.setExpanded(true)

                if (getRecycleViewAdaptor()?.itemCount!! > 0)
                    getRecycleViewAdaptor()?.removeItemAt(getRecycleViewAdaptor()?.itemCount!! - 1)

                val listToAppend: MutableList<HomeTransactionListData> = mutableListOf()
                val oldData = getGraphRecycleViewAdapter()?.getDataList()
                for (parentItem in it) {

                    var shouldAppend = false
                    for (i in 0 until oldData?.size!!) {
                        if (parentItem.date == oldData[i].date) {
                            if (parentItem.transaction.size != oldData[i].transaction.size) {
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
                    //if transaction is empty and filer is applied then state would be Error where no transaction image show
                    if (homeTransactionsRequest.totalAppliedFilter > 0) {
                        getBindings().lyInclude.multiStateView.viewState =
                            MultiStateView.ViewState.ERROR
                    } else {
                        //if transaction is empty and filer is not applied then state would be Empty where a single row appears welcome to yap
                        getBindings().lyInclude.multiStateView.viewState =
                            MultiStateView.ViewState.EMPTY
                    }
                    transactionViewHelper?.setTooltipVisibility(View.GONE)
                    viewModel.state.isTransEmpty.set(true)
                } else {
                    if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
                        showTransactionsAndGraph()
                    } else {
                        viewModel.state.isTransEmpty.set(true)
                    }
                    getRecycleViewAdaptor()?.setList(it)
                    getGraphRecycleViewAdapter()?.setList(it)
                    transactionViewHelper?.setTooltipOnZero()
                }
            }
        })

//        getGraphRecycleViewAdapter()?.setItemListener(listener)
        getRecycleViewAdaptor()?.setItemListener(transactionClickListener)
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
                homeTransactionsRequest.number =
                    homeTransactionsRequest.number + 1
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
        setNotificationAdapter(MyUserManager.user, MyUserManager.card.value)
    }

    private fun setNotificationAdapter(accountInfo: AccountInfo?, paymentCard: Card?) {
        accountInfo?.let { account ->
            paymentCard?.let { card ->
                mAdapter = NotificationAdapter(
                    requireContext(),
                    viewModel.getNotifications(account, card),
                    this
                )
                getBindings().lyInclude.rvNotificationList.setSlideOnFling(false)
                getBindings().lyInclude.rvNotificationList.setOverScrollEnabled(true)
                getBindings().lyInclude.rvNotificationList.adapter = mAdapter
                getBindings().lyInclude.rvNotificationList.smoothScrollToPosition(0)
                getBindings().lyInclude.rvNotificationList.setItemTransitionTimeMillis(150)
                getBindings().lyInclude.rvNotificationList.setItemTransformer(
                    ScaleTransformer.Builder()
                        .setMaxScale(1.05f)
                        .setMinScale(1f)
                        .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                        //.setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                        .build()
                )
            }
        }
    }

    private fun clearNotification() {
        mAdapter?.removeAllItems()
    }

    override fun onCloseClick(notification: HomeNotification, position: Int) {
        super.onCloseClick(notification, position)
        clearNotification()
    }

    private fun isShowSetPin(paymentCard: Card?): Boolean {
        return (paymentCard?.deliveryStatus == CardDeliveryStatus.SHIPPED.name && !paymentCard.pinCreated)
    }

    private fun showTransactionsAndGraph() {
        if (viewModel.transactionsLiveData.value.isNullOrEmpty()) {
            if (0 >= viewModel.state.filterCount.get() ?: 0) {
                viewModel.state.isTransEmpty.set(true)
            }
        } else {
            getBindings().lyInclude.multiStateView.viewState = MultiStateView.ViewState.CONTENT
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

    override fun onResume() {
        super.onResume()
        viewModel.state.filterCount.set(homeTransactionsRequest.totalAppliedFilter)
        MyUserManager.updateCardBalance {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterTransactionBroadcast()
        getBindings().appbar.removeOnOffsetChangedListener(appbarListener)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        MyUserManager.onAccountInfoSuccess.removeObservers(this)
        super.onDestroy()

    }

    private fun setAvailableBalance(balance: String) {
        try {
            val ss1 = SpannableString(balance.toFormattedCurrency())
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

    override fun onClick(notification: HomeNotification, position: Int) {
        if (position != getBindings().lyInclude.rvNotificationList.currentItem) {
            getBindings().lyInclude.rvNotificationList.smoothScrollToPosition(position)
            return
        }

        when (notification.action) {
            NotificationAction.COMPLETE_VERIFICATION -> {
                launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                    putExtra(
                        Constants.name,
                        MyUserManager.user?.currentCustomer?.firstName.toString()
                    )
                    putExtra(Constants.data, false)
                }
            }

            NotificationAction.SET_PIN -> {
                MyUserManager.card.value?.let {
                    viewModel.clickEvent.setValue(viewModel.EVENT_SET_CARD_PIN)
                }
            }

            NotificationAction.UPDATE_EMIRATES_ID -> {
                if (MyUserManager.user?.otpBlocked == true) {
                    if (MyUserManager.eidStatus == EIDStatus.NOT_SET &&
                        PartnerBankStatus.ACTIVATED.status != MyUserManager.user?.partnerBankStatus
                    ) {
                        launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                            putExtra(
                                Constants.name,
                                MyUserManager.user?.currentCustomer?.firstName.toString()
                            )
                            putExtra(Constants.data, true)
                            putExtra(
                                "document",
                                GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation(
                                    identityNo = MyUserManager.user?.currentCustomer?.identityNo
                                )
                            )
                        }
                    } else {
                        showToast(Utils.getOtpBlockedMessage(requireContext()))
                    }
                } else {
                    launchActivity<DocumentsDashboardActivity>(requestCode = RequestCodes.REQUEST_KYC_DOCUMENTS) {
                        putExtra(
                            Constants.name,
                            MyUserManager.user?.currentCustomer?.firstName.toString()
                        )
                        putExtra(Constants.data, true)
                        putExtra(
                            "document",
                            GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation(
                                identityNo = MyUserManager.user?.currentCustomer?.identityNo
                            )
                        )
                    }
                }
            }
            NotificationAction.HELP_AND_SUPPORT -> {
                startActivity(
                    FragmentPresenterActivity.getIntent(
                        requireContext(),
                        Constants.MODE_HELP_SUPPORT, null
                    )
                )
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
                    val result =
                        data.getBooleanExtra(Constants.result, false)
                    if (result) {
                        startActivityForResult(
                            LocationSelectionActivity.newIntent(
                                context = requireContext(),
                                address = MyUserManager.userAddress ?: Address(),
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
                    val result = it.getBooleanExtra(ADDRESS_SUCCESS, false)
                    if (result) {
                        viewModel.clickEvent.setValue(viewModel.ON_ADD_NEW_ADDRESS_EVENT)
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
            RequestCodes.REQUEST_MEETING_CONFIRMED -> {
                MyUserManager.getAccountInfo()
            }
            RequestCodes.REQUEST_FOR_SET_PIN -> {
                data?.let {
                    val isPinSet =
                        it.getBooleanExtra(Constants.isPinCreated, false)
                    val isSkip =
                        it.getBooleanExtra(Constants.IS_TOPUP_SKIP, false)
                    getGraphRecycleViewAdapter()?.notifyDataSetChanged()
                    if (isPinSet && isSkip) {
                        viewModel.getDebitCards()
                    } else {
                        viewModel.getDebitCards()
                        openTopUpScreen()
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
            homeTransactionsRequest.txnType = it.getTxnType()
            homeTransactionsRequest.amountStartRange = it.amountStartRange
            homeTransactionsRequest.amountEndRange = it.amountEndRange
            homeTransactionsRequest.title = null
            homeTransactionsRequest.totalAppliedFilter = it.totalAppliedFilter
        }
    }

    private fun getFilterTransactions() {
        transactionViewHelper?.setTooltipVisibility(View.GONE)
        viewModel.filterTransactions()
    }

    private fun getRecycleViewAdaptor(): TransactionsHeaderAdapter? {
        return if (getBindings().lyInclude.rvTransaction.adapter is TransactionsHeaderAdapter) {
            (getBindings().lyInclude.rvTransaction.adapter as TransactionsHeaderAdapter)
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

    private fun openTopUpScreen() {
        startActivity(TopUpLandingActivity.getIntent(requireContext()))
    }

    private fun setViewsArray(): ArrayList<GuidedTourViewDetail> {
        val list = ArrayList<GuidedTourViewDetail>()
        list.add(
            GuidedTourViewDetail(
                getParentActivity().cvYapIt,
                "Your current balance",
                "Here you can see your account’s current balance. It will be updated in-real time after every transaction.",
                padding = 220f,
                circleRadius = 300f
            )
        )
        list.add(
            GuidedTourViewDetail(
                getBindings().ivSearch,
                "Menu Type",
                "Here you can see your account’s current balance. It will be updated in-real time after every transaction.",
                padding = 170f,
                circleRadius = 220f
            )
        )

        list.add(
            GuidedTourViewDetail(
                getBindings().tvAvailableBalance,
                "Yap it",
                "Here you can see your account’s current balance. It will be updated in-real time after every transaction.",
                padding = 260f,
                circleRadius = 260f
            )
        )
        list.add(
            GuidedTourViewDetail(
                getBindings().lyInclude.rlFilter,
                "Yap it",
                "Here you can see your account’s current balance. It will be updated in-real time after every transaction.",
                padding = 150f,
                circleRadius = 160f
            )
        )
        return list
    }

    fun getParentActivity(): ActivityYapDashboardBinding {
        return (activity as? YapDashboardActivity)?.viewDataBinding as ActivityYapDashboardBinding
    }


    fun setUpDashBoardNotificationsView() {
        setUpStatusAdapter()
    }

    private fun setUpStatusAdapter() {
        if (MyUserManager.getPrimaryCard()?.status == CardDeliveryStatus.SHIPPING.name) {

            viewModel.state.isTransEmpty.set(false)
            rvTransaction.visibility = View.GONE
            vGraph.visibility = View.GONE
            rvNotificationStatus.visibility = View.VISIBLE
            dashboardNotificationStatusAdapter =
                context?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
            dashboardNotificationStatusAdapter?.allowFullItemClickListener = false
            dashboardNotificationStatusAdapter?.onItemClickListener = listener
            rvNotificationStatus.adapter = dashboardNotificationStatusAdapter
        } else {
            rvNotificationStatus.visibility = View.GONE
            rvTransaction.visibility = View.VISIBLE
            vGraph.visibility = View.VISIBLE

        }
    }


    private fun getStatusList(): MutableList<StatusDataModel> {
        val list = ArrayList<StatusDataModel>()
        list.add(
            StatusDataModel(
                0,
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                resources.getDrawable(R.drawable.ic_dashboard_delivery),
                NotificationProgressStatus.IS_COMPLETED,
                CardDeliveryStatus.SHIPPING
            )
        )
        list.add(
            StatusDataModel(
                1,
                getString(Strings.screen_time_line_display_text_status_card_delivered_title),
                getString(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                resources.getDrawable(R.drawable.ic_dashboard_active),
//                resources.getDrawable(R.drawable.card_spare),
                NotificationProgressStatus.IN_PROGRESS,
                CardDeliveryStatus.SHIPPED
            )
        )

        list.add(
            StatusDataModel(
                2,
                getString(Strings.screen_time_line_display_text_status_additional_requirements_title),
                getString(Strings.screen_time_line_display_text_status_additional_requirements_description),
                getString(Strings.screen_time_line_display_text_status_additional_requirements_action),
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                3,
                getString(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_action),
                resources.getDrawable(R.drawable.ic_dashboard_set_pin),
                NotificationProgressStatus.IS_PENDING,
                CardDeliveryStatus.SHIPPED
            )
        )
        list.add(
            StatusDataModel(
                4,
                getString(Strings.screen_time_line_display_text_status_card_top_up_title),
                getString(Strings.screen_time_line_display_text_status_card_top_up_description),
                getString(Strings.screen_time_line_display_text_status_card_top_up_action),
                resources.getDrawable(R.drawable.ic_dashboard_topup),
                NotificationProgressStatus.IS_PENDING
            )
        )
        return list
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

            var statusDataModel: StatusDataModel = data as StatusDataModel

            when (statusDataModel.position) {
                0 -> {
                    startActivityForResult(
                        FragmentPresenterActivity.getIntent(
                            requireContext(),
                            Constants.MODE_MEETING_CONFORMATION,
                            null
                        ), RequestCodes.REQUEST_MEETING_CONFIRMED
                    )
                }
                1 -> {
                    context.shortToast("1")
                }
                2 -> {
                    //open email
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    startActivity(Intent.createChooser(intent, "Choose"))
                }
                3 -> {
                    startActivityForResult(
                        SetCardPinWelcomeActivity.newIntent(
                            requireContext(),
                            MyUserManager.getPrimaryCard()
                        ), RequestCodes.REQUEST_FOR_SET_PIN
                    )

                }
                4 -> {
                    context?.startActivity(activity?.let { TopUpLandingActivity.getIntent(it) })
                }
            }
        }
    }

}