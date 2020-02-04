package co.yap.modules.dashboard.more.notification.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityNotificationBinding
import co.yap.modules.dashboard.more.notification.interfaces.INotifications
import co.yap.modules.dashboard.more.notification.viewmodels.NotificationsViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.interfaces.OnItemClickListener

class NotificationsActivity : BaseBindingActivity<INotifications.ViewModel>(),
    INotifications.View {

    override val viewModel: INotifications.ViewModel
        get() = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNotifications()
        viewModel.clickEvent.observe(this, Observer {
            if (it == R.id.tbBtnBack) {
                onBackPressed()
            }
        })
        getBinding().recyclerNotification.adapter = viewModel.adapter
        viewModel.adapter.allowFullItemClickListener = true
        viewModel.adapter.setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
        }
    }

    fun getBinding(): ActivityNotificationBinding {
        return viewDataBinding as ActivityNotificationBinding
    }
}
