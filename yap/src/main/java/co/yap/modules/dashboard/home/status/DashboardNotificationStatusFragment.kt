package co.yap.modules.dashboard.home.status

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.viewmodels.YapHomeViewModel
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.translation.Strings
import kotlinx.android.synthetic.main.fragment_dashboard_notification_statuses.*

class DashboardNotificationStatusFragment : YapDashboardChildFragment<IYapHome.ViewModel>(),
    IYapHome.View {

    override var transactionViewHelper: TransactionsViewHelper? = null

    var dashboardNotificationStatusAdapter: DashboardNotificationStatusAdapter? = null

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_dashboard_notification_statuses

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setUpAdapter()

    }

    private fun setUpAdapter() {
        dashboardNotificationStatusAdapter =
            activity?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
        rvNotificationStatus.adapter = dashboardNotificationStatusAdapter
    }


    override fun setObservers() {


        viewModel.clickEvent.observe(this, Observer {
            when (it) {

            }
        })
    }

    private fun getStatusList(): MutableList<StatusDataModel> {
        val list = ArrayList<StatusDataModel>()
        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                resources.getDrawable(R.drawable.ic_dashboard_delivery),
                NotificationProgressStatus.IS_COMPLETED
            )
        )
        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_card_delivered_title),
                getString(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                resources.getDrawable(R.drawable.card_spare),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_additional_requirements_title),
                getString(Strings.screen_time_line_display_text_status_additional_requirements_description),
                getString(Strings.screen_time_line_display_text_status_additional_requirements_action),
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_action),
                resources.getDrawable(R.drawable.ic_dashboard_set_pin),
                NotificationProgressStatus.IS_PENDING
            )
        )
        list.add(
            StatusDataModel(
                getString(Strings.screen_time_line_display_text_status_card_top_up_title),
                getString(Strings.screen_time_line_display_text_status_card_top_up_description),
                getString(Strings.screen_time_line_display_text_status_card_top_up_action),
                resources.getDrawable(R.drawable.ic_dashboard_topup),
                NotificationProgressStatus.IS_PENDING
            )
        )
        return list
    }

}