package co.yap.modules.dashboard.main.activities

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityYapDashboardBinding
import co.yap.modules.dashboard.cards.analytics.main.activities.CardAnalyticsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.home.fragments.YapHomeFragment
import co.yap.modules.dashboard.main.adapters.YapDashboardAdaptor
import co.yap.modules.dashboard.main.interfaces.IYapDashboard
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.modules.dashboard.more.home.fragments.InviteFriendFragment
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.store.fragments.YapStoreFragment
import co.yap.modules.dashboard.unverifiedemail.UnVerifiedEmailActivity
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyDashboardActivity
import co.yap.modules.dashboard.yapit.topup.landing.TopUpLandingActivity
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.sendmoney.home.main.SMBeneficiaryParentActivity
import co.yap.sendmoney.y2y.home.activities.YapToYapDashboardActivity
import co.yap.translation.Strings
import co.yap.widgets.CoreButton
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.permissions.PermissionHelper
import co.yap.yapcore.managers.SessionManager
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import kotlinx.android.synthetic.main.activity_yap_dashboard.*
import kotlinx.android.synthetic.main.layout_drawer_yap_dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.cachapa.expandablelayout.ExpandableLayout

class YapDashboardActivity : BaseBindingActivity<IYapDashboard.ViewModel>(), IYapDashboard.View,
    IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    val fragments: Array<Fragment> = arrayOf(YapHomeFragment(), YapStoreFragment())
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_yap_dashboard

    override val viewModel: YapDashBoardViewModel
        get() = ViewModelProviders.of(this).get(YapDashBoardViewModel::class.java)

    lateinit var adapter: YapDashboardAdaptor
    var permissionHelper: PermissionHelper? = null
    private var actionMenu: FloatingActionMenu? = null
    private var mNavigator: ActivityNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavigator = (applicationContext as NavigatorProvider).provideNavigator()
        SessionManager.getCountriesFromServer { _, _ -> }
        setupPager()
        addObservers()
        addListeners()
        //  setupOldYapButtons()
        setupNewYapButtons()
        logEvent()
        initializeChatOverLayButton()
        lifecycleScope.launch {
            delay(100)
            mNavigator?.handleDeepLinkFlow(
                this@YapDashboardActivity,
                SessionManager.deepLinkFlowId.value
            )
        }
    }

    private fun logEvent() {
        val logger: AppEventsLogger = AppEventsLogger.newLogger(this)
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP)
    }

    private fun setupOldYapButtons() {
        actionMenu = FloatingActionMenu.Builder(this)
            .setStartAngle(0)
            .setEndAngle(-180).setRadius(dimen(R.dimen._69sdp))
            .setAnimationHandler(SlideInAnimationHandler())
            .addSubActionView(
                getString(R.string.yap_to_yap),
                R.drawable.ic_yap_to_yap,
                R.layout.component_yap_menu_sub_button,
                this, 1
            )
            .addSubActionView(
                getString(R.string.top_up),
                R.drawable.ic_top_up,
                R.layout.component_yap_menu_sub_button,
                this, 2
            )
            .addSubActionView(
                getString(R.string.send_money),
                R.drawable.ic_send_money,
                R.layout.component_yap_menu_sub_button,
                this, 3
            )
            .attachTo(getViewBinding().ivYapIt).setAlphaOverlay(getViewBinding().flAlphaOverlay)
            .setTxtYapIt(getViewBinding().txtYapIt)
            .setStateChangeListener(object :
                FloatingActionMenu.MenuStateChangeListener {
                override fun onMenuOpened(menu: FloatingActionMenu) {
                    overLayButtonVisibility(View.GONE)
                }

                override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
                    lifecycleScope.launch {
                        delay(200)
                        overLayButtonVisibility(View.VISIBLE)
                    }

                    when (subActionButtonId) {
                        1 -> {
                            checkPermission()
                        }
                        2 -> {
                            launchActivity<TopUpLandingActivity>(type = FeatureSet.TOP_UP)
                        }
                        3 -> {
                            launchActivity<SMBeneficiaryParentActivity>(type = FeatureSet.SEND_MONEY)
                        }
                    }
                }
            }).build()
    }

    private fun setupNewYapButtons() {
        actionMenu = FloatingActionMenu.Builder(this)
            .setStartAngle(0)
            .setEndAngle(-180).setRadius(dimen(R.dimen._69sdp))
            .setAnimationHandler(SlideInAnimationHandler())
            .addSubActionView(
                getString(Strings.common_send_money),
                R.drawable.ic_send_money,
                R.layout.component_yap_menu_sub_button,
                this, 1
            )/*.addSubActionView(
                getString(Strings.common_pay_bills),
                R.drawable.ic_bill,
                R.layout.component_yap_menu_sub_button,
                this, 2
            )*/.addSubActionView(
                getString(Strings.common_add_money),
                R.drawable.ic_add_sign_white,
                R.layout.component_yap_menu_sub_button,
                this, 3
            )
            .attachTo(getViewBinding().ivYapIt).setAlphaOverlay(getViewBinding().flAlphaOverlay)
            .setTxtYapIt(getViewBinding().txtYapIt)
            .setStateChangeListener(object :
                FloatingActionMenu.MenuStateChangeListener {
                override fun onMenuOpened(menu: FloatingActionMenu) {
                    trackEventWithScreenName(FirebaseEvent.CLICK_YAPIT)
                    overLayButtonVisibility(View.GONE)
                }

                override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
                    lifecycleScope.launch {
                        delay(200)
                        overLayButtonVisibility(View.VISIBLE)
                    }
                    when (subActionButtonId) {
                        1 -> {
                            trackEventWithScreenName(FirebaseEvent.CLICK_ACTIONS_SENDMONEY)
                            launchActivity<SendMoneyDashboardActivity>(type = FeatureSet.SEND_MONEY)
                            /*if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus) {
                                checkPermission()
                            } else {
                                showToast("${getString(Strings.screen_popup_activation_pending_display_text_message)}^${AlertType.TOAST.name}")
                            }*/
                        }
                        2 -> {
                            /* if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus) {
                                 openTopUpScreen()
                             } else {
                                 showToast("${getString(Strings.screen_popup_activation_pending_display_text_message)}^${AlertType.TOAST.name}")
                             }*/
                        }
                        3 -> {
                            launchActivity<AddMoneyActivity>(type = FeatureSet.TOP_UP)
                        }
                    }
                }
            })
            .build()
    }

    private fun setupPager() {
//        SessionManager.card = MutableLiveData()
        adapter = YapDashboardAdaptor(supportFragmentManager)
        getViewBinding().viewPager.adapter = adapter

        with(getViewBinding().viewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }
        getViewBinding().viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                enableDrawerSwipe(position == 0)
                when (position) {
                    YAP_HOME_FRAGMENT -> {
                        CoroutineScope(Main).launch {
                            delay(300)
                            viewModel.isYapHomeFragmentVisible.value = true
                            viewModel.isYapMoreFragmentVisible.value = false
                            viewModel.isYapCardsFragmentVisible.value = false
                            viewModel.isYapStoreFragmentVisible.value = false
                        }
                    }
                    YAP_STORE_FRAGMENT -> {
                        CoroutineScope(Main).launch {
                            delay(300)
                            viewModel.isYapStoreFragmentVisible.value = true
                            viewModel.isYapHomeFragmentVisible.value = false
                            viewModel.isYapMoreFragmentVisible.value = false
                            viewModel.isYapCardsFragmentVisible.value = false
                        }
                    }
                    YAP_CARDS_FRAGMENT -> {
                        CoroutineScope(Main).launch {
                            delay(300)
                            viewModel.isYapCardsFragmentVisible.value = true
                            viewModel.isYapStoreFragmentVisible.value = false
                            viewModel.isYapHomeFragmentVisible.value = false
                            viewModel.isYapMoreFragmentVisible.value = false
                        }
                    }
                    YAP_MORE_FRAGMENT -> {
                        CoroutineScope(Main).launch {
                            delay(300)
                            viewModel.isYapMoreFragmentVisible.value = true
                            viewModel.isYapCardsFragmentVisible.value = false
                            viewModel.isYapStoreFragmentVisible.value = false
                            viewModel.isYapHomeFragmentVisible.value = false
                        }
                    }
                }
            }
        })
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnCopy -> shareAccountInfo()
                R.id.lUserInfo -> expandableLayout.toggle(true)
                R.id.imgProfile -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_PROFILE)
                    startActivity(MoreActivity.newIntent(this))
                }
                R.id.tvLogOut -> {
                    logoutAlert()
                }
                viewModel.EVENT_LOGOUT_SUCCESS -> {
                    doLogout()
                }
            }
        })

        viewModel.showUnverifedscreen.observe(this, Observer {
            if (it) {
                showUnverifiedPopup()
            } else
                viewModel.isUnverifiedScreenNotVisible.value = true
        })
    }

    private fun shareAccountInfo() {
        trackEventWithScreenName(FirebaseEvent.SHARE_ACCOUNT_DETAILS)
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, viewModel.getAccountInfo())
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    private fun showUnverifiedPopup() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_change_unverified_email)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvUnverifiedDescription = dialog.findViewById<TextView>(R.id.tvUnverifiedDescription)
        val tvEmail = dialog.findViewById<TextView>(R.id.tvEmail)
        val tvTroubleDescription = dialog.findViewById<TextView>(R.id.tvTroubleDescription)
        tvUnverifiedDescription.text =
            getString(Strings.screen_email_verified_popup_display_text_title).format(
                SessionManager.user!!.currentCustomer.firstName
            )

        SessionManager.user?.currentCustomer?.email?.let {
            tvEmail.text = it
        }

        val fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary))
        val myClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                dialog.dismiss()
                trackEventWithScreenName(FirebaseEvent.CHANGE_MAIL)
                startActivity(
                    UnVerifiedEmailActivity.newIntent(widget.context)
                )
            }
        }

        val newValue =
            getString(Strings.screen_email_verified_popup_display_text_click_here).plus("")
        val clickValue =
            getString(Strings.screen_email_verified_popup_button_title_click_here)
        val spanStr = SpannableStringBuilder("$clickValue $newValue")

        spanStr.setSpan(
            myClickableSpan,
            0,
            (clickValue.length + 1),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanStr.setSpan(
            fcs,
            0,
            clickValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvTroubleDescription.movementMethod = LinkMovementMethod.getInstance()
        tvTroubleDescription.text = spanStr

        dialog.findViewById<CoreButton>(R.id.btnOpenMailApp).setOnClickListener {
            trackEventWithScreenName(FirebaseEvent.OPEN_MAIL_APP)
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            startActivity(Intent.createChooser(intent, "Choose Email App"))
        }
        dialog.findViewById<AppCompatImageView>(R.id.ivClose).setOnClickListener {
            dialog.dismiss()
            viewModel.isUnverifiedScreenNotVisible.value = true
        }
        dialog.findViewById<TextView>(R.id.btnLater).setOnClickListener {
            dialog.dismiss()
            viewModel.resendVerificationEmail() {
                trackEventWithScreenName(FirebaseEvent.MAIL_VERIFICATION_RESEND)
                viewModel.isUnverifiedScreenNotVisible.value = true
            }
        }
        dialog.show()
    }

    fun showHideBottomBar(show: Boolean) {
        getViewBinding().rlYapIt.visibility = if (show) View.VISIBLE else View.GONE
        getViewBinding().bottomNav.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        mNavigator = null
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END)
    }

    override fun openDrawer() {
        trackEventWithScreenName(FirebaseEvent.CLICK_MAIN_MENU)
        drawerLayout.openDrawer(GravityCompat.END)
    }

    override fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) closeDrawer()
        else openDrawer()
    }

    override fun isDrawerOpen(): Boolean = drawerLayout.isDrawerOpen(GravityCompat.END)

    override fun enableDrawerSwipe(enable: Boolean) {
        if (enable) drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        else drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun setupDrawerNavigation(navController: NavController) {
        getViewBinding().drawerNav.setupWithNavController(navController)
    }

    private fun setupBottomNavigation(navController: NavController) {
        getViewBinding().bottomNav.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (actionMenu?.isOpen == true && actionMenu?.isAnimating() == false) {
            actionMenu?.toggle(getViewBinding().ivYapIt, true)
        } else if (drawerLayout.isDrawerOpen(GravityCompat.END)) closeDrawer()
        else if (getViewBinding().viewPager.currentItem != 0) {
            bottomNav.selectedItemId = R.id.yapHome
        } else super.onBackPressed()
    }

    private fun addListeners() {
        expandableLayout.setOnExpansionUpdateListener { expansionFraction, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.setImageResource(R.drawable.ic_chevron_up)
                ExpandableLayout.State.COLLAPSED -> ivChevron.setImageResource(R.drawable.ic_chevron_down)
            }
        }
        getViewBinding().includedDrawerLayout.lAnalytics.lnAnalytics.setOnClickListener {
            trackEventWithScreenName(FirebaseEvent.CLICK_ANALYTICS_MAIN_MENU)
            launchActivity<CardAnalyticsActivity>(type = FeatureSet.ANALYTICS)
            closeDrawer()
        }
        getViewBinding().includedDrawerLayout.lRefer.lnAnalytics.setOnClickListener {
            trackEventWithScreenName(FirebaseEvent.CLICK_REFER_FRIEND)
            startFragment<InviteFriendFragment>(
                InviteFriendFragment::class.java.name, false,
                bundleOf()
            )
            closeDrawer()
        }
        getViewBinding().includedDrawerLayout.lStatements.lnAnalytics.setOnClickListener {
            SessionManager.getPrimaryCard()?.let {
                trackEventWithScreenName(FirebaseEvent.CLICK_STATEMENTS)
                launchActivity<CardStatementsActivity> {
                    putExtra("card", it)
                    putExtra("isFromDrawer", true)
                }
                closeDrawer()
            }
        }
        getViewBinding().includedDrawerLayout.lSupport.lnAnalytics.setOnClickListener {
            startActivity(
                FragmentPresenterActivity.getIntent(
                    this,
                    Constants.MODE_HELP_SUPPORT, null
                )
            )
            closeDrawer()
        }
        getViewBinding().includedDrawerLayout.lyContact.lnAnalytics.setOnClickListener {
            trackEventWithScreenName(FirebaseEvent.CLICK_HELP_MAIN_MENU)
            startActivity(
                FragmentPresenterActivity.getIntent(
                    this,
                    Constants.MODE_HELP_SUPPORT, null
                )
            )
            closeDrawer()
        }

        getViewBinding().includedDrawerLayout.lLiveChat.lnAnalytics.setOnClickListener {
            trackEventWithScreenName(FirebaseEvent.CLICK_LIVECHAT_MAIN_MENU)
            chatSetup()
            closeDrawer()
        }

        getViewBinding().includedDrawerLayout.ivSettings.setOnClickListener {
            trackEventWithScreenName(FirebaseEvent.CLICK_PROFILE)
            startActivity(Intent(this, MoreActivity::class.java))
            closeDrawer()
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.yapHome -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_HOME)
                    getViewBinding().viewPager.setCurrentItem(0, false)
                    SessionManager.getAccountInfo()
                }
                R.id.yapStore -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_STORE)
                    getViewBinding().viewPager.setCurrentItem(1, false)
                }
                R.id.yapIt -> {

                }
                R.id.yapCards -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_CARDS)
                    getViewBinding().viewPager.setCurrentItem(2, false)
                }
                R.id.yapMore -> {
                    trackEventWithScreenName(FirebaseEvent.CLICK_MORE_DASHBOARD)
                    getViewBinding().viewPager.setCurrentItem(3, false)
                }
            }
            true
        }
        //Don't remove it not by mistake
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.yapIt -> {

                }
                R.id.yapCards -> {
                    getViewBinding().viewPager.setCurrentItem(2, false)
                }
            }
        }
    }

    private fun checkPermission() {
        permissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.READ_CONTACTS
            ), 100
        )
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                openY2YScreen()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                openY2YScreen()
            }

            override fun onPermissionDenied() {
                openY2YScreen()
            }

            override fun onPermissionDeniedBySystem() {
                openY2YScreen()
            }
        })
    }

    private fun openY2YScreen() {
        launchActivity<YapToYapDashboardActivity>(type = FeatureSet.YAP_TO_YAP) {
            putExtra(ExtraKeys.IS_Y2Y_SEARCHING.name, false)
        }
    }

    override fun onResume() {
        super.onResume()
        getCountUnreadMessage()
        if (bottomNav.selectedItemId == R.id.yapHome) {
            SessionManager.getAccountInfo() {
                viewModel.populateState()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionHelper != null) {
            permissionHelper!!.onRequestPermissionsResult(
                requestCode,
                permissions as Array<String>,
                grantResults
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodes.REQUEST_NOTIFICATION_FLOW -> {
                data?.let {
                    val result =
                        data.getBooleanExtra(Constants.result, false)
                    if (result) {
                        getViewBinding().viewPager.setCurrentItem(0, false)
                        getViewBinding().bottomNav.selectedItemId = R.id.yapHome
                    }
                }
            }
        }
    }

    fun getViewBinding(): ActivityYapDashboardBinding {
        return (viewDataBinding as ActivityYapDashboardBinding)
    }

    private fun logoutAlert() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.screen_profile_settings_logout_display_text_alert_title))
            .setMessage(getString(R.string.screen_profile_settings_logout_display_text_alert_message))
            .setPositiveButton(getString(R.string.screen_profile_settings_logout_display_text_alert_logout),
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.logout()
                })

            .setNegativeButton(
                getString(R.string.screen_profile_settings_logout_display_text_alert_cancel),
                null
            )
            .show()
    }

    private fun doLogout() {
        SessionManager.doLogout(this)
        finishAffinity()
    }

    fun findVisibleFragment() {
//        supportFragmentManager.findFragmentById(YapHomeFragment::class.java)
    }

}