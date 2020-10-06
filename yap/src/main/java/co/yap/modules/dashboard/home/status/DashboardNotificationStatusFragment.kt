package co.yap.modules.dashboard.home.status

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.app.YAPApplication
import co.yap.databinding.ActivityYapDashboardBinding
import co.yap.databinding.FragmentDashboardNotificationStatusesBinding
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.viewmodels.YapHomeViewModel
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.translation.Strings
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_dashboard_notification_statuses.*

class DashboardNotificationStatusFragment : YapDashboardChildFragment<IYapHome.ViewModel>(),
    IYapHome.View {

    private var parentViewModel: YapDashBoardViewModel? = null
    override var transactionViewHelper: TransactionsViewHelper? = null

    var dashboardNotificationStatusAdapter: TransactionStatusAdapter? = null
//    var dashboardNotificationStatusAdapter: DashboardNotificationStatusAdapter? = null

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_dashboard_notification_statuses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentViewModel =
            activity?.let { ViewModelProviders.of(it).get(YapDashBoardViewModel::class.java) }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        dashboardNotificationStatusAdapter =
            activity?.let { TransactionStatusAdapter(getStatusList()) }
//        dashboardNotificationStatusAdapter =
//            activity?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
        rvNotificationStatus.adapter = dashboardNotificationStatusAdapter

    }


    override fun setObservers() {
        MyUserManager.onAccountInfoSuccess.observe(this, Observer { isSuccess ->
            if (isSuccess) {

            }
        })

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

            }
        })
    }


    override fun onResume() {
        super.onResume()
        viewModel.state.filterCount.set(YAPApplication.homeTransactionsRequest.totalAppliedFilter)
        MyUserManager.updateCardBalance {}
    }


    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        MyUserManager.onAccountInfoSuccess.removeObservers(this)
        super.onDestroy()

    }

    private fun getBindings(): FragmentDashboardNotificationStatusesBinding {
        return viewDataBinding as FragmentDashboardNotificationStatusesBinding
    }


    private fun getStatusList(): MutableList<StatusDataModel> {
        val list = ArrayList<StatusDataModel>()
        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IS_COMPLETED
            )
        )
        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_card_delivered_title),
                getString(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_action),
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IS_PENDING
            )
        )
        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_card_top_up_title),
                getString(Strings.screen_time_line_display_text_status_card_top_up_description),
                getString(Strings.screen_time_line_display_text_status_card_top_up_action),
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IS_PENDING
            )
        )
        return list
    }

    fun getParentActivity(): ActivityYapDashboardBinding {
        return (activity as? YapDashboardActivity)?.viewDataBinding as ActivityYapDashboardBinding
    }
}