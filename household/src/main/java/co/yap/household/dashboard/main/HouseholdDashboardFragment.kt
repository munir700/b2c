package co.yap.household.dashboard.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.cards.MyCardFragment
import co.yap.household.dashboard.expense.HouseHoldExpenseFragment
import co.yap.household.dashboard.home.HouseholdHomeFragment
import co.yap.household.dashboard.more.HouseHoldMoreFragment
import co.yap.household.databinding.ActivityHouseholdDashboardBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.dashboard.more.cdm.CdmMapFragment
import co.yap.modules.dashboard.more.help.fragments.HelpSupportFragment
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyDashboardActivity
import co.yap.modules.sidemenu.ProfilePictureAdapter
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.sendmoney.home.main.SMBeneficiaryParentActivity
import co.yap.translation.Strings
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.adapters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.alert
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_household_dashboard.*
import kotlinx.android.synthetic.main.layout_drawer_header.*
import kotlinx.android.synthetic.main.layout_drawer_header_expandable.*
import net.cachapa.expandablelayout.ExpandableLayout
import javax.inject.Inject

@AndroidEntryPoint
class HouseholdDashboardFragment:
    BaseNavViewModelFragmentV2<ActivityHouseholdDashboardBinding, IHouseholdDashboard.State, HouseHoldDashBoardVM>(),
    FloatingActionMenu.MenuStateChangeListener, DrawerListenerImpl {

    val adapter: SectionsPagerAdapter by lazy {
         SectionsPagerAdapter(requireActivity(), childFragmentManager)
    }

    @Inject
    lateinit var profilePictureAdapter: ProfilePictureAdapter

    private var actionMenu: FloatingActionMenu? = null

    val actionMenuBuilder: FloatingActionMenu.Builder by lazy {
        FloatingActionMenu.Builder(requireActivity())
            .setStartAngle(0)
            .setEndAngle(-180).setRadius(requireContext().dimen(R.dimen._69sdp))
            .setAnimationHandler(SlideInAnimationHandler())
            .addSubActionView(
                getString(R.string.send_money),
                co.yap.R.drawable.ic_send_money,
                co.yap.R.layout.component_yap_menu_sub_button,
                requireActivity(), 1
            )

            .addSubActionView(
                requireContext().getString(R.string.request_money),
                R.drawable.ic_request_money,
                co.yap.R.layout.component_yap_menu_sub_button,
                requireActivity(), 2
            ).setStateChangeListener(this)
    }
    override fun getBindingVariable() = BR.viewModel
    override val viewModel: HouseHoldDashBoardVM by viewModels()

    override fun getLayoutId() = R.layout.activity_household_dashboard

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.profilePictureAdapter.set(profilePictureAdapter)
        profilePictureAdapter.onItemClickListener = onItemClickListener
        setBackButtonDispatcher()
        setHasOptionsMenu(true)
        actionMenu = actionMenuBuilder.attachTo(mViewDataBinding.ivYapItAction)
            .setAlphaOverlay(mViewDataBinding.flAlphaOverlay)
            .setTxtYapIt(mViewDataBinding.txtYapIt).build()
        setupViewPager()
        drawerLayout.addDrawerListener(this)
        expandableLayout.setOnExpansionUpdateListener { _, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.rotation = 180F
                ExpandableLayout.State.COLLAPSED -> ivChevron.rotation = 0F
            }
        }
    }

    private fun setupViewPager() {
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseHoldExpenseFragment>()
        adapter.addFragmentInfo<MyCardFragment>()
        adapter.addFragmentInfo<HouseHoldMoreFragment>()
        viewModel.adapter.set(adapter)
        bottomNav.setUpWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 3
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                drawerLayout.setDrawerLockMode(if (position == 0) LOCK_MODE_UNLOCKED else LOCK_MODE_LOCKED_CLOSED)
            }
        })
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.lyHeader_section -> expandableLayout.toggle(true)
            else -> {
                drawerLayout.closeDrawer(GravityCompat.END)
                drawerLayout.tag = id
                //launchActivity<MoreActivity>()
            }
        }
    }

    override fun onDrawerClosed(drawerView: View) {
        val id = drawerLayout.tag as? Int
        id?.let {
            when (id) {
                R.id.ivSettings -> launchActivity<MoreActivity>()
                R.id.atm_cdm -> startFragment(CdmMapFragment::class.java.name)
                R.id.helpSupport -> startFragment(HelpSupportFragment::class.java.name)
                R.id.ContactUs -> startFragment(HelpSupportFragment::class.java.name)
                R.id.notification -> toast("Coming Soon")
                else -> {
                }
            }
        }
        drawerLayout.tag = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.open_drawer -> {
                drawerLayout.openDrawer(GravityCompat.END)
                return true
            }
        }
        return false
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AccountInfo) {
                if (data.accountType == AccountType.B2C_ACCOUNT.name) {
                    data.uuid?.let {
                        SwitchProfileLiveData.get(it, this@HouseholdDashboardFragment)
                            .observe(this@HouseholdDashboardFragment, Observer<AccountInfo?> {
                                launchActivity<YapDashboardActivity>(clearPrevious = true)
                            })
                    }
                } else launchActivity<MoreActivity>()
            }
        }
    }

    override fun onMenuOpened(menu: FloatingActionMenu) {
    }

    override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
        when (subActionButtonId) {
            1 -> {
                launchActivity<SendMoneyDashboardActivity>(type = FeatureSet.SEND_MONEY)
            }

            2 -> {
                if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus) {
                    alert("Coming Soon")
                    // will perform required action here
                } else {
                    showToast("${getString(Strings.screen_popup_activation_pending_display_text_message)}^${AlertType.TOAST.name}")
                }
            }
        }
    }

    override fun toolBarVisibility() = false
    override fun onBackPressed(): Boolean {
        if (actionMenu?.isOpen!! && !actionMenu?.isAnimating()!!) {
            actionMenu?.toggle(mViewDataBinding.ivYapItAction, true)
        } else if (drawerLayout.isDrawerOpen(GravityCompat.END)) drawerLayout.closeDrawer(
            GravityCompat.END
        ) else finishActivityAffinity()
        return super.onBackPressed()
    }
}
