package co.yap.modules.dashboard.more.notification.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentNotificationsHomeBinding
import co.yap.modules.dashboard.more.notification.interfaces.INotificationHome
import co.yap.modules.dashboard.more.notification.viewmodels.NotificationsHomeViewModel
import co.yap.modules.yapnotification.models.Notification
import co.yap.yapcore.interfaces.OnItemClickListener
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener

class NotificationHomeFragment : NotificationsBaseFragment<INotificationHome.ViewModel>() {
    private lateinit var onTouchListener: RecyclerTouchListener

    override val viewModel: NotificationsHomeViewModel
        get() = ViewModelProviders.of(this).get(NotificationsHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_notifications_home


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNotifications()
        viewModel.clickEvent.observe(this, Observer {
            if (it == R.id.tbBtnBack) {
                onBackPressed()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().recyclerNotification.adapter = viewModel.adapter
        viewModel.adapter.allowFullItemClickListener = true
        viewModel.adapter.setItemListener(listener)
        initSwipeListener()
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.parentViewModel?.notification = data as? Notification
        }
    }

    private fun initSwipeListener() {
        onTouchListener =
            RecyclerTouchListener(requireActivity(), getBinding().recyclerNotification)
                .setClickable(
                    object : RecyclerTouchListener.OnRowClickListener {
                        override fun onRowClicked(position: Int) {}
                        override fun onIndependentViewClicked(
                            independentViewID: Int,
                            position: Int
                        ) {
                        }
                    }).setSwipeOptionViews(R.id.btnDelete)
                .setSwipeable(
                    R.id.foregroundContainer, R.id.swipe
                )
                { viewID, position ->
                    when (viewID) {
                        R.id.btnDelete -> {
                            showToast("Under Development")
                        }
                    }
                }
    }

    override fun onResume() {
        super.onResume()
        getBinding().recyclerNotification.addOnItemTouchListener(onTouchListener)

    }

    override fun onPause() {
        super.onPause()
        getBinding().recyclerNotification.removeOnItemTouchListener(onTouchListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    fun getBinding(): FragmentNotificationsHomeBinding {
        return viewDataBinding as FragmentNotificationsHomeBinding
    }
}