package co.yap.modules.dashboard.main.activities

import android.Manifest
import android.app.Dialog
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
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityYapDashboardBinding
import co.yap.modules.dashboard.main.adapters.YapDashboardAdaptor
import co.yap.modules.dashboard.main.interfaces.IYapDashboard
import co.yap.modules.dashboard.main.viewmodels.YapDashBoardViewModel
import co.yap.modules.dashboard.yapit.y2y.home.activities.YapToYapDashboardActivity
import co.yap.modules.others.unverifiedemail.UnVerifiedEmailActivity
import co.yap.translation.Strings
import co.yap.widgets.CoreButton
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.helpers.PermissionHelper
import co.yap.yapcore.helpers.dimen
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_yap_dashboard.*
import kotlinx.android.synthetic.main.layout_drawer_yap_dashboard.*
import net.cachapa.expandablelayout.ExpandableLayout

class YapDashboardActivity : BaseBindingActivity<IYapDashboard.ViewModel>(), IYapDashboard.View,
    IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_yap_dashboard

    override val viewModel: IYapDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(YapDashBoardViewModel::class.java)

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var adapter: YapDashboardAdaptor
    var permissionHelper: PermissionHelper? = null
    private var actionMenu: FloatingActionMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupPager()
        viewModel.getAccountBalanceRequest()
        addObservers()
        addListeners()
        setupYapButton()
    }

    private fun setupYapButton() {
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
            .setStateChangeListener(object :
                FloatingActionMenu.MenuStateChangeListener {
                override fun onMenuOpened(menu: FloatingActionMenu) {

                }

                override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
                    when (subActionButtonId) {
                        1 -> checkPermission()
//                        2->checkPermission()
//                        3->checkPermission()

                    }
                }

            })
            .build()
    }

    private fun setupPager() {

        adapter = YapDashboardAdaptor(supportFragmentManager)
        getViewBinding().viewPager.adapter = adapter

        with(getViewBinding().viewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
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
            }
        })
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnCopy -> viewModel.copyAccountInfoToClipboard()
                R.id.lUserInfo -> expandableLayout.toggle(true)
            }
        })

        viewModel.showUnverifedscreen.observe(this, Observer {
            if (it) {
                showUnverifiedPopup()
            }
        })
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
                MyUserManager.user!!.currentCustomer.firstName
            )
        tvEmail.text = MyUserManager.user!!.currentCustomer.email

        val fcs = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary))
        val myClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                dialog.dismiss()
                startActivity(
                    UnVerifiedEmailActivity.newIntent(widget.context)
                )
            }
        }

        val newValue =
            getString(Strings.screen_email_verified_popup_display_text_click_here).plus("")
        val clickValue =
            getString(Strings.screen_email_verified_popup_button_title_click_here)
        val spanStr = SpannableStringBuilder("$newValue $clickValue")

        spanStr.setSpan(
            fcs,
            (newValue.length + 1),
            (newValue.length + 1) + clickValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanStr.setSpan(
            myClickableSpan,
            (newValue.length + 1),
            (newValue.length + 1) + clickValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvTroubleDescription.text = spanStr
        tvTroubleDescription.movementMethod = LinkMovementMethod.getInstance()

        dialog.findViewById<CoreButton>(R.id.btnOpenMailApp).setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            startActivity(Intent.createChooser(intent, "Choose"))
        }
        dialog.findViewById<TextView>(R.id.btnLater).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showHideBottomBar(show: Boolean) {
        getViewBinding().rlYapIt.visibility = if (show) View.VISIBLE else View.GONE
        getViewBinding().bottomNav.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END)
    }

    override fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END)
    }

    override fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) closeDrawer()
        else openDrawer()
    }

    override fun enableDrawerSwipe(enable: Boolean) {
        if (enable) drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        else drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun setupDrawerNavigation(navController: NavController) {
        getViewBinding().drawerNav?.setupWithNavController(navController)
    }

    private fun setupBottomNavigation(navController: NavController) {
        getViewBinding().bottomNav.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (actionMenu?.isOpen!! && !actionMenu?.isAnimating()!!) {
            actionMenu?.toggle(ivYapIt, true)
        } else if (drawerLayout.isDrawerOpen(GravityCompat.END)) closeDrawer()
        else super.onBackPressed()
    }

    private fun addListeners() {
        expandableLayout.setOnExpansionUpdateListener { expansionFraction, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.setImageResource(R.drawable.ic_chevron_up)
                ExpandableLayout.State.COLLAPSED -> ivChevron.setImageResource(R.drawable.ic_chevron_down)
            }
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.yapHome -> {
                    getViewBinding().viewPager.setCurrentItem(0, false)
                }
                R.id.yapStore -> {
                    getViewBinding().viewPager.setCurrentItem(1, false)
                }
                R.id.yapIt -> {
                    //checkPermission()
                    //getViewBinding().ivYapIt
                }
                R.id.yapCards -> {
                    getViewBinding().viewPager.setCurrentItem(2, false)
                }
                R.id.yapMore -> {
                    getViewBinding().viewPager.setCurrentItem(3, false)
                }
            }
            true
        }
        //Don't remove it not by mistake
        bottomNav.setOnNavigationItemReselectedListener {
            it
            when (it.itemId) {
                R.id.yapIt -> {
                    checkPermission()
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
                startActivity(
                    YapToYapDashboardActivity.getIntent(
                        this@YapDashboardActivity,
                        false,
                        null
                    )
                )
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDenied() {
                showToast("Can't proceed without permissions")
            }

            override fun onPermissionDeniedBySystem() {
                permissionHelper!!.openAppDetailsActivity()
            }
        })
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

    private fun getViewBinding(): ActivityYapDashboardBinding {
        return (viewDataBinding as ActivityYapDashboardBinding)
    }

}