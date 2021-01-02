package co.yap.modules.dashboard.more.notifications.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.networking.notification.HomeNotification
import co.yap.translation.Strings.screen_notification_listing_display_text_delete_alert_title
import co.yap.translation.Strings.screen_notification_listing_display_text_delete_message
import co.yap.widgets.DividerItemDecoration
import co.yap.widgets.advrecyclerview.swipeable.RecyclerViewSwipeManager
import co.yap.widgets.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager
import co.yap.widgets.advrecyclerview.utils.WrapperAdapterUtils
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.NotificationHelper.getNotificationTestData
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.fragment_notifications_home_v2.*

class NotificationsHomeFragment : BaseBindingFragment<INotificationsHome.ViewModel>(),
    INotificationsHome.View, OnItemClickListener {
    private var mRecyclerViewSwipeManager: RecyclerViewSwipeManager? = null
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private lateinit var mNotificationsAdapter: NotificationsHomeAdapter
    private var mRecyclerViewTouchActionGuardManager: RecyclerViewTouchActionGuardManager? = null
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_notifications_home_v2

    override val viewModel: NotificationsHomeViewModel
        get() = ViewModelProviders.of(this).get(NotificationsHomeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> requireActivity().finish()
            R.id.ivRightIcon -> findNavController().navigate(R.id.action_notificationHomeFragment_to_notificationSettingsFragment)
        }
    }

    private fun initRecyclerView() {
// touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
        mRecyclerViewTouchActionGuardManager = RecyclerViewTouchActionGuardManager().apply {
            setInterceptVerticalScrollingWhileAnimationRunning(true)
            isEnabled = true

        }
        mRecyclerViewSwipeManager = RecyclerViewSwipeManager().apply {
            mNotificationsAdapter = NotificationsHomeAdapter(
                getNotificationTestData(
                    SessionManager.user,
                    SessionManager.card.value,
                    requireContext()
                ), null
            )
            viewModel.mNotificationsHomeAdapter?.set(mNotificationsAdapter)
            mWrappedAdapter = createWrappedAdapter(mNotificationsAdapter)
        }
        recyclerView?.apply {
            adapter = mWrappedAdapter // requires *wrapped* adapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    R.drawable.line_divider,
                    false,
                    false, dimen(R.dimen._72sdp)
                )
            )
            mRecyclerViewTouchActionGuardManager?.attachRecyclerView(this)
            mRecyclerViewSwipeManager?.attachRecyclerView(this)
            mNotificationsAdapter.onChildViewClickListener =
                { view: View, position: Int, data: HomeNotification? ->
                    confirm(
                        message = getString(screen_notification_listing_display_text_delete_message),
                        title = getString(
                            screen_notification_listing_display_text_delete_alert_title
                        )
                    ) {
                        mNotificationsAdapter.removeAt(position)
                    }
                }
            mNotificationsAdapter.onItemClickListener = this@NotificationsHomeFragment
        }
    }

    override fun onItemClick(view: View, data: Any, pos: Int) {
        val item = data as HomeNotification
        findNavController().navigate(
            R.id.action_notificationHomeFragment_to_notificationDetailFragment,
            bundleOf(Constants.data to item)
        )
    }

    override fun onDestroyView() {
        mRecyclerViewSwipeManager?.let {
            it.release()
            mRecyclerViewSwipeManager = null
        }
        mRecyclerViewTouchActionGuardManager?.let {
            it.release()
            mRecyclerViewTouchActionGuardManager = null
        }
        mWrappedAdapter?.let {
            WrapperAdapterUtils.releaseAll(it)
            mWrappedAdapter = null
        }
        super.onDestroyView()
    }
}
