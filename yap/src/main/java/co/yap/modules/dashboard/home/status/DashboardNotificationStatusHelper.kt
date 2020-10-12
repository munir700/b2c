package co.yap.modules.dashboard.home.status

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import co.yap.R
import co.yap.databinding.FragmentYapHomeBinding
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager

class DashboardNotificationStatusHelper(
    val context: Context, val binding: FragmentYapHomeBinding,
    val viewModel: IYapHome.ViewModel, val fargment: FragmentActivity? = null

) {
    fun getStringHelper(resourceKey: String): String = Translator.getString(context, resourceKey)

    init {
        setUpAdapter()
    }

    private fun setUpAdapter() {
        var dashboardNotificationStatusAdapter =
            DashboardNotificationStatusAdapter(context, getStatusList())
        dashboardNotificationStatusAdapter =
            context?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
        dashboardNotificationStatusAdapter?.allowFullItemClickListener = false


        dashboardNotificationStatusAdapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                var statusDataModel: StatusDataModel = data as StatusDataModel

                when (statusDataModel.position) {
                    0 -> {
                        fargment?.startActivityForResult(
                            FragmentPresenterActivity.getIntent(
                                context,
                                Constants.MODE_MEETING_CONFORMATION,
                                null
                            ), RequestCodes.REQUEST_MEETING_CONFIRMED
                        )
                    }
                    2 -> {
                        //open email
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                        context.startActivity(Intent.createChooser(intent, "Choose"))
                    }
                    3 -> {
                        fargment?.startActivityForResult(
                            SetCardPinWelcomeActivity.newIntent(
                                context,
                                MyUserManager.getPrimaryCard()
                            ), RequestCodes.REQUEST_FOR_SET_PIN
                        )
                    }
                    4 -> {
                        openTopUpScreen()
                    }
                }
            }
        })

        binding.lyInclude.rvNotificationStatus.adapter = dashboardNotificationStatusAdapter

    }

    private fun openTopUpScreen() {
        context.startActivity(TopUpLandingActivity.getIntent(context))
    }

    private fun getStatusList(): MutableList<StatusDataModel> {
        val list = ArrayList<StatusDataModel>()
        list.add(
            StatusDataModel(
                0,
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_delivery),
                NotificationProgressStatus.IS_COMPLETED,
                CardDeliveryStatus.SHIPPING
            )
        )
        list.add(
            StatusDataModel(
                1,
                getStringHelper(Strings.screen_time_line_display_text_status_card_delivered_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                context.resources.getDrawable(R.drawable.ic_dashboard_active),
//                resources.getDrawable(R.drawable.card_spare),
                NotificationProgressStatus.IN_PROGRESS,
                CardDeliveryStatus.SHIPPED
            )
        )

        list.add(
            StatusDataModel(
                2,
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_title),
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_description),
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                3,
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_set_pin),
                NotificationProgressStatus.IS_PENDING,
                CardDeliveryStatus.SHIPPED
            )
        )
        list.add(
            StatusDataModel(
                4,
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
