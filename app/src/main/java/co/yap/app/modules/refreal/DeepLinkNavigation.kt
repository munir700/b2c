package co.yap.app.modules.refreal

import android.content.Intent
import androidx.navigation.NavDeepLinkBuilder
import co.yap.app.R
import co.yap.app.modules.refreal.DeepLinkNavigation.DeepLinkFlow.SET_PIN
import co.yap.modules.dashboard.cards.analytics.main.activities.CardAnalyticsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.more.cdm.CdmMapFragment
import co.yap.modules.dashboard.more.help.fragments.HelpSupportFragment
import co.yap.modules.dashboard.more.notifications.main.NotificationsActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyDashboardActivity
import co.yap.modules.dashboard.yapit.topup.cardslisting.TopUpBeneficiariesActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.IDeepLinkHandler
import co.yap.yapcore.helpers.SingletonHolder
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.managers.SessionManager

class DeepLinkNavigation private constructor(private val activity: YapDashboardActivity) :
    IDeepLinkHandler {
    companion object :
        SingletonHolder<DeepLinkNavigation, YapDashboardActivity>(::DeepLinkNavigation)

    override fun handleDeepLinkFlow(flowId: String?) {
        flowId?.let {
            when (it) {
                SET_PIN.flowId -> {
                }
                DeepLinkFlow.CARD_STATMENT.flowId -> {
                    SessionManager.getPrimaryCard()?.let {
                        activity.launchActivity<CardStatementsActivity> {
                            putExtra("card", it)
                            putExtra("isFromDrawer", false)
                        }
                    }
                }
                DeepLinkFlow.ANALYTICS.flowId -> {
                    activity.launchActivity<CardAnalyticsActivity>(type = FeatureSet.ANALYTICS)
                }
                DeepLinkFlow.TERM_CONDITION.flowId -> {
                }
                DeepLinkFlow.UPDATE_EMIRATES_ID.flowId -> {
                }
                DeepLinkFlow.PERSON_DETAILS.flowId -> {
                    val pendingIntent = NavDeepLinkBuilder(activity)
                        .setGraph(R.navigation.more_main_navigation)
                        .setDestination(R.id.personalDetailsFragment)
                        .createPendingIntent()
                    pendingIntent.send(activity, 0, Intent())
                }
                DeepLinkFlow.SETTINGS.flowId -> {
                }
                DeepLinkFlow.HELP_SUPPORT.flowId -> {
                    activity.startActivity(
                        FragmentPresenterActivity.getIntent(
                            activity,
                            Constants.MODE_HELP_SUPPORT, null
                        )
                    )
                }
                DeepLinkFlow.INVITE_FRIEND.flowId -> {
                }
                DeepLinkFlow.LOCATE_ATM_CDM.flowId -> {
                    activity.startFragment<HelpSupportFragment>(CdmMapFragment::class.java.name)
                }
                DeepLinkFlow.NOTIFICATIONS.flowId -> {
                    activity.launchActivity<NotificationsActivity>(requestCode = RequestCodes.REQUEST_NOTIFICATION_FLOW)
//                    activity.launchActivity<NotificationsActivity> { }
                }
                DeepLinkFlow.YAP_FOR_YOU.flowId -> {
                }
                DeepLinkFlow.ADD_SPARE_CARD.flowId -> {
                }
                DeepLinkFlow.CARDS.flowId -> {
                    activity.getViewBinding().viewPager.setCurrentItem(2, false)
                }
                DeepLinkFlow.QR_CODE.flowId -> {
                }
                DeepLinkFlow.YAP_TO_YAP.flowId -> {
                }
                DeepLinkFlow.SEND_MONEY.flowId -> {
                    activity.launchActivity<SendMoneyDashboardActivity>(type = FeatureSet.SEND_MONEY)
                }
                DeepLinkFlow.TOP_UP.flowId -> {
                    activity.launchActivity<TopUpBeneficiariesActivity>(requestCode = RequestCodes.REQUEST_SHOW_BENEFICIARY) {
                        putExtra(
                            Constants.SUCCESS_BUTTON_LABEL,
                            activity.getString(Strings.screen_topup_success_display_text_dashboard_action_button_title)
                        )
                    }
                }
                DeepLinkFlow.YAP_STORE.flowId -> {
                    activity.getViewBinding().viewPager.setCurrentItem(1, false)
                }
                else -> {
                }
            }
        }
        SessionManager.deepLinkFlowId.value = null
    }

    private enum class DeepLinkFlow(val flowId: String) {
        SET_PIN("set_pin"),
        CARD_STATMENT("card_statements"),
        ANALYTICS("analytics"),
        TERM_CONDITION("terms_conditions"),
        UPDATE_EMIRATES_ID("update_emirates_id"),
        PERSON_DETAILS("personal_details"),
        SETTINGS("settings"),
        HELP_SUPPORT("help_support"),
        INVITE_FRIEND("invite_friend"),
        LOCATE_ATM_CDM("locate_atm_cdm"),
        NOTIFICATIONS("notifications"),
        YAP_FOR_YOU("yap_for_you"),
        ADD_SPARE_CARD("add_spare_card"),
        CARDS("cards"),
        QR_CODE("qr_code"),
        YAP_TO_YAP("yap_to_yap"),
        SEND_MONEY("send_money"),
        TOP_UP("top_up"),
        YAP_STORE("yap_store"),
        DASHBOARD("dashboard"),
    }
}





