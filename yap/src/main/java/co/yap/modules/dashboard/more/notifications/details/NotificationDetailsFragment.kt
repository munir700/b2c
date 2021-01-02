package co.yap.modules.dashboard.more.notifications.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants

class NotificationDetailsFragment : BaseBindingFragment<INotificationDetails.ViewModel>(),
    INotificationDetails.View {
    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_notification_details

    override val viewModel: NotificationDetailsViewModel
        get() = ViewModelProviders.of(this).get(NotificationDetailsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.notification = arguments?.getParcelable(Constants.data)
    }

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> navigateBack()
        }
    }
}
