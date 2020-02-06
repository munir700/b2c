package co.yap.modules.dashboard.more.notification.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.notification.interfaces.INotificationDetail
import co.yap.modules.dashboard.more.notification.viewmodels.NotificationDetailViewModel

class NotificationDetailFragment : MoreBaseFragment<INotificationDetail.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_notification_detail


    override val viewModel: INotificationDetail.ViewModel
        get() = ViewModelProviders.of(this).get(NotificationDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.tvDeleteNotification -> {
                showToast("Under Development")
            }
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}