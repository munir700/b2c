package co.yap.modules.dashboard.more.home.fragments

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMoreHomeBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.more.cdm.CdmMapFragment
import co.yap.modules.dashboard.more.home.adaptor.YapMoreAdaptor
import co.yap.modules.dashboard.more.home.interfaces.IMoreHome
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.home.viewmodels.MoreHomeViewModel
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.notifications.main.NotificationsActivity
import co.yap.modules.dashboard.more.yapforyou.activities.YAPForYouActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.guidedtour.OnTourItemClickListener
import co.yap.widgets.guidedtour.TourSetup
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.NotificationHelper
import co.yap.yapcore.helpers.TourGuideManager
import co.yap.yapcore.helpers.TourGuideType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.leanplum.MoreB2CEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import com.leanplum.Leanplum
import com.liveperson.infra.configuration.Configuration.getDimension

class YapMoreFragment : YapDashboardChildFragment<FragmentMoreHomeBinding, IMoreHome.ViewModel>(),
    IMoreHome.View {

    val yapMoreAdapter: YapMoreAdaptor? by lazy {
        context?.let { YapMoreAdaptor(it, viewModel.getMoreOptions()) }
    }
    override fun getBindingVariable(): Int = BR.viewModel
    private var tourStep: TourSetup? = null

    override fun getLayoutId(): Int = R.layout.fragment_more_home

    override val viewModel: MoreHomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initComponents()
        setupRecycleView()
    }


    fun countNotifications(transactionCount: Int?): Int {
        val leanPlumNotificationCount = Leanplum.getInbox().unreadCount()
        val prefsNotificationCount = NotificationHelper.getNotifications(
            SessionManager.user,
            SessionManager.card.value,
            requireContext()
        ).size
        val transactionsCount = transactionCount ?: 0
        return leanPlumNotificationCount.plus(prefsNotificationCount).plus(transactionsCount)
    }

    fun handleNotificationData(transactionCount: Int?) {
       yapMoreAdapter?.let { adapter->
           adapter.apply {
               if (getDataList().isNotEmpty()) {
                   val item = getDataForPosition(0)
                   item.apply {
                       badgeCount = countNotifications(transactionCount)
                       hasBadge = badgeCount > 0
                   }.also {
                       viewModel.badgeCount.set(it.badgeCount.toString())
                       viewModel.hasBadge.set(it.hasBadge)
                       setItemAt(0, it)
                   }
               }
           }

        }
    }

    override fun onResume() {
        super.onResume()
        initComponents()
        viewModel.getTransactionsNotificationsCount()
    }

    /**
     * Deprecated Code of UpdateNotificationCounter with Callback Approach
     */
/*    private fun updateNotificationCounter() {
//        Leanplum.forceContentUpdate()
        if (::adapter.isInitialized) {
            if (!adapter.getDataList().isNullOrEmpty()) {
                val notificationCount: Int = NotificationHelper.getNotifications(
                    SessionManager.user,
                    SessionManager.card.value,
                    requireContext()
                ).size
                val item = adapter.getDataForPosition(0)
                viewModel.getTransactionsNotificationsCount {
                    item.badgeCount =
                        Leanplum.getInbox().unreadCount().plus(notificationCount).plus(it ?: 0)
                    viewModel.badgeCount.set(item.badgeCount.toString())
                    viewModel.hasBadge.set(item.badgeCount > 0)
                    //Leanplum.getInbox().unreadCount() > 0
//                Leanplum.getInbox().addChangedHandler(object : InboxChangedCallback() {
//                    override fun inboxChanged() {
//                        item.badgeCount = item.badgeCount.plus(Leanplum.getInbox().unreadCount())
//                        item.hasBadge = item.badgeCount > 0
//                    }
//                })
                    item.hasBadge = item.badgeCount > 0
                    adapter.setItemAt(0, item)
                }
                adapter.setItemAt(0, item)
            }
        }
    }*/

    private fun initComponents() {
        with(viewDataBinding) {
            tvName.text =
                SessionManager.user?.currentCustomer?.getFullName()

            val ibanSpan = SpannableString("IBAN ${SessionManager.user?.iban?.maskIbanNumber()}")
            tvIban.text = Utils.setSpan(
                0,
                4,
                ibanSpan,
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
            )

            SessionManager.user?.bank?.swiftCode?.let {
                val bicSpan = SpannableString("BIC $it")
                tvBic.text = Utils.setSpan(
                    0,
                    3,
                    bicSpan,
                    ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
                )
            }
        }

    }

    override fun onDestroyView() {
        removeObservers()
        super.onDestroyView()
    }
    private fun setupRecycleView() {
        with(viewDataBinding) {
            recyclerOptions.apply {
                adapter = yapMoreAdapter
                itemAnimator?.changeDuration = 0
                addItemDecoration(
                    SpaceGridItemDecoration(dimen(R.dimen.margin_normal), 3, true)
                )
            }
            yapMoreAdapter?.let { adapter ->
                adapter.apply {
                    allowFullItemClickListener = true
                    setItemListener(listener)
                }
            }
        }
    }

    override fun setObservers() {
        viewModel.notificationCountData.observe(viewLifecycleOwner, ::handleNotificationData)
        viewModel.clickEvent.observe(viewLifecycleOwner, observer)
        if (context is YapDashboardActivity) {
            (context as YapDashboardActivity).viewModel.isYapMoreFragmentVisible.observe(
                viewLifecycleOwner,
                Observer { isMoreFragmentVisible ->
                    if (isMoreFragmentVisible) {
                        tourStep =
                            requireActivity().launchTourGuide(TourGuideType.MORE_SCREEN) {
                                this.addAll(setViewsArray())
                            }
                    } else {
                        tourStep?.let {
                            if (it.isShowing)
                                it.dismiss()
                        }
                    }
                })
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        if (context is YapDashboardActivity) {
            (context as YapDashboardActivity).viewModel.isYapMoreFragmentVisible.removeObservers(
                this
            )
        }
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is MoreOption)
                viewModel.clickEvent.setValue(data.id)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.imgProfile, R.id.imgSettings -> {
                requireActivity().launchActivity<MoreActivity>(requestCode = RequestCodes.REQUEST_CODE_MORE_ACTIVITY) {
                }
            }
            R.id.btnBankDetails -> {
                trackEventWithScreenName(FirebaseEvent.CLICK_BANK_DETAILS)
                openAccountDetailBottomSheet()
            }
            Constants.MORE_YAP_FOR_YOU -> {
                launchActivity<YAPForYouActivity>(type = FeatureSet.YAP_FOR_YOU)
            }
            Constants.MORE_NOTIFICATION, R.id.imgNotification -> {
                trackEventWithScreenName(FirebaseEvent.CLICK_NOTIFICATIONS)
                requireActivity().launchActivity<NotificationsActivity>(requestCode = RequestCodes.REQUEST_NOTIFICATION_FLOW) {
                }
            }
            Constants.MORE_LOCATE_ATM -> {
                trackEventWithScreenName(FirebaseEvent.CLICK_ATM_LOCATION)
                trackEvent(MoreB2CEvents.OPEN_ATM_MAP.type)
                startFragment(CdmMapFragment::class.java.name)
            }
            Constants.MORE_INVITE_FRIEND -> {
                trackEventWithScreenName(FirebaseEvent.CLICK_INVITE_FRIEND)
                startFragment(
                    InviteFriendFragment::class.java.name, false,
                    bundleOf()
                )
            }
            Constants.MORE_HELP_SUPPORT -> {
                trackEventWithScreenName(FirebaseEvent.CLICK_HELP_MORE_SCREEN)
                startActivity(
                    FragmentPresenterActivity.getIntent(
                        requireContext(),
                        Constants.MODE_HELP_SUPPORT, null
                    )
                )
            }
        }
    }

    private fun setViewsArray(): ArrayList<GuidedTourViewDetail> {
        val list = ArrayList<GuidedTourViewDetail>()
        list.add(
            GuidedTourViewDetail(
                viewDataBinding.btnBankDetails,
                title = getString(Strings.screen_more_detail_display_text_tour_bank_details_heading),
                description = getString(Strings.screen_more_detail_display_text_tour_bank_details_description),
                padding = -getDimension(R.dimen._45sdp),
                circleRadius = getDimension(R.dimen._65sdp),
                callBackListener = tourItemListener
            )
        )
        return list
    }

    private val tourItemListener = object : OnTourItemClickListener {
        override fun onTourCompleted(pos: Int) {
            TourGuideManager.lockTourGuideScreen(
                TourGuideType.MORE_SCREEN,
                completed = true
            )
        }

        override fun onTourSkipped(pos: Int) {
            TourGuideManager.lockTourGuideScreen(
                TourGuideType.MORE_SCREEN,
                skipped = true
            )
        }
    }

    private fun openAccountDetailBottomSheet() {
        launchBottomSheetSegment(
            accountDetailBottomSheetItemClickListener,
            configuration = BottomSheetConfiguration(
                heading = Translator.getString(
                    requireContext(),
                    Strings.screen_more_display_text_bank_details
                )
            ),
            viewType = Constants.VIEW_ITEM_ACCOUNT_DETAIL,
            listData = viewModel.loadBottomSheetData(),
            buttonClick = onBottomSheetButtonClick
        )
    }

    private val accountDetailBottomSheetItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is CoreBottomSheetData) {
                when (view.id) {
                    R.id.ivCopy -> {
                        Utils.copyToClipboard(view.context, data.subContent ?: "")
                        view.context.toast(
                            Translator.getString(
                                requireContext(),
                                Strings.screen_more_detail_display_text_copied_to_clipboard
                            ), Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }
    }
    private val onBottomSheetButtonClick = View.OnClickListener {
        if (viewModel.list.isNotEmpty())
            context?.share(text = getBody(), title = "Share")
    }

    private fun getBody(): String {
        return "Name: ${viewModel.list[0].subContent}\n" +
                "SWIFT/BIC: ${viewModel.list[4].subContent}\n" +
                "IBAN: ${viewModel.list[1].subContent}\n" +
                "Account: ${viewModel.list[2].subContent}\n" +
                "Bank: ${viewModel.list[3].subContent}\n" +
                "Address: ${viewModel.list[5].subContent}\n"
    }
}