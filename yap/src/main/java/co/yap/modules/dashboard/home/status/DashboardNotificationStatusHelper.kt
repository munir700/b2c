package co.yap.modules.dashboard.home.status

import android.content.Context
import co.yap.R
import co.yap.databinding.FragmentDashboardNotificationStatusesBinding
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.translation.Strings
import co.yap.translation.Translator

class DashboardNotificationStatusHelper(
    val context: Context, val binding: FragmentDashboardNotificationStatusesBinding,
    val viewModel: IYapHome.ViewModel

) {
    var dashboardNotificationStatusAdapter: DashboardNotificationStatusAdapter? = null

    fun getStringHelper(resourceKey: String): String = Translator.getString(context, resourceKey)

    init {
        setUpAdapter()
    }


    private fun setUpAdapter() {
        dashboardNotificationStatusAdapter =
            context?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
        binding.rvNotificationStatus.adapter = dashboardNotificationStatusAdapter
    }


    private fun getStatusList(): MutableList<StatusDataModel> {
        val list = ArrayList<StatusDataModel>()
        list.add(
            StatusDataModel(
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_delivery),
                NotificationProgressStatus.IS_COMPLETED
            )
        )
        list.add(
            StatusDataModel(
                getStringHelper(Strings.screen_time_line_display_text_status_card_delivered_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                context.resources.getDrawable(R.drawable.card_spare),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_title),
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_description),
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_set_pin),
                NotificationProgressStatus.IS_PENDING
            )
        )
        list.add(
            StatusDataModel(
                getStringHelper(Strings.screen_time_line_display_text_status_card_top_up_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_top_up_description),
                getStringHelper(Strings.screen_time_line_display_text_status_card_top_up_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_topup),
                NotificationProgressStatus.IS_PENDING
            )
        )
        return list
    }

}