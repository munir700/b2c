package co.yap.modules.dashboard.home.fragments

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.app.YAPApplication.Companion.homeTransactionsRequest
import co.yap.databinding.FragmentYapHomeBinding
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter
import co.yap.modules.dashboard.home.adaptor.NotificationAdapter
import co.yap.modules.dashboard.home.adaptor.TransactionsHeaderAdapter
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
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.transaction_filters.activities.TransactionFiltersActivity
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.fixSwipeToRefresh
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.appbar.AppBarLayout
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.content_fragment_yap_home.*
import kotlinx.android.synthetic.main.fragment_yap_home.*
import kotlinx.android.synthetic.main.view_graph.*


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
    private lateinit var swipeToRefresh: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        setObservers()
        setAvailableBalance(viewModel.state.availableBalance)
    }

    private fun initComponents() {
//        setUpGraph()
        rvTransaction.layoutManager = LinearLayoutManager(context)
        rvTransaction.adapter =
            TransactionsHeaderAdapter(mutableListOf(), adaptorlistener)
        getRecycleViewAdaptor()?.allowFullItemClickListener = true

        // swipeToRefresh = view?.findViewById(R.id.refreshLayout) as SwipeRefreshLayout
        getBindings().refreshLayout.setOnRefreshListener(this)
        rvTransactionsBarChart.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            true
        )
        rvTransactionsBarChart.adapter = GraphBarsAdapter(mutableListOf(), viewModel)

        getBindings().lyInclude.rvTransaction.apply {
            fixSwipeToRefresh(getBindings().refreshLayout)
        }
    }


    override fun onRefresh() {
        viewModel.isRefreshing.value = true
        homeTransactionsRequest.number = 0
        viewModel.requestAccountTransactions()
        getBindings().refreshLayout.isRefreshing = false
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
                R.id.ivMenu -> parentView?.toggleDrawer()
                R.id.rlFilter -> {
                    if (null != viewModel.transactionsLiveData.value && viewModel.transactionsLiveData.value!!.isEmpty() && homeTransactionsRequest.totalAppliedFilter == 0) {
                        showErrorSnackBar("No Transactions Found")
                        return@Observer
                    } else {
                        startActivityForResult(
                            TransactionFiltersActivity.newIntent(requireContext()),
                            TransactionFiltersActivity.INTENT_FILTER_REQUEST
                        )
                    }
                }
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
                if (getRecycleViewAdaptor()?.itemCount!! > 0)
                    getRecycleViewAdaptor()?.removeItemAt(getRecycleViewAdaptor()?.itemCount!! - 1)
                getRecycleViewAdaptor()?.setList(it)
                getGraphRecycleViewAdapter()?.setList(it)
            } else {
                if (it.isEmpty()) {
                    ivNoTransaction.visibility = View.VISIBLE
                    rvTransaction.visibility = View.GONE
                } else {
                    getRecycleViewAdaptor()?.setList(it)
                    getGraphRecycleViewAdapter()?.setList(it)
                }
            }
        })

//        getGraphRecycleViewAdapter()?.setItemListener(listener)
        getRecycleViewAdaptor()?.setItemListener(listener)
        getRecycleViewAdaptor()?.allowFullItemClickListener = true
        //getBindings().lyInclude.rvTransaction.addOnScrollListener(endlessScrollListener)
        getBindings().lyInclude.rvTransaction.addOnScrollListener(object :
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

        viewModel.isLoadMore.observe(this, Observer {
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
                //     if (getRecycleViewAdaptor()?.itemCount!! > 0)
                //     getRecycleViewAdaptor()?.removeItemAt(getRecycleViewAdaptor()?.itemCount!! - 1)
            }

        })
    }

    private fun checkUserStatus() {
        //MyUserManager.user?.notificationStatuses = Constants.USER_STATUS_ON_BOARDED
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
            Constants.USER_STATUS_CARD_ACTIVATED -> {
                showTransactionsAndGraph()
                notificationsList.clear()
                mAdapter = NotificationAdapter(
                    notificationsList,
                    requireContext(),
                    this
                )
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showTransactionsAndGraph() {
        ivNoTransaction.visibility = View.GONE
        rvTransaction.visibility = View.VISIBLE
        vGraph.visibility = View.VISIBLE
        view?.let {
            transactionViewHelper = TransactionsViewHelper(
                requireContext(),
                it,
                viewModel
            )
        }
    }

    private fun addSetPinNotification() {
        notificationsList.add(
            Notification(
                "Set your card PIN",
                "Now create a unique 4-digit PIN isoCountryCode2Digit to be able to use your debit card for purchases and withdrawals",
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
        if (Constants.USER_STATUS_CARD_ACTIVATED == MyUserManager.user?.notificationStatuses) {
            checkUserStatus()
        }
        viewModel.state.filterCount.set(homeTransactionsRequest.totalAppliedFilter)
        MyUserManager.updateCardBalance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appbar.removeOnOffsetChangedListener(appbarListener)
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
                tvAvailableBalance.text = ss1
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(notification: Notification) {
        when (notification.action) {
            Constants.NOTIFICATION_ACTION_SET_PIN -> viewModel.getDebitCards()
            Constants.NOTIFICATION_ACTION_COMPLETE_VERIFICATION -> {
                startActivity(
                    DocumentsDashboardActivity.getIntent(
                        requireContext(),
                        parentViewModel.state.firstName,
                        false
                    )
                )
                activity?.finish()
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
        appbar.addOnOffsetChangedListener(appbarListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TransactionFiltersActivity.INTENT_FILTER_REQUEST) {
            if (YAPApplication.hasFilterStateChanged)
                getFilterTransactions()
        }
    }

    private fun getFilterTransactions() {
        rvTransaction.adapter =
            TransactionsHeaderAdapter(mutableListOf(), listener)


        rvTransactionsBarChart.adapter =
            GraphBarsAdapter(mutableListOf(), viewModel)

        viewModel.filterTransactions()
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            (data as HomeTransactionListData).content.get(0).transactionId
        }
    }

    private fun getRecycleViewAdaptor(): TransactionsHeaderAdapter? {
        return if (rvTransaction.adapter is TransactionsHeaderAdapter) {
            (rvTransaction.adapter as TransactionsHeaderAdapter)
        } else {
            null
        }
    }

    fun setUpGraph() {
//        if (!viewModel.transactionLogicHelper.transactionList.isNullOrEmpty()) {
//            rvTransactionsBarChart.adapter =
//                GraphBarsAdapter(
//                    viewModel.transactionLogicHelper.transactionList,
//                    /*activity!!.applicationContext,*/
//                    viewModel.MAX_CLOSING_BALANCE
//                )

//            rvTransactionsBarChart.layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                true
//            )

//        }
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
            layout = clSnackbar,
            message = error
        )
    }
}