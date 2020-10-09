package co.yap.modules.dashboard.home.status

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentDashboardNotificationStatusesBinding
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.more.yapforyou.fragments.YAPForYouFragmentDirections
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.extentions.shortToast
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.ezaka.customer.app.utils.getActivityFromContext
import kotlinx.android.synthetic.main.fragment_dashboard_notification_statuses.*

class DashboardNotificationStatusHelper(
    val context: Context, val binding: FragmentDashboardNotificationStatusesBinding,
    val viewModel: IYapHome.ViewModel

) {
//    var dashboardNotificationStatusAdapter: DashboardNotificationStatusAdapter? = null

    fun getStringHelper(resourceKey: String): String = Translator.getString(context, resourceKey)

    init {
        setUpAdapter()
    }

    private fun setUpAdapter() {
     var   dashboardNotificationStatusAdapter = DashboardNotificationStatusAdapter(context, getStatusList())
//             dashboardNotificationStatusAdapter =
//                context?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
            dashboardNotificationStatusAdapter?.allowFullItemClickListener = false

            dashboardNotificationStatusAdapter?.onItemClickListener = listener

            binding.rvNotificationStatus.adapter = dashboardNotificationStatusAdapter

        }

        private val listener = object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {

                var statusDataModel: StatusDataModel = data as StatusDataModel

                when (statusDataModel.position) {
                    0 -> {
                        context.shortToast("0")

                        //

//                        context.startActivityForResult(
//                            FragmentPresenterActivity.getIntent(
//                                requireContext(),
//                                Constants.MODE_MEETING_CONFORMATION,
//                                null
//                            ), RequestCodes.REQUEST_MEETING_CONFIRMED
//                        )


                        //
                    }
                    1 -> {
                        context.shortToast("1")
                    }
                    2 -> {
                        context.shortToast("2")
                        //open email

//                        val intent = Intent(Intent.ACTION_MAIN)
//                        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
//                        startActivity(Intent.createChooser(intent, "Choose"))
                    }
                    3 -> {
                        context.shortToast("3")

                        //
//                        startActivityForResult(
//                            SetCardPinWelcomeActivity.newIntent(
//                                requireContext(),
//                                MyUserManager.card.value
////                            MyUserManager.getPrimaryCard()
//                            ), RequestCodes.REQUEST_FOR_SET_PIN
//                        )

                        //

                    }
                    4 -> {
                        context.shortToast("4")

//                        context?.startActivity(activity?.let { TopUpLandingActivity.getIntent(it) })
                    }
                }
            }
        }


        private fun checkStatus()  {
//        MyUserManager.card.value?.status =

//        viewModel.EVENT_SET_CARD_PIN -> {
//            startActivityForResult(//here
//                SetCardPinWelcomeActivity.newIntent(
//                    requireContext(),
//                    MyUserManager.getPrimaryCard()
//                ), RequestCodes.REQUEST_FOR_SET_PIN
//            )
//        }

//        viewModel.ON_ADD_NEW_ADDRESS_EVENT -> {
//            startActivityForResult(
//                FragmentPresenterActivity.getIntent(
//                    requireContext(),
//                    Constants.MODE_MEETING_CONFORMATION,
//                    null
//                ), RequestCodes.REQUEST_MEETING_CONFIRMED
//            )
//        }
    }

    private fun openTopUpScreen() {
        context.startActivity(TopUpLandingActivity.getIntent(context))
    }

    private fun getStatusList(): MutableList<StatusDataModel> {
        val list = ArrayList<StatusDataModel>()
        list.add(
            StatusDataModel(0,
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getStringHelper(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_delivery),
                NotificationProgressStatus.IS_COMPLETED
            )
        )
        list.add(
            StatusDataModel(1,
                getStringHelper(Strings.screen_time_line_display_text_status_card_delivered_title),
                getStringHelper(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                context.resources.getDrawable(R.drawable.card_spare),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(2,
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_title),
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_description),
                getStringHelper(Strings.screen_time_line_display_text_status_additional_requirements_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(3,
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getStringHelper(Strings.screen_time_line_display_text_status_set_card_pin_action),
                context.resources.getDrawable(R.drawable.ic_dashboard_set_pin),
                NotificationProgressStatus.IS_PENDING
            )
        )
        list.add(
            StatusDataModel(4,
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