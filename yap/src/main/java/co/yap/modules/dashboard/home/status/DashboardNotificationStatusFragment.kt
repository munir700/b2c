package co.yap.modules.dashboard.home.status

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentDashboardNotificationStatusesBinding
import co.yap.modules.dashboard.home.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.modules.dashboard.home.viewmodels.YapHomeViewModel
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.helpers.extentions.shortToast
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_dashboard_notification_statuses.*

class DashboardNotificationStatusFragment : YapDashboardChildFragment<IYapHome.ViewModel>(),
    IYapHome.View {

    override var transactionViewHelper: TransactionsViewHelper? = null
    var dashboardNotificationStatusHelper: DashboardNotificationStatusHelper? = null

    var dashboardNotificationStatusAdapter: DashboardNotificationStatusAdapter? = null

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_dashboard_notification_statuses

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
//        dashboardNotificationStatusHelper =
//            DashboardNotificationStatusHelper(requireContext(), getBindings(), viewModel)

    }

    private fun setUpAdapter() {
        dashboardNotificationStatusAdapter =
            context?.let { DashboardNotificationStatusAdapter(it, getStatusList()) }
        dashboardNotificationStatusAdapter?.allowFullItemClickListener = false

        dashboardNotificationStatusAdapter?.onItemClickListener = listener

        rvNotificationStatus.adapter = dashboardNotificationStatusAdapter

    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

            var statusDataModel: StatusDataModel = data as StatusDataModel

            when (statusDataModel.position) {
                0 -> {
                    context.shortToast("0")
                    //
                    startActivityForResult(
                        FragmentPresenterActivity.getIntent(
                            requireContext(),
                            Constants.MODE_MEETING_CONFORMATION,
                            null
                        ), RequestCodes.REQUEST_MEETING_CONFIRMED
                    )
                    //
                }
                1 -> {
                    context.shortToast("1")
                }
                2 -> {
                    //open email

                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    startActivity(Intent.createChooser(intent, "Choose"))
                }
                3 -> {
                    startActivityForResult(
                        SetCardPinWelcomeActivity.newIntent(
                            requireContext(),
                            MyUserManager.getPrimaryCard()
                        ), RequestCodes.REQUEST_FOR_SET_PIN
                    )

                }
                4 -> {
                    context?.startActivity(activity?.let { TopUpLandingActivity.getIntent(it) })
                }
            }
        }
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
                0,
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_title),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_description),
                getString(Strings.screen_time_line_display_text_status_card_on_the_way_action),
                resources.getDrawable(R.drawable.ic_dashboard_delivery),
                NotificationProgressStatus.IS_COMPLETED,
                CardDeliveryStatus.SHIPPING
            )
        )
        list.add(
            StatusDataModel(
                1,
                getString(Strings.screen_time_line_display_text_status_card_delivered_title),
                getString(Strings.screen_time_line_display_text_status_card_delivered_description),
                null,
                resources.getDrawable(R.drawable.ic_dashboard_active),
//                resources.getDrawable(R.drawable.card_spare),
                NotificationProgressStatus.IN_PROGRESS,
                CardDeliveryStatus.SHIPPED
            )
        )

        list.add(
            StatusDataModel(
                2,
                getString(Strings.screen_time_line_display_text_status_additional_requirements_title),
                getString(Strings.screen_time_line_display_text_status_additional_requirements_description),
                getString(Strings.screen_time_line_display_text_status_additional_requirements_action),
                resources.getDrawable(R.drawable.ic_dashboard_active),
                NotificationProgressStatus.IN_PROGRESS
            )
        )

        list.add(
            StatusDataModel(
                3,
                getString(Strings.screen_time_line_display_text_status_set_card_pin_title),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_description),
                getString(Strings.screen_time_line_display_text_status_set_card_pin_action),
                resources.getDrawable(R.drawable.ic_dashboard_set_pin),
                NotificationProgressStatus.IS_PENDING,
                CardDeliveryStatus.SHIPPED
            )
        )
        list.add(
            StatusDataModel(
                4,
                getString(Strings.screen_time_line_display_text_status_card_top_up_title),
                getString(Strings.screen_time_line_display_text_status_card_top_up_description),
                getString(Strings.screen_time_line_display_text_status_card_top_up_action),
                resources.getDrawable(R.drawable.ic_dashboard_topup),
                NotificationProgressStatus.IS_PENDING
            )
        )
        return list
    }

    private fun getBindings(): FragmentDashboardNotificationStatusesBinding {
        return viewDataBinding as FragmentDashboardNotificationStatusesBinding
    }
}