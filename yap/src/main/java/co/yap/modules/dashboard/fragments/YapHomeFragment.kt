package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.NotificationAdapter
import co.yap.modules.dashboard.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.interfaces.NotificationItemClickListener
import co.yap.modules.dashboard.models.Notification
import co.yap.modules.dashboard.viewmodels.YapDashBoardViewModel
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import co.yap.modules.onboarding.constants.Constants
import co.yap.yapcore.managers.MyUserManager
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.content_fragment_yap_home.*


class YapHomeFragment : YapDashboardChildFragment<IYapHome.ViewModel>(), IYapHome.View,
    DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>,
    DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>,
    NotificationItemClickListener {

    private lateinit var mAdapter: NotificationAdapter
    private lateinit var parentViewModel: YapDashBoardViewModel
    private var notificationsList: ArrayList<Notification> = ArrayList()
    override lateinit var transactionViewHelper: TransactionsViewHelper

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewHelper = TransactionsViewHelper(
            requireContext(),
            view,
            viewModel
        )
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                viewModel.EVENT_SET_CARD_PIN -> {
                    findNavController().navigate(R.id.action_yapHome_to_setCardPinWelcomeActivity)
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
    }


    private fun checkUserStatus() {
        //MyUserManager.user?.notificationStatuses = Constants.USER_STATUS_ON_BOARDED
        when (MyUserManager.user?.notificationStatuses) {
            Constants.USER_STATUS_ON_BOARDED -> {
                addCompleteVerificationNotification()
            }
            Constants.USER_STATUS_MEETING_SUCCESS -> {
                addSetPinNotification()
            }
            Constants.USER_STATUS_MEETING_SCHEDULED -> {
                notificationsList.clear()
                mAdapter = NotificationAdapter(notificationsList, requireContext(), this)
                mAdapter.notifyDataSetChanged()
            }
            Constants.USER_STATUS_CARD_ACTIVATED -> {
                notificationsList.clear()
                mAdapter = NotificationAdapter(notificationsList, requireContext(), this)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun addSetPinNotification() {
        notificationsList.add(
            Notification(
                "Set your card pin",
                "Now create a unique 4-digit PIN code to be able to use your debit card for purchases and withdrawals",
                "",
                Constants.NOTIFICATION_ACTION_SET_PIN,
                "",
                ""
            )
        )
        mAdapter = NotificationAdapter(notificationsList, requireContext(), this)
        rvNotificationList.setSlideOnFling(false)
        rvNotificationList.setOverScrollEnabled(true)
        rvNotificationList.adapter = mAdapter
        rvNotificationList.addOnItemChangedListener(this)
        rvNotificationList.addScrollStateChangeListener(this)
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
        mAdapter = NotificationAdapter(notificationsList, requireContext(), this)
        rvNotificationList.setSlideOnFling(false)
        rvNotificationList.setOverScrollEnabled(true)
        rvNotificationList.adapter = mAdapter
        rvNotificationList.addOnItemChangedListener(this)
        rvNotificationList.addScrollStateChangeListener(this)
        rvNotificationList.smoothScrollToPosition(0)
        rvNotificationList.setItemTransitionTimeMillis(100)
        rvNotificationList.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )

    }


    override fun onCurrentItemChanged(p0: RecyclerView.ViewHolder?, p1: Int) {
    }

    override fun onScrollEnd(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun onScrollStart(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun onScroll(
        p0: Float,
        p1: Int,
        p2: Int,
        p3: RecyclerView.ViewHolder?,
        p4: RecyclerView.ViewHolder?
    ) {
    }

    override fun onResume() {
        super.onResume()
        if (Constants.USER_STATUS_CARD_ACTIVATED == MyUserManager.user?.notificationStatuses) {
            notificationsList.clear()
            mAdapter = NotificationAdapter(notificationsList, requireContext(), this)
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(notification: Notification) {
        when (notification.action) {
            Constants.NOTIFICATION_ACTION_SET_PIN -> viewModel.getDebitCards()
            Constants.NOTIFICATION_ACTION_COMPLETE_VERIFICATION -> {
                val action =
                    YapHomeFragmentDirections.actionYapHomeToDocumentsDashboardActivity(
                        parentViewModel.state.firstName
                    )
                findNavController().navigate(action)
            }

        }
    }

}