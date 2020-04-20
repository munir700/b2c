package co.yap.household.dashboard.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.home.HouseholdHomeFragment
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.household.databinding.ActivityHouseholdDashboardBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.BaseViewModelActivity
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_household_dashboard.*
import kotlinx.android.synthetic.main.layout_drawer_header.*
import kotlinx.android.synthetic.main.layout_drawer_header_expandable.*
import kotlinx.android.synthetic.main.layout_drawer_household_dashboard.*
import net.cachapa.expandablelayout.ExpandableLayout
import javax.inject.Inject

class HouseholdDashboardActivity :
    BaseViewModelActivity<ActivityHouseholdDashboardBinding, IHouseholdDashboard.State, HouseHoldDashBoardVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.activity_household_dashboard
    private var actionMenu: FloatingActionMenu? = null
    var selectedUser: AccountInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        setUpAdapter()
        addListeners()
    }

    private fun addObservers() {
        MyUserManager.switchProfile.observe(this, switchProfileObserver)
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnCopyHH -> viewModel.copyAccountInfoToClipboard()
                R.id.lyHeader_section -> expandableLayout.toggle(true)
            }
        })
    }

    private fun setUpAdapter() {
        val mAdapter = ProfilePictureAdapter(
            MyUserManager.usersList,
            null
        )
        mAdapter.onItemClickListener = userClickListener
        recyclerView.adapter = mAdapter
        viewModel.profilePictureAdapter.set(mAdapter)
    }

    private val userClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AccountInfo) {
                selectedUser = data
                MyUserManager.switchProfile(data.uuid)
            }
        }
    }

    private val switchProfileObserver = Observer<Boolean> {
        if (it) {
            if (selectedUser?.accountType == AccountType.B2C_ACCOUNT.name) {
                // Go to yap Dashboard
                launchActivity<YapDashboardActivity>()
                finish()
            } else if (selectedUser?.accountType == AccountType.B2C_HOUSEHOLD.name) {
                // Go to household Dashboard
            }
        }
    }

    private fun addListeners() {
        expandableLayout.setOnExpansionUpdateListener { expansionFraction, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.rotation = 180F
                ExpandableLayout.State.COLLAPSED -> ivChevron.rotation = 0F
            }
        }
    }

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()

        viewModel.adapter.set(adapter)
        bottomNav.setUpWithViewPager(viewPager)
        setupYapItButton()
    }

    private fun setupYapItButton() {
        actionMenu = FloatingActionMenu.Builder(this)
            .setStartAngle(0)
            .setEndAngle(-180).setRadius(dimen(co.yap.R.dimen._69sdp))
            .setAnimationHandler(SlideInAnimationHandler())
            .addSubActionView(
                getString(co.yap.R.string.yap_to_yap),
                co.yap.R.drawable.ic_yap_to_yap,
                co.yap.R.layout.component_yap_menu_sub_button,
                this, 1
            )
            .addSubActionView(
                getString(co.yap.R.string.top_up),
                co.yap.R.drawable.ic_top_up,
                co.yap.R.layout.component_yap_menu_sub_button,
                this, 2
            )
            .attachTo(mViewDataBinding.ivYapIt).setAlphaOverlay(mViewDataBinding.flAlphaOverlay)
            .setTxtYapIt(mViewDataBinding.txtYapIt)
            .setStateChangeListener(object :
                FloatingActionMenu.MenuStateChangeListener {
                override fun onMenuOpened(menu: FloatingActionMenu) {

                }

                override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
                    when (subActionButtonId) {
                        1 -> {

                            showToast("Account activation pending 1")

                        }
                        2 -> {
                            showToast("Account activation pending 2")
                        }
                    }
                }
            })
            .build()
    }
}