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
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.app.YAPApplication.Companion.homeTransactionsRequest
import co.yap.databinding.FragmentYapHomeBinding
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
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.transaction_filters.activities.TransactionFiltersActivity
import co.yap.yapcore.helpers.EndlessScrollListener
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.appbar.AppBarLayout
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.content_fragment_yap_home.*
import kotlinx.android.synthetic.main.fragment_yap_home.*


class YapHomeFragment : YapDashboardChildFragment<IYapHome.ViewModel>(), IYapHome.View,
    NotificationItemClickListener {

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
        initComponents()
        setObservers()
        setAvailableBalance(viewModel.state.availableBalance)
    }

    private fun initComponents() {
        rvTransaction.adapter =
            TransactionsHeaderAdapter(mutableListOf())
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
                    //findNavController().navigate(R.id.action_yapHome_to_setCardPinWelcomeActivity)
                }
                R.id.ivMenu -> parentView?.toggleDrawer()
                R.id.rlFilter -> {
                    startActivityForResult(
                        TransactionFiltersActivity.newIntent(requireContext()),
                        TransactionFiltersActivity.INTENT_FILTER_REQUEST
                    )
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
                getRecycleViewAdaptor()?.setList(it)
            } else {
                getRecycleViewAdaptor()?.setList(it)
            }
        })

        getRecycleViewAdaptor()?.setItemListener(listener)
        //getBindings().lyInclude.rvTransaction.addOnScrollListener(endlessScrollListener)
        getBindings().lyInclude.rvTransaction.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                val layoutManager =
                    getBindings().lyInclude.rvTransaction.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == layoutManager.itemCount - 1) {
                    if (!viewModel.isLoadMore.value!!) {
                        viewModel.isLoadMore.value = true
                        viewModel.homeTransactionsRequest.number = viewModel.homeTransactionsRequest.number +1
                        viewModel.loadMore()
                    }
                }
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
                this,
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
                "Now create a unique 4-digit PIN code to be able to use your debit card for purchases and withdrawals",
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
        if (null != homeTransactionsRequest) {
            showToast(YAPApplication.homeTransactionsRequest?.debitSearch.toString() + " debitSearch" + YAPApplication.homeTransactionsRequest?.minAmount.toString() + " ,min,max " + YAPApplication.homeTransactionsRequest?.minAmount.toString())
        }
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

        if (null != homeTransactionsRequest) {
            showToast(YAPApplication.homeTransactionsRequest?.debitSearch.toString() + " debitSearch" + YAPApplication.homeTransactionsRequest?.minAmount.toString() + " ,min,max " + YAPApplication.homeTransactionsRequest?.minAmount.toString())

        }
//        if (requestCode == TransactionFiltersActivity.INTENT_FILTER_REQUEST) {
//            showToast(
//                "data received " + data!!.getIntExtra(
//                    TransactionFiltersActivity.KEY_FILTER_START_AMOUNT,
//                    0
//                )
//            )
//        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
//            val action =
//                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((data as Store).id.toString())
//            view.findNavController().navigate(action)
        }
    }

    private fun getRecycleViewAdaptor(): TransactionsHeaderAdapter? {
        return if (rvTransaction.adapter is TransactionsHeaderAdapter) {
            (rvTransaction.adapter as TransactionsHeaderAdapter)
        } else {
            null
        }
    }

    private fun getBindings(): FragmentYapHomeBinding {
        return viewDataBinding as FragmentYapHomeBinding
    }
}