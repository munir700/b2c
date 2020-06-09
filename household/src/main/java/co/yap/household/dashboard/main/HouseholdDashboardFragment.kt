package co.yap.household.dashboard.main

import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.cards.MyCardFragment
import co.yap.household.dashboard.expense.HouseHoldExpenseFragment
import co.yap.household.dashboard.home.HouseholdHomeFragment
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.household.databinding.ActivityHouseholdDashboardBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.sendmoney.home.activities.SendMoneyLandingActivity
import co.yap.translation.Strings
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.PartnerBankStatus
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_household_dashboard.*
import kotlinx.android.synthetic.main.layout_drawer_header.*
import kotlinx.android.synthetic.main.layout_drawer_header_expandable.*
import net.cachapa.expandablelayout.ExpandableLayout
import javax.inject.Inject

class HouseholdDashboardFragment :
    BaseNavViewModelFragment<ActivityHouseholdDashboardBinding, IHouseholdDashboard.State, HouseHoldDashBoardVM>(),
    FloatingActionMenu.MenuStateChangeListener {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    @Inject
    lateinit var profilePictureAdapter: ProfilePictureAdapter

    private var actionMenu: FloatingActionMenu? = null

    @Inject
    lateinit var actionMenuBuilder: FloatingActionMenu.Builder
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.activity_household_dashboard

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        viewModel.profilePictureAdapter.set(profilePictureAdapter)
        profilePictureAdapter.onItemClickListener = onItemClickListener
        setBackButtonDispatcher()
        setHasOptionsMenu(true)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
        actionMenu = actionMenuBuilder.attachTo(mViewDataBinding.ivYapIt)
            .setAlphaOverlay(mViewDataBinding.flAlphaOverlay)
            .setTxtYapIt(mViewDataBinding.txtYapIt).setStateChangeListener(object :
                FloatingActionMenu.MenuStateChangeListener {
                override fun onMenuOpened(menu: FloatingActionMenu) {

                }

                override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
                    when (subActionButtonId) {
                        1 -> {
                            if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
                                startActivity(SendMoneyLandingActivity.newIntent(activity!!))
                            } else {
                                showToast("${getString(Strings.screen_popup_activation_pending_display_text_message)}^${AlertType.TOAST.name}")
                            }
                        }

                        2 -> {
                            if (PartnerBankStatus.ACTIVATED.status == MyUserManager.user?.partnerBankStatus) {
                                // will perform required action here
                            } else {
                                showToast("${getString(Strings.screen_popup_activation_pending_display_text_message)}^${AlertType.TOAST.name}")
                            }
                        }
                    }
                }

            }).build()
        setupViewPager()
        expandableLayout.setOnExpansionUpdateListener { _, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.rotation = 180F
                ExpandableLayout.State.COLLAPSED -> ivChevron.rotation = 0F
            }
        }
    }

    private fun setupViewPager() {
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<MyCardFragment>()
        adapter.addFragmentInfo<HouseHoldExpenseFragment>()
        adapter.addFragmentInfo<HouseHoldExpenseFragment>()
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

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnCopyHH -> {
            }
            R.id.lyHeader_section -> expandableLayout.toggle(true)
        }
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
                }
            }
        }
    }

    override fun onMenuOpened(menu: FloatingActionMenu) {
    }

    override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
        when (subActionButtonId) {
            1 -> showToast("Account activation pending 1")
            2 -> showToast("Account activation pending 2")
        }
    }

    override fun toolBarVisibility() = false
    override fun onBackPressed(): Boolean {
        if (actionMenu?.isOpen!! && !actionMenu?.isAnimating()!!) {
            actionMenu?.toggle(mViewDataBinding.ivYapIt, true)
        } else if (drawerLayout.isDrawerOpen(GravityCompat.END)) drawerLayout.closeDrawer(
            GravityCompat.END
        ) else finishActivityAffinity()
        return super.onBackPressed()
    }
}
